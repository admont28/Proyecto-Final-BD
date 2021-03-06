package Beans;

import Entities.Auxiliar;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.AuxiliarJpaController;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.Persistence;

@ManagedBean(name = "auxiliarController")
@SessionScoped
public class AuxiliarController implements Serializable {

    private Auxiliar current;
    private DataModel items = null;
    private AuxiliarJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AuxiliarController() {
    }

    public Auxiliar getSelected() {
        if (current == null) {
            current = new Auxiliar();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AuxiliarJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new AuxiliarJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getAuxiliarCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findAuxiliarEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/auxiliar/List";
    }

    public String prepareView() {
        current = (Auxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/auxiliar/View";
    }

    public String prepareCreate() {
        current = new Auxiliar();
        selectedItemIndex = -1;
        return "/Pages/auxiliar/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuxiliarCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Auxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/auxiliar/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuxiliarUpdated"));
            return "/Pages/auxiliar/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Auxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/auxiliar/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/auxiliar/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/auxiliar/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getAuxiliarId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuxiliarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getAuxiliarCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findAuxiliarEntities(1, selectedItemIndex).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "/Pages/auxiliar/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/auxiliar/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAuxiliarEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAuxiliarEntities(), true);
    }

    @FacesConverter(forClass = Auxiliar.class)
    public static class AuxiliarControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AuxiliarController controller = (AuxiliarController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "auxiliarController");
            return controller.getJpaController().findAuxiliar(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Auxiliar) {
                Auxiliar o = (Auxiliar) object;
                return getStringKey(o.getAuxiliarId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Auxiliar.class.getName());
            }
        }

    }

}

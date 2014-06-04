package Beans;

import Entities.TipoAuxiliar;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.TipoAuxiliarJpaController;

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

@ManagedBean(name = "tipoAuxiliarController")
@SessionScoped
public class TipoAuxiliarController implements Serializable {

    private TipoAuxiliar current;
    private DataModel items = null;
    private TipoAuxiliarJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoAuxiliarController() {
    }

    public TipoAuxiliar getSelected() {
        if (current == null) {
            current = new TipoAuxiliar();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoAuxiliarJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new TipoAuxiliarJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getTipoAuxiliarCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findTipoAuxiliarEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (TipoAuxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/tipoAuxiliar/View";
    }

    public String prepareCreate() {
        current = new TipoAuxiliar();
        selectedItemIndex = -1;
        return "/Pages/tipoAuxiliar/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoAuxiliarCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TipoAuxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/tipoAuxiliar/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoAuxiliarUpdated"));
            return "/Pages/tipoAuxiliar/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TipoAuxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/tipoAuxiliar/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/tipoAuxiliar/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/tipoAuxiliar/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getTipoAuxiliarId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoAuxiliarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getTipoAuxiliarCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findTipoAuxiliarEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/tipoAuxiliar/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/tipoAuxiliar/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findTipoAuxiliarEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findTipoAuxiliarEntities(), true);
    }

    @FacesConverter(forClass = TipoAuxiliar.class)
    public static class TipoAuxiliarControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoAuxiliarController controller = (TipoAuxiliarController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoAuxiliarController");
            return controller.getJpaController().findTipoAuxiliar(getKey(value));
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
            if (object instanceof TipoAuxiliar) {
                TipoAuxiliar o = (TipoAuxiliar) object;
                return getStringKey(o.getTipoAuxiliarId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoAuxiliar.class.getName());
            }
        }

    }

}

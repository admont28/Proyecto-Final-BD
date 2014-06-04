package Beans;

import Entities.TipoRequisito;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.TipoRequisitoJpaController;

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

@ManagedBean(name = "tipoRequisitoController")
@SessionScoped
public class TipoRequisitoController implements Serializable {

    private TipoRequisito current;
    private DataModel items = null;
    private TipoRequisitoJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoRequisitoController() {
    }

    public TipoRequisito getSelected() {
        if (current == null) {
            current = new TipoRequisito();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoRequisitoJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new TipoRequisitoJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getTipoRequisitoCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findTipoRequisitoEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/tipoRequisito/List";
    }

    public String prepareView() {
        current = (TipoRequisito) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/tipoRequisito/View";
    }

    public String prepareCreate() {
        current = new TipoRequisito();
        selectedItemIndex = -1;
        return "/Pages/tipoRequisito/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoRequisitoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TipoRequisito) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/tipoRequisito/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoRequisitoUpdated"));
            return "/Pages/tipoRequisito/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TipoRequisito) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/tipoRequisito/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/tipoRequisito/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/tipoRequisito/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getTipoRequisitoId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TipoRequisitoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getTipoRequisitoCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findTipoRequisitoEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/tipoRequisito/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/tipoRequisito/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findTipoRequisitoEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findTipoRequisitoEntities(), true);
    }

    @FacesConverter(forClass = TipoRequisito.class)
    public static class TipoRequisitoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoRequisitoController controller = (TipoRequisitoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoRequisitoController");
            return controller.getJpaController().findTipoRequisito(getKey(value));
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
            if (object instanceof TipoRequisito) {
                TipoRequisito o = (TipoRequisito) object;
                return getStringKey(o.getTipoRequisitoId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoRequisito.class.getName());
            }
        }

    }

}

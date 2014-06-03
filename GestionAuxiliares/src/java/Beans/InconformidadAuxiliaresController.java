package Beans;

import Entities.InconformidadAuxiliares;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.InconformidadAuxiliaresJpaController;

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

@ManagedBean(name = "inconformidadAuxiliaresController")
@SessionScoped
public class InconformidadAuxiliaresController implements Serializable {

    private InconformidadAuxiliares current;
    private DataModel items = null;
    private InconformidadAuxiliaresJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public InconformidadAuxiliaresController() {
    }

    public InconformidadAuxiliares getSelected() {
        if (current == null) {
            current = new InconformidadAuxiliares();
            selectedItemIndex = -1;
        }
        return current;
    }

    private InconformidadAuxiliaresJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new InconformidadAuxiliaresJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getInconformidadAuxiliaresCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findInconformidadAuxiliaresEntities(getPageSize(), getPageFirstItem()));
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
        current = (InconformidadAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new InconformidadAuxiliares();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InconformidadAuxiliaresCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (InconformidadAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InconformidadAuxiliaresUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (InconformidadAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getInconformidadAuxiliaresId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InconformidadAuxiliaresDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getInconformidadAuxiliaresCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findInconformidadAuxiliaresEntities(1, selectedItemIndex).get(0);
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
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findInconformidadAuxiliaresEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findInconformidadAuxiliaresEntities(), true);
    }

    @FacesConverter(forClass = InconformidadAuxiliares.class)
    public static class InconformidadAuxiliaresControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InconformidadAuxiliaresController controller = (InconformidadAuxiliaresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inconformidadAuxiliaresController");
            return controller.getJpaController().findInconformidadAuxiliares(getKey(value));
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
            if (object instanceof InconformidadAuxiliares) {
                InconformidadAuxiliares o = (InconformidadAuxiliares) object;
                return getStringKey(o.getInconformidadAuxiliaresId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + InconformidadAuxiliares.class.getName());
            }
        }

    }

}

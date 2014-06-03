package Beans;

import Entities.ResAuxiliaresSeleccionados;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.ResAuxiliaresSeleccionadosJpaController;

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

@ManagedBean(name = "resAuxiliaresSeleccionadosController")
@SessionScoped
public class ResAuxiliaresSeleccionadosController implements Serializable {

    private ResAuxiliaresSeleccionados current;
    private DataModel items = null;
    private ResAuxiliaresSeleccionadosJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ResAuxiliaresSeleccionadosController() {
    }

    public ResAuxiliaresSeleccionados getSelected() {
        if (current == null) {
            current = new ResAuxiliaresSeleccionados();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ResAuxiliaresSeleccionadosJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new ResAuxiliaresSeleccionadosJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getResAuxiliaresSeleccionadosCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findResAuxiliaresSeleccionadosEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/resAuxiliaresSeleccionados/List";
    }

    public String prepareView() {
        current = (ResAuxiliaresSeleccionados) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/resAuxiliaresSeleccionados/View";
    }

    public String prepareCreate() {
        current = new ResAuxiliaresSeleccionados();
        selectedItemIndex = -1;
        return "/Pages/resAuxiliaresSeleccionados/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ResAuxiliaresSeleccionadosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ResAuxiliaresSeleccionados) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/resAuxiliaresSeleccionados/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ResAuxiliaresSeleccionadosUpdated"));
            return "/Pages/resAuxiliaresSeleccionados/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ResAuxiliaresSeleccionados) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/resAuxiliaresSeleccionados/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/resAuxiliaresSeleccionados/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/resAuxiliaresSeleccionados/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getResAuxSelId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ResAuxiliaresSeleccionadosDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getResAuxiliaresSeleccionadosCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findResAuxiliaresSeleccionadosEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/resAuxiliaresSeleccionados/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/resAuxiliaresSeleccionados/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findResAuxiliaresSeleccionadosEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findResAuxiliaresSeleccionadosEntities(), true);
    }

    @FacesConverter(forClass = ResAuxiliaresSeleccionados.class)
    public static class ResAuxiliaresSeleccionadosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ResAuxiliaresSeleccionadosController controller = (ResAuxiliaresSeleccionadosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "resAuxiliaresSeleccionadosController");
            return controller.getJpaController().findResAuxiliaresSeleccionados(getKey(value));
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
            if (object instanceof ResAuxiliaresSeleccionados) {
                ResAuxiliaresSeleccionados o = (ResAuxiliaresSeleccionados) object;
                return getStringKey(o.getResAuxSelId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ResAuxiliaresSeleccionados.class.getName());
            }
        }

    }

}

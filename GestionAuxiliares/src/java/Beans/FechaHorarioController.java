package Beans;

import Entities.FechaHorario;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.FechaHorarioJpaController;

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

@ManagedBean(name = "fechaHorarioController")
@SessionScoped
public class FechaHorarioController implements Serializable {

    private FechaHorario current;
    private DataModel items = null;
    private FechaHorarioJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public FechaHorarioController() {
    }

    public FechaHorario getSelected() {
        if (current == null) {
            current = new FechaHorario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FechaHorarioJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new FechaHorarioJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getFechaHorarioCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findFechaHorarioEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/fechaHorario/List";
    }

    public String prepareView() {
        current = (FechaHorario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/fechaHorario/View";
    }

    public String prepareCreate() {
        current = new FechaHorario();
        selectedItemIndex = -1;
        return "/Pages/fechaHorario/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FechaHorarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (FechaHorario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/fechaHorario/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FechaHorarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (FechaHorario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/fechaHorario/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/fechaHorario/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/fechaHorario/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getFechaHorarioId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FechaHorarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getFechaHorarioCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findFechaHorarioEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/fechaHorario/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/fechaHorario/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findFechaHorarioEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findFechaHorarioEntities(), true);
    }

    @FacesConverter(forClass = FechaHorario.class)
    public static class FechaHorarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FechaHorarioController controller = (FechaHorarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fechaHorarioController");
            return controller.getJpaController().findFechaHorario(getKey(value));
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
            if (object instanceof FechaHorario) {
                FechaHorario o = (FechaHorario) object;
                return getStringKey(o.getFechaHorarioId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FechaHorario.class.getName());
            }
        }

    }

}

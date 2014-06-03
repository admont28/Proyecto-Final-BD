package Beans;

import Entities.CumplimientoActividades;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.CumplimientoActividadesJpaController;

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

@ManagedBean(name = "cumplimientoActividadesController")
@SessionScoped
public class CumplimientoActividadesController implements Serializable {

    private CumplimientoActividades current;
    private DataModel items = null;
    private CumplimientoActividadesJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CumplimientoActividadesController() {
    }

    public CumplimientoActividades getSelected() {
        if (current == null) {
            current = new CumplimientoActividades();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CumplimientoActividadesJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new CumplimientoActividadesJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getCumplimientoActividadesCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findCumplimientoActividadesEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/cumplimientoActividades/List";
    }

    public String prepareView() {
        current = (CumplimientoActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/cumplimientoActividades/View";
    }

    public String prepareCreate() {
        current = new CumplimientoActividades();
        selectedItemIndex = -1;
        return "/Pages/cumplimientoActividades/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CumplimientoActividadesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (CumplimientoActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/cumplimientoActividades/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CumplimientoActividadesUpdated"));
            return "/Pages/cumplimientoActividades/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (CumplimientoActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/cumplimientoActividades/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/cumplimientoActividades/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/cumplimientoActividades/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getCumpActId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CumplimientoActividadesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getCumplimientoActividadesCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findCumplimientoActividadesEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/cumplimientoActividades/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/cumplimientoActividades/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findCumplimientoActividadesEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findCumplimientoActividadesEntities(), true);
    }

    @FacesConverter(forClass = CumplimientoActividades.class)
    public static class CumplimientoActividadesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CumplimientoActividadesController controller = (CumplimientoActividadesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cumplimientoActividadesController");
            return controller.getJpaController().findCumplimientoActividades(getKey(value));
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
            if (object instanceof CumplimientoActividades) {
                CumplimientoActividades o = (CumplimientoActividades) object;
                return getStringKey(o.getCumpActId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CumplimientoActividades.class.getName());
            }
        }

    }

}

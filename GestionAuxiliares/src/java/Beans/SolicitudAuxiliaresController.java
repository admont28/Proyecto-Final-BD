package Beans;

import Entities.SolicitudAuxiliares;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.SolicitudAuxiliaresJpaController;

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

@ManagedBean(name = "solicitudAuxiliaresController")
@SessionScoped
public class SolicitudAuxiliaresController implements Serializable {

    private SolicitudAuxiliares current;
    private DataModel items = null;
    private SolicitudAuxiliaresJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SolicitudAuxiliaresController() {
    }

    public SolicitudAuxiliares getSelected() {
        if (current == null) {
            current = new SolicitudAuxiliares();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SolicitudAuxiliaresJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new SolicitudAuxiliaresJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getSolicitudAuxiliaresCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findSolicitudAuxiliaresEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/solicitudAuxiliares/List";
    }

    public String prepareView() {
        current = (SolicitudAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/solicitudAuxiliares/View";
    }

    public String prepareCreate() {
        current = new SolicitudAuxiliares();
        selectedItemIndex = -1;
        return "/Pages/solicitudAuxiliares/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudAuxiliaresCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SolicitudAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/solicitudAuxiliares/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudAuxiliaresUpdated"));
            return "/Pages/solicitudAuxiliares/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SolicitudAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/solicitudAuxiliares/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/solicitudAuxiliares/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/solicitudAuxiliares/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getSolicitudAuxiliaresId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SolicitudAuxiliaresDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getSolicitudAuxiliaresCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findSolicitudAuxiliaresEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/solicitudAuxiliares/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/solicitudAuxiliares/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findSolicitudAuxiliaresEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findSolicitudAuxiliaresEntities(), true);
    }

    @FacesConverter(forClass = SolicitudAuxiliares.class)
    public static class SolicitudAuxiliaresControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SolicitudAuxiliaresController controller = (SolicitudAuxiliaresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "solicitudAuxiliaresController");
            return controller.getJpaController().findSolicitudAuxiliares(getKey(value));
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
            if (object instanceof SolicitudAuxiliares) {
                SolicitudAuxiliares o = (SolicitudAuxiliares) object;
                return getStringKey(o.getSolicitudAuxiliaresId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SolicitudAuxiliares.class.getName());
            }
        }

    }

}

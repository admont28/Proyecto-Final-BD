package Beans;

import Entities.SeleccionAuxiliares;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.SeleccionAuxiliaresJpaController;

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

@ManagedBean(name = "seleccionAuxiliaresController")
@SessionScoped
public class SeleccionAuxiliaresController implements Serializable {

    private SeleccionAuxiliares current;
    private DataModel items = null;
    private SeleccionAuxiliaresJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SeleccionAuxiliaresController() {
    }

    public SeleccionAuxiliares getSelected() {
        if (current == null) {
            current = new SeleccionAuxiliares();
            current.setSeleccionAuxiliaresPK(new Entities.SeleccionAuxiliaresPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private SeleccionAuxiliaresJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new SeleccionAuxiliaresJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getSeleccionAuxiliaresCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findSeleccionAuxiliaresEntities(getPageSize(), getPageFirstItem()));
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
        current = (SeleccionAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new SeleccionAuxiliares();
        current.setSeleccionAuxiliaresPK(new Entities.SeleccionAuxiliaresPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getSeleccionAuxiliaresPK().setIdAuxiliares(current.getAuxiliar().getAuxiliarId());
            current.getSeleccionAuxiliaresPK().setIdEvaluacionAuxiliares(current.getEvaluacionAuxiliares().getEvaluacionAuxiliaresId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SeleccionAuxiliaresCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (SeleccionAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getSeleccionAuxiliaresPK().setIdAuxiliares(current.getAuxiliar().getAuxiliarId());
            current.getSeleccionAuxiliaresPK().setIdEvaluacionAuxiliares(current.getEvaluacionAuxiliares().getEvaluacionAuxiliaresId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SeleccionAuxiliaresUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (SeleccionAuxiliares) getItems().getRowData();
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
            getJpaController().destroy(current.getSeleccionAuxiliaresPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SeleccionAuxiliaresDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getSeleccionAuxiliaresCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findSeleccionAuxiliaresEntities(1, selectedItemIndex).get(0);
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
        return JsfUtil.getSelectItems(getJpaController().findSeleccionAuxiliaresEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findSeleccionAuxiliaresEntities(), true);
    }

    @FacesConverter(forClass = SeleccionAuxiliares.class)
    public static class SeleccionAuxiliaresControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SeleccionAuxiliaresController controller = (SeleccionAuxiliaresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "seleccionAuxiliaresController");
            return controller.getJpaController().findSeleccionAuxiliares(getKey(value));
        }

        Entities.SeleccionAuxiliaresPK getKey(String value) {
            Entities.SeleccionAuxiliaresPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.SeleccionAuxiliaresPK();
            key.setIdAuxiliares(values[0]);
            key.setIdEvaluacionAuxiliares(values[1]);
            return key;
        }

        String getStringKey(Entities.SeleccionAuxiliaresPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdAuxiliares());
            sb.append(SEPARATOR);
            sb.append(value.getIdEvaluacionAuxiliares());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof SeleccionAuxiliares) {
                SeleccionAuxiliares o = (SeleccionAuxiliares) object;
                return getStringKey(o.getSeleccionAuxiliaresPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SeleccionAuxiliares.class.getName());
            }
        }

    }

}

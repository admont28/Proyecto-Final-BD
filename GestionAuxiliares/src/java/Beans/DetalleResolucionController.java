package Beans;

import Entities.DetalleResolucion;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.DetalleResolucionJpaController;

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

@ManagedBean(name = "detalleResolucionController")
@SessionScoped
public class DetalleResolucionController implements Serializable {

    private DetalleResolucion current;
    private DataModel items = null;
    private DetalleResolucionJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DetalleResolucionController() {
    }

    public DetalleResolucion getSelected() {
        if (current == null) {
            current = new DetalleResolucion();
            current.setDetalleResolucionPK(new Entities.DetalleResolucionPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private DetalleResolucionJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new DetalleResolucionJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getDetalleResolucionCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findDetalleResolucionEntities(getPageSize(), getPageFirstItem()));
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
        current = (DetalleResolucion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new DetalleResolucion();
        current.setDetalleResolucionPK(new Entities.DetalleResolucionPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getDetalleResolucionPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            current.getDetalleResolucionPK().setIdResAuxSel(current.getResAuxiliaresSeleccionados().getResAuxSelId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DetalleResolucionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (DetalleResolucion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getDetalleResolucionPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            current.getDetalleResolucionPK().setIdResAuxSel(current.getResAuxiliaresSeleccionados().getResAuxSelId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DetalleResolucionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (DetalleResolucion) getItems().getRowData();
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
            getJpaController().destroy(current.getDetalleResolucionPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DetalleResolucionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getDetalleResolucionCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findDetalleResolucionEntities(1, selectedItemIndex).get(0);
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
        return JsfUtil.getSelectItems(getJpaController().findDetalleResolucionEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findDetalleResolucionEntities(), true);
    }

    @FacesConverter(forClass = DetalleResolucion.class)
    public static class DetalleResolucionControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DetalleResolucionController controller = (DetalleResolucionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleResolucionController");
            return controller.getJpaController().findDetalleResolucion(getKey(value));
        }

        Entities.DetalleResolucionPK getKey(String value) {
            Entities.DetalleResolucionPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.DetalleResolucionPK();
            key.setIdAuxiliar(values[0]);
            key.setIdResAuxSel(values[1]);
            return key;
        }

        String getStringKey(Entities.DetalleResolucionPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdAuxiliar());
            sb.append(SEPARATOR);
            sb.append(value.getIdResAuxSel());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DetalleResolucion) {
                DetalleResolucion o = (DetalleResolucion) object;
                return getStringKey(o.getDetalleResolucionPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DetalleResolucion.class.getName());
            }
        }

    }

}

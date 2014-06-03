package Beans;

import Entities.IncAuxAuxiliar;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.IncAuxAuxiliarJpaController;

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

@ManagedBean(name = "incAuxAuxiliarController")
@SessionScoped
public class IncAuxAuxiliarController implements Serializable {

    private IncAuxAuxiliar current;
    private DataModel items = null;
    private IncAuxAuxiliarJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public IncAuxAuxiliarController() {
    }

    public IncAuxAuxiliar getSelected() {
        if (current == null) {
            current = new IncAuxAuxiliar();
            current.setIncAuxAuxiliarPK(new Entities.IncAuxAuxiliarPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private IncAuxAuxiliarJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new IncAuxAuxiliarJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getIncAuxAuxiliarCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findIncAuxAuxiliarEntities(getPageSize(), getPageFirstItem()));
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
        current = (IncAuxAuxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new IncAuxAuxiliar();
        current.setIncAuxAuxiliarPK(new Entities.IncAuxAuxiliarPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getIncAuxAuxiliarPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            current.getIncAuxAuxiliarPK().setIdInconformidadAuxiliares(current.getInconformidadAuxiliares().getInconformidadAuxiliaresId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IncAuxAuxiliarCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (IncAuxAuxiliar) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getIncAuxAuxiliarPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            current.getIncAuxAuxiliarPK().setIdInconformidadAuxiliares(current.getInconformidadAuxiliares().getInconformidadAuxiliaresId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IncAuxAuxiliarUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (IncAuxAuxiliar) getItems().getRowData();
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
            getJpaController().destroy(current.getIncAuxAuxiliarPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("IncAuxAuxiliarDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getIncAuxAuxiliarCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findIncAuxAuxiliarEntities(1, selectedItemIndex).get(0);
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
        return JsfUtil.getSelectItems(getJpaController().findIncAuxAuxiliarEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findIncAuxAuxiliarEntities(), true);
    }

    @FacesConverter(forClass = IncAuxAuxiliar.class)
    public static class IncAuxAuxiliarControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IncAuxAuxiliarController controller = (IncAuxAuxiliarController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "incAuxAuxiliarController");
            return controller.getJpaController().findIncAuxAuxiliar(getKey(value));
        }

        Entities.IncAuxAuxiliarPK getKey(String value) {
            Entities.IncAuxAuxiliarPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.IncAuxAuxiliarPK();
            key.setIdInconformidadAuxiliares(values[0]);
            key.setIdAuxiliar(values[1]);
            return key;
        }

        String getStringKey(Entities.IncAuxAuxiliarPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdInconformidadAuxiliares());
            sb.append(SEPARATOR);
            sb.append(value.getIdAuxiliar());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof IncAuxAuxiliar) {
                IncAuxAuxiliar o = (IncAuxAuxiliar) object;
                return getStringKey(o.getIncAuxAuxiliarPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + IncAuxAuxiliar.class.getName());
            }
        }

    }

}

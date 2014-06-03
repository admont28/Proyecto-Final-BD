package Beans;

import Entities.Directores;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.DirectoresJpaController;

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

@ManagedBean(name = "directoresController")
@SessionScoped
public class DirectoresController implements Serializable {

    private Directores current;
    private DataModel items = null;
    private DirectoresJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DirectoresController() {
    }

    public Directores getSelected() {
        if (current == null) {
            current = new Directores();
            current.setDirectoresPK(new Entities.DirectoresPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private DirectoresJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new DirectoresJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getDirectoresCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findDirectoresEntities(getPageSize(), getPageFirstItem()));
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
        current = (Directores) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Directores();
        current.setDirectoresPK(new Entities.DirectoresPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getDirectoresPK().setIdJornada(current.getJornada().getJornadaId());
            current.getDirectoresPK().setIdPrograma(current.getProgramaAcademico().getProgramaAcademicoId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DirectoresCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Directores) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getDirectoresPK().setIdJornada(current.getJornada().getJornadaId());
            current.getDirectoresPK().setIdPrograma(current.getProgramaAcademico().getProgramaAcademicoId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DirectoresUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Directores) getItems().getRowData();
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
            getJpaController().destroy(current.getDirectoresPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DirectoresDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getDirectoresCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findDirectoresEntities(1, selectedItemIndex).get(0);
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
        return JsfUtil.getSelectItems(getJpaController().findDirectoresEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findDirectoresEntities(), true);
    }

    @FacesConverter(forClass = Directores.class)
    public static class DirectoresControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DirectoresController controller = (DirectoresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "directoresController");
            return controller.getJpaController().findDirectores(getKey(value));
        }

        Entities.DirectoresPK getKey(String value) {
            Entities.DirectoresPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.DirectoresPK();
            key.setIdPrograma(values[0]);
            key.setIdJornada(values[1]);
            return key;
        }

        String getStringKey(Entities.DirectoresPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPrograma());
            sb.append(SEPARATOR);
            sb.append(value.getIdJornada());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Directores) {
                Directores o = (Directores) object;
                return getStringKey(o.getDirectoresPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Directores.class.getName());
            }
        }

    }

}

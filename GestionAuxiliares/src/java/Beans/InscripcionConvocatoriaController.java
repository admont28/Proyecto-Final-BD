package Beans;

import Entities.InscripcionConvocatoria;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.InscripcionConvocatoriaJpaController;

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

@ManagedBean(name = "inscripcionConvocatoriaController")
@SessionScoped
public class InscripcionConvocatoriaController implements Serializable {

    private InscripcionConvocatoria current;
    private DataModel items = null;
    private InscripcionConvocatoriaJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public InscripcionConvocatoriaController() {
    }

    public InscripcionConvocatoria getSelected() {
        if (current == null) {
            current = new InscripcionConvocatoria();
            current.setInscripcionConvocatoriaPK(new Entities.InscripcionConvocatoriaPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private InscripcionConvocatoriaJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new InscripcionConvocatoriaJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getInscripcionConvocatoriaCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findInscripcionConvocatoriaEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/inscripcionConvocatoria/List";
    }

    public String prepareView() {
        current = (InscripcionConvocatoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/inscripcionConvocatoria/View";
    }

    public String prepareCreate() {
        current = new InscripcionConvocatoria();
        current.setInscripcionConvocatoriaPK(new Entities.InscripcionConvocatoriaPK());
        selectedItemIndex = -1;
        return "/Pages/inscripcionConvocatoria/Create";
    }

    public String create() {
        try {
            current.getInscripcionConvocatoriaPK().setIdConvocatoria(current.getConvocatoria().getConvocatoriaId());
            current.getInscripcionConvocatoriaPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InscripcionConvocatoriaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (InscripcionConvocatoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/inscripcionConvocatoria/Edit";
    }

    public String update() {
        try {
            current.getInscripcionConvocatoriaPK().setIdConvocatoria(current.getConvocatoria().getConvocatoriaId());
            current.getInscripcionConvocatoriaPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InscripcionConvocatoriaUpdated"));
            return "/Pages/inscripcionConvocatoria/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (InscripcionConvocatoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/inscripcionConvocatoria/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/inscripcionConvocatoria/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/inscripcionConvocatoria/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getInscripcionConvocatoriaPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InscripcionConvocatoriaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getInscripcionConvocatoriaCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findInscripcionConvocatoriaEntities(1, selectedItemIndex).get(0);
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
        return JsfUtil.getSelectItems(getJpaController().findInscripcionConvocatoriaEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findInscripcionConvocatoriaEntities(), true);
    }

    @FacesConverter(forClass = InscripcionConvocatoria.class)
    public static class InscripcionConvocatoriaControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InscripcionConvocatoriaController controller = (InscripcionConvocatoriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inscripcionConvocatoriaController");
            return controller.getJpaController().findInscripcionConvocatoria(getKey(value));
        }

        Entities.InscripcionConvocatoriaPK getKey(String value) {
            Entities.InscripcionConvocatoriaPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.InscripcionConvocatoriaPK();
            key.setIdAuxiliar(values[0]);
            key.setIdConvocatoria(values[1]);
            return key;
        }

        String getStringKey(Entities.InscripcionConvocatoriaPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdAuxiliar());
            sb.append(SEPARATOR);
            sb.append(value.getIdConvocatoria());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof InscripcionConvocatoria) {
                InscripcionConvocatoria o = (InscripcionConvocatoria) object;
                return getStringKey(o.getInscripcionConvocatoriaPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + InscripcionConvocatoria.class.getName());
            }
        }

    }

}

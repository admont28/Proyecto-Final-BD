package Beans;

import Entities.Hora;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.HoraJpaController;

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

@ManagedBean(name = "horaController")
@SessionScoped
public class HoraController implements Serializable {

    private Hora current;
    private DataModel items = null;
    private HoraJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public HoraController() {
    }

    public Hora getSelected() {
        if (current == null) {
            current = new Hora();
            current.setHoraPK(new Entities.HoraPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private HoraJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new HoraJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getHoraCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findHoraEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/hora/List";
    }

    public String prepareView() {
        current = (Hora) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/hora/View";
    }

    public String prepareCreate() {
        current = new Hora();
        current.setHoraPK(new Entities.HoraPK());
        selectedItemIndex = -1;
        return "/Pages/hora/Create";
    }

    public String create() {
        try {
            current.getHoraPK().setIdFechaHorario(current.getFechaHorario().getFechaHorarioId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HoraCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Hora) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/hora/Edit";
    }

    public String update() {
        try {
            current.getHoraPK().setIdFechaHorario(current.getFechaHorario().getFechaHorarioId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HoraUpdated"));
            return "/Pages/hora/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Hora) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/hora/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/hora/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/hora/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getHoraPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HoraDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getHoraCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findHoraEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/hora/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/hora/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findHoraEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findHoraEntities(), true);
    }

    @FacesConverter(forClass = Hora.class)
    public static class HoraControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HoraController controller = (HoraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "horaController");
            return controller.getJpaController().findHora(getKey(value));
        }

        Entities.HoraPK getKey(String value) {
            Entities.HoraPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.HoraPK();
            key.setHoraInicio(values[0]);
            key.setIdFechaHorario(values[1]);
            return key;
        }

        String getStringKey(Entities.HoraPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getHoraInicio());
            sb.append(SEPARATOR);
            sb.append(value.getIdFechaHorario());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Hora) {
                Hora o = (Hora) object;
                return getStringKey(o.getHoraPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Hora.class.getName());
            }
        }

    }

}

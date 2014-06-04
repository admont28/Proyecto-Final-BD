package Beans;

import Entities.AuxCumplimientoActividades;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.AuxCumplimientoActividadesJpaController;

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

@ManagedBean(name = "auxCumplimientoActividadesController")
@SessionScoped
public class AuxCumplimientoActividadesController implements Serializable {

    private AuxCumplimientoActividades current;
    private DataModel items = null;
    private AuxCumplimientoActividadesJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AuxCumplimientoActividadesController() {
    }

    public AuxCumplimientoActividades getSelected() {
        if (current == null) {
            current = new AuxCumplimientoActividades();
            current.setAuxCumplimientoActividadesPK(new Entities.AuxCumplimientoActividadesPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private AuxCumplimientoActividadesJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new AuxCumplimientoActividadesJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getAuxCumplimientoActividadesCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findAuxCumplimientoActividadesEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/auxCumplimientoActividades/List";
    }

    public String prepareView() {
        current = (AuxCumplimientoActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/auxCumplimientoActividades/View";
    }

    public String prepareCreate() {
        current = new AuxCumplimientoActividades();
        current.setAuxCumplimientoActividadesPK(new Entities.AuxCumplimientoActividadesPK());
        selectedItemIndex = -1;
        return "/Pages/auxCumplimientoActividades/Create";
    }

    public String create() {
        try {
            current.getAuxCumplimientoActividadesPK().setIdCumplimientoActividades(current.getCumplimientoActividades().getCumpActId());
            current.getAuxCumplimientoActividadesPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuxCumplimientoActividadesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (AuxCumplimientoActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/auxCumplimientoActividades/Edit";
    }

    public String update() {
        try {
            current.getAuxCumplimientoActividadesPK().setIdCumplimientoActividades(current.getCumplimientoActividades().getCumpActId());
            current.getAuxCumplimientoActividadesPK().setIdAuxiliar(current.getAuxiliar().getAuxiliarId());
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuxCumplimientoActividadesUpdated"));
            return "/Pages/auxCumplimientoActividades/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (AuxCumplimientoActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/auxCumplimientoActividades/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/auxCumplimientoActividades/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/auxCumplimientoActividades/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getAuxCumplimientoActividadesPK());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuxCumplimientoActividadesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getAuxCumplimientoActividadesCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findAuxCumplimientoActividadesEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/auxCumplimientoActividades/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/auxCumplimientoActividades/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAuxCumplimientoActividadesEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAuxCumplimientoActividadesEntities(), true);
    }

    @FacesConverter(forClass = AuxCumplimientoActividades.class)
    public static class AuxCumplimientoActividadesControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AuxCumplimientoActividadesController controller = (AuxCumplimientoActividadesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "auxCumplimientoActividadesController");
            return controller.getJpaController().findAuxCumplimientoActividades(getKey(value));
        }

        Entities.AuxCumplimientoActividadesPK getKey(String value) {
            Entities.AuxCumplimientoActividadesPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entities.AuxCumplimientoActividadesPK();
            key.setIdAuxiliar(values[0]);
            key.setIdCumplimientoActividades(values[1]);
            return key;
        }

        String getStringKey(Entities.AuxCumplimientoActividadesPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdAuxiliar());
            sb.append(SEPARATOR);
            sb.append(value.getIdCumplimientoActividades());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AuxCumplimientoActividades) {
                AuxCumplimientoActividades o = (AuxCumplimientoActividades) object;
                return getStringKey(o.getAuxCumplimientoActividadesPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AuxCumplimientoActividades.class.getName());
            }
        }

    }

}

package Beans;

import Entities.HorarioActividades;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.HorarioActividadesJpaController;

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

@ManagedBean(name = "horarioActividadesController")
@SessionScoped
public class HorarioActividadesController implements Serializable {

    private HorarioActividades current;
    private DataModel items = null;
    private HorarioActividadesJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public HorarioActividadesController() {
    }

    public HorarioActividades getSelected() {
        if (current == null) {
            current = new HorarioActividades();
            selectedItemIndex = -1;
        }
        return current;
    }

    private HorarioActividadesJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new HorarioActividadesJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getHorarioActividadesCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findHorarioActividadesEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/horarioActividades/List";
    }

    public String prepareView() {
        current = (HorarioActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/horarioActividades/View";
    }

    public String prepareCreate() {
        current = new HorarioActividades();
        selectedItemIndex = -1;
        return "/Pages/horarioActividades/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HorarioActividadesCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (HorarioActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/horarioActividades/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HorarioActividadesUpdated"));
            return "/Pages/horarioActividades/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (HorarioActividades) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/horarioActividades/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/horarioActividades/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/horarioActividades/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getHorarioActividadesId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("HorarioActividadesDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getHorarioActividadesCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findHorarioActividadesEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/horarioActividades/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/horarioActividades/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findHorarioActividadesEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findHorarioActividadesEntities(), true);
    }

    @FacesConverter(forClass = HorarioActividades.class)
    public static class HorarioActividadesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HorarioActividadesController controller = (HorarioActividadesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "horarioActividadesController");
            return controller.getJpaController().findHorarioActividades(getKey(value));
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
            if (object instanceof HorarioActividades) {
                HorarioActividades o = (HorarioActividades) object;
                return getStringKey(o.getHorarioActividadesId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + HorarioActividades.class.getName());
            }
        }

    }

}

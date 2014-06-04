package Beans;

import Entities.EvaluacionAuxiliares;
import Beans.util.JsfUtil;
import Beans.util.PaginationHelper;
import Controller.EvaluacionAuxiliaresJpaController;

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

@ManagedBean(name = "evaluacionAuxiliaresController")
@SessionScoped
public class EvaluacionAuxiliaresController implements Serializable {

    private EvaluacionAuxiliares current;
    private DataModel items = null;
    private EvaluacionAuxiliaresJpaController jpaController = null;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EvaluacionAuxiliaresController() {
    }

    public EvaluacionAuxiliares getSelected() {
        if (current == null) {
            current = new EvaluacionAuxiliares();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EvaluacionAuxiliaresJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new EvaluacionAuxiliaresJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getEvaluacionAuxiliaresCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findEvaluacionAuxiliaresEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "/Pages/evaluacionAuxiliares/List";
    }

    public String prepareView() {
        current = (EvaluacionAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/evaluacionAuxiliares/View";
    }

    public String prepareCreate() {
        current = new EvaluacionAuxiliares();
        selectedItemIndex = -1;
        return "/Pages/evaluacionAuxiliares/Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EvaluacionAuxiliaresCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (EvaluacionAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/Pages/evaluacionAuxiliares/Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EvaluacionAuxiliaresUpdated"));
            return "/Pages/evaluacionAuxiliares/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (EvaluacionAuxiliares) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/Pages/evaluacionAuxiliares/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/Pages/evaluacionAuxiliares/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/Pages/evaluacionAuxiliares/List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getEvaluacionAuxiliaresId());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EvaluacionAuxiliaresDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getEvaluacionAuxiliaresCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findEvaluacionAuxiliaresEntities(1, selectedItemIndex).get(0);
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
        return "/Pages/evaluacionAuxiliares/List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "/Pages/evaluacionAuxiliares/List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findEvaluacionAuxiliaresEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findEvaluacionAuxiliaresEntities(), true);
    }

    @FacesConverter(forClass = EvaluacionAuxiliares.class)
    public static class EvaluacionAuxiliaresControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EvaluacionAuxiliaresController controller = (EvaluacionAuxiliaresController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "evaluacionAuxiliaresController");
            return controller.getJpaController().findEvaluacionAuxiliares(getKey(value));
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
            if (object instanceof EvaluacionAuxiliares) {
                EvaluacionAuxiliares o = (EvaluacionAuxiliares) object;
                return getStringKey(o.getEvaluacionAuxiliaresId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EvaluacionAuxiliares.class.getName());
            }
        }

    }

}

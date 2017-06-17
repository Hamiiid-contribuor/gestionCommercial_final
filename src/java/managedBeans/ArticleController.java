package managedBeans;

import entities.Article;
import managedBeans.util.JsfUtil;
import managedBeans.util.JsfUtil.PersistAction;
import sessionBeans.ArticleFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("articleController")
@SessionScoped
public class ArticleController implements Serializable {

    @EJB
    private sessionBeans.ArticleFacade ejbFacade;
    private List<Article> items = null;
    private Article selected;

    public void addArticle() {

        int code = ejbFacade.addArticle(selected);
        if (code > 0) {
            items.add(selected);
            selected = new Article();
        }
        showAddEditArticleErrors(code);
    }

    public void editArticle() {

        int code = ejbFacade.editArticle(selected);
        if (code > 0) {
            items.add(items.indexOf(selected), selected);
            selected = new Article();
        }
        showAddEditArticleErrors(code);
    }

    public void removeArticle(Article article) {

        int code = ejbFacade.removeArticle(article);
        if (code > 0) {
            items.remove(items.indexOf(selected));
            selected = new Article();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ARTICLE_DELETE_SUCCESS"));
        }
    }

    public void showAddEditArticleErrors(int code) {
        if (code > 0) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ARTICLE_CREATE_EDIT_SUCCESS"));
        }
        if (code == -1) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ARTICLE_CREATE_EDIT_REFERENCE_NULL"));
        }
        if (code == -2) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ARTICLE_CREATE_EDIT_DESIGNATION_NULL"));
        }
        if (code == -3) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ARTICLE_CREATE_EDIT_PRIXHT_NULL"));
        }
        if (code == -4) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ARTICLE_CREATE_EDIT_CATEGORIE_NULL"));
        }

    }

    public ArticleController() {
    }

    public Article getSelected() {
        if (selected == null) {
            selected = new Article();
        }
        return selected;
    }

    public void setSelected(Article selected) {
        this.selected = selected;
    }

    private ArticleFacade getFacade() {
        return ejbFacade;
    }

    public List<Article> getItems() {
        if (items == null) {
            items = ejbFacade.allArticles();
        }
        return items;
    }

    //----------------------------------------------------------------------------
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Article prepareCreate() {
        selected = new Article();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ArticleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ArticleUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ArticleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Article getArticle(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Article> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Article> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Article.class)
    public static class ArticleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArticleController controller = (ArticleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "articleController");
            return controller.getArticle(getKey(value));
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
            if (object instanceof Article) {
                Article o = (Article) object;
                return getStringKey(o.getReference());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Article.class.getName()});
                return null;
            }
        }

    }

}

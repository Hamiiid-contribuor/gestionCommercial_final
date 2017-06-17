/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Article;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lidrissi Hamid
 */
@Stateless
public class ArticleFacade extends AbstractFacade<Article> {

    @PersistenceContext(unitName = "gestionCommercial_finalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticleFacade() {
        super(Article.class);
    }

    public int removeArticle(Article article) {

        if (article != null) {
            remove(article);
            return 1;
        }
        return -1;
    }

    public int editArticle(Article article) {
        int res = verifyAddEditArticle(article);
        if (res > 0) {
            edit(article);
        }
        return res;
    }

    public int addArticle(Article article) {
        int res = verifyAddEditArticle(article);
        if (res > 0) {
            create(article);
        }
        return res;
    }

    public List<Article> allArticles() {
        return findAll();
    }

    public int verifyAddEditArticle(Article article) {
        if (article.getReference() == null) {
            return -1;
        }
        if (article.getDesignation() == null) {
            return -2;
        }
        if (article.getPrixHT() == null) {
            return -3;
        }
        if (article.getCategorie() == null) {
            return -4;
        }
        return 1;
    }
}

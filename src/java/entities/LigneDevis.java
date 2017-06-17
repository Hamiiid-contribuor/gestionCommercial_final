/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Lidrissi Hamid
 */
@Entity
public class LigneDevis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double tva = new Double(20);
    private int qte;
    private Double prixTTC;

    @ManyToOne
    private Devis devis;

    @OneToOne
    private Article article;

    public LigneDevis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Devis getDevis() {
        if (devis == null) {
            devis = new Devis();
        }
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public Article getArticle() {
        if (article == null) {
            article = new Article();
        }
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Double getPrixTTC() {
        if (prixTTC == null) {
            prixTTC = new Double(0);
        }
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
    }

}

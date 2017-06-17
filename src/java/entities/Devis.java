/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Lidrissi Hamid
 */
@Entity
public class Devis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long reference;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDevis = new Date();

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "devis")
    private List<LigneDevis> lignesDevis;

    public Devis() {
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Date getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(Date dateDevis) {
        this.dateDevis = dateDevis;
    }

    public Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneDevis> getLignesDevis() {
        if (lignesDevis == null) {
            lignesDevis = new ArrayList<>();
        }
        return lignesDevis;
    }

    public void setLignesDevis(List<LigneDevis> lignesDevis) {
        this.lignesDevis = lignesDevis;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> ligneCommandes;
    @Temporal(TemporalType.DATE)
    private Date dateCommande;

    public Commande() {}
    public Commande(Date date) { 
        this.date = date; 
        this.dateCommande = date; 
    }
    public int getId() { return id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Date getDateCommande() {
        return dateCommande;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    // autres attributs, par ex. List<LigneCommande> lignes

    // constructeur(s), getters et setters

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }
    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}

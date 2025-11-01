/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

/**
 *
 * @author hp
 */

import javax.persistence.*;

@Entity
@Table(name = "ligne_commandes")
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantite;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    public LigneCommande() {}
    public LigneCommande(Produit produit, Commande commande, int quantite) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
    }

    public int getQuantite() { return quantite; }
    public Produit getProduit() { return produit; }
}

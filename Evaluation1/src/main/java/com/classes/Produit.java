/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import javax.persistence.*;

@NamedQuery(name = "Produit.findPrixSup100", query = "SELECT p FROM Produit p WHERE p.prix > 100")
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private double prix;
    private int quantite;

    @ManyToOne
    private Categorie categorie;

    // ✅ Constructeur vide
    public Produit() {
    }

    // ✅ Constructeur complet
   public Produit(String reference, int prix, Categorie categorie) {
    this.reference = reference;
    this.prix = prix;
    this.quantite = 1; // valeur par défaut
    this.categorie = categorie;
}

    // ✅ Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", categorie=" + (categorie != null ? categorie.getNom() : "Aucune") +
                '}';
    }
}

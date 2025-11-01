/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

/**
 *
 * @author hp
 */




import com.classes.*;
import com.services.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class TestApplication {
    public static void main(String[] args) throws Exception {
        // Initialisation des services
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        // Création des catégories
        Categorie cat1 = new Categorie("Ordinateurs", "PC portables et de bureau");
        Categorie cat2 = new Categorie("Accessoires", "Souris et claviers");
        categorieService.create(cat1);
        categorieService.create(cat2);

        // Création des produits
        Produit p1 = new Produit("ES12", 120, cat1);
        Produit p2 = new Produit("ZR85", 100, cat1);
        Produit p3 = new Produit("EE85", 200, cat2);
        Produit p4 = new Produit("AA77", 80, cat2);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);

        // Afficher produits par catégorie
        System.out.println("\n--- Produits de la catégorie Ordinateurs ---");
        for (Produit p : produitService.getProduitsByCategorie(cat1)) {
            System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
        }

        // Création des commandes
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
        Commande cmd2 = new Commande(sdf.parse("20/03/2013"));
        commandeService.create(cmd1);
        commandeService.create(cmd2);

        // Création des lignes de commande
        ligneCommandeService.create(new LigneCommande(p1, cmd1, 7));
        ligneCommandeService.create(new LigneCommande(p2, cmd1, 14));
        ligneCommandeService.create(new LigneCommande(p3, cmd1, 5));
        ligneCommandeService.create(new LigneCommande(p4, cmd2, 10));

        // Afficher les produits d'une commande
        System.out.println("\n--- Produits de la commande 1 ---");
        commandeService.afficherProduitsCommande(cmd1.getId());

        // Commandes entre deux dates
        System.out.println("\n--- Commandes entre 01/03/2013 et 31/03/2013 ---");
        List<Commande> commandes = commandeService.getCommandesBetweenDates(
                sdf.parse("01/03/2013"), sdf.parse("31/03/2013"));
        commandes.forEach(c -> System.out.println("Commande " + c.getId() + " - " + c.getDateCommande()));

        // Produits dont le prix > 100 DH
        System.out.println("\n--- Produits avec prix > 100 DH ---");
        produitService.getProduitsPrixSup100()
                .forEach(p -> System.out.println(p.getReference() + " - " + p.getPrix() + " DH"));
    }
}


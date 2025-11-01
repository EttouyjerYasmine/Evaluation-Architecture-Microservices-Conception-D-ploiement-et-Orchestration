/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

/**
 *
 * @author hp
 */


import com.classes.Commande;
import com.classes.LigneCommande;
import com.dao.Idao;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class CommandeService implements Idao<Commande> {

    @Override
    public void create(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(c);
        tx.commit();
        s.close();
    }

    @Override
    public Commande getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Commande c = s.get(Commande.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Commande> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Commande> list = s.createQuery("from Commande", Commande.class).list();
        s.close();
        return list;
    }

    @Override
    public void update(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(c);
        tx.commit();
        s.close();
    }

    @Override
    public void delete(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.delete(c);
        tx.commit();
        s.close();
    }

    // ✅ Afficher les produits commandés entre deux dates
    public List<Commande> getCommandesBetweenDates(Date dateDebut, Date dateFin) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query<Commande> q = s.createQuery(
                "from Commande c where c.dateCommande between :d1 and :d2", Commande.class);
        q.setParameter("d1", dateDebut);
        q.setParameter("d2", dateFin);
        List<Commande> list = q.list();
        s.close();
        return list;
    }

    // ✅ Afficher les produits d’une commande donnée
    public void afficherProduitsCommande(int idCommande) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Commande commande = s.get(Commande.class, idCommande);

        if (commande != null) {
            System.out.println("Commande : " + commande.getId() +
                    "\tDate : " + commande.getDateCommande());
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\tQuantité");

            for (LigneCommande lc : commande.getLigneCommandes()) {
                System.out.println(
                        lc.getProduit().getReference() + "\t\t" +
                                lc.getProduit().getPrix() + " DH\t" +
                                lc.getQuantite());
            }
        } else {
            System.out.println(" Commande non trouvée !");
        }

        s.close();
    }
 }
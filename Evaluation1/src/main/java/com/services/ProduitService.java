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

import com.classes.*;
import com.dao.Idao;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ProduitService implements Idao<Produit> {

    @Override
    public void create(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(p);
        tx.commit();
        s.close();
    }

    @Override
    public Produit getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Produit p = s.get(Produit.class, id);
        s.close();
        return p;
    }

    @Override
    public List<Produit> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createQuery("from Produit", Produit.class).list();
        s.close();
        return list;
    }

    @Override
    public void update(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(p);
        tx.commit();
        s.close();
    }

    @Override
    public void delete(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.delete(p);
        tx.commit();
        s.close();
    }

    // 🔸 Produits par catégorie
    public List<Produit> getProduitsByCategorie(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> q = s.createQuery("FROM Produit p WHERE p.categorie = :cat", Produit.class);
        q.setParameter("cat", c);
        List<Produit> list = q.list();
        s.close();
        return list;
    }

    // 🔸 Produits commandés entre deux dates
    public List<Produit> getProduitsBetweenDates(Date d1, Date d2) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> q = s.createQuery(
            "SELECT lc.produit FROM LigneCommande lc WHERE lc.commande.date BETWEEN :d1 AND :d2",
            Produit.class);
        q.setParameter("d1", d1);
        q.setParameter("d2", d2);
        List<Produit> list = q.list();
        s.close();
        return list;
    }

    // 🔸 Produits d’une commande donnée
    public List<LigneCommande> getProduitsByCommande(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Query<LigneCommande> q = s.createQuery(
            "FROM LigneCommande lc WHERE lc.commande = :cmd", LigneCommande.class);
        q.setParameter("cmd", c);
        List<LigneCommande> list = q.list();
        s.close();
        return list;
    }

    // 🔸 Produits dont le prix > 100 (requête nommée)
    public List<Produit> getProduitsPrixSup100() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createNamedQuery("Produit.findPrixSup100", Produit.class).list();
        s.close();
        return list;
    }
}


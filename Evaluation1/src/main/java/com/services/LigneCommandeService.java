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


import com.classes.LigneCommande;
import com.dao.Idao;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LigneCommandeService implements Idao<LigneCommande> {

    @Override
    public void create(LigneCommande lc) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(lc);
        tx.commit();
        s.close();
    }

    @Override
    public LigneCommande getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        LigneCommande lc = s.get(LigneCommande.class, id);
        s.close();
        return lc;
    }

    @Override
    public List<LigneCommande> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommande> list = s.createQuery("from LigneCommande", LigneCommande.class).list();
        s.close();
        return list;
    }

    @Override
    public void update(LigneCommande lc) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(lc);
        tx.commit();
        s.close();
    }

    @Override
    public void delete(LigneCommande lc) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.delete(lc);
        tx.commit();
        s.close();
    }
}

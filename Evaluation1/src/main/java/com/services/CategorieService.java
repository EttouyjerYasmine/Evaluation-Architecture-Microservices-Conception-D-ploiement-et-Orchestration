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


import com.util.HibernateUtil;
import com.classes.Categorie;
import com.dao.Idao;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class CategorieService implements Idao<Categorie> {

    @Override
    public void create(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(c);
        tx.commit();
        s.close();
    }

    @Override
    public Categorie getById(int id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Categorie c = s.get(Categorie.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Categorie> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> list = s.createQuery("from Categorie", Categorie.class).list();
        s.close();
        return list;
    }

    @Override
    public void update(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(c);
        tx.commit();
        s.close();
    }

    @Override
    public void delete(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.delete(c);
        tx.commit();
        s.close();
    }
}

package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HommeService extends BaseService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
        return tx(s -> { s.save(o); return true; });
    }

    @Override
    public boolean update(Homme o) {
        return tx(s -> { s.update(o); return true; });
    }

    @Override
    public boolean delete(Homme o) {
        return tx(s -> { s.delete(o); return true; });
    }

    @Override
    public Homme findById(Long id) {
        Session s = getSession();
        try { return s.get(Homme.class, id); } finally { s.close(); }
    }

    @Override
    public List<Homme> findAll() {
        Session s = getSession();
        try { return s.createQuery("from Homme", Homme.class).list(); } finally { s.close(); }
    }

    // Afficher les épouses d’un homme entre deux dates (par date de debut mariage)
    public List<Femme> epousesEntreDates(Long hommeId, Date d1, Date d2) {
        Session s = getSession();
        try {
            String jpql = "select m.femme from Mariage m where m.homme.id = :hid and m.dateDebut between :d1 and :d2";
            Query<Femme> q = s.createQuery(jpql, Femme.class);
            q.setParameter("hid", hommeId);
            q.setParameter("d1", d1);
            q.setParameter("d2", d2);
            return q.list();
        } finally {
            s.close();
        }
    }

    // Détails des mariages d'un homme donné
    public List<Mariage> mariagesAvecDetails(Long hommeId) {
        Session s = getSession();
        try {
            String jpql = "select m from Mariage m join fetch m.femme where m.homme.id = :hid order by m.dateDebut";
            return s.createQuery(jpql, Mariage.class).setParameter("hid", hommeId).list();
        } finally { s.close(); }
    }
}

package ma.projet.service;

import java.util.Date;
import java.util.List;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class TacheService extends BaseService<Tache> {
    public TacheService() { super(Tache.class); }

    public List<Tache> findByPriceGreaterThan(double minPrice) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.getNamedQuery("Tache.findPriceGreaterThan").setDouble("minPrice", minPrice).list();
        }
    }

    public List<Tache> findRealizedBetween(Date start, Date end) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Tache t where t.dateDebutReelle >= :d1 and t.dateFinReelle <= :d2")
                    .setDate("d1", start)
                    .setDate("d2", end)
                    .list();
        }
    }
}

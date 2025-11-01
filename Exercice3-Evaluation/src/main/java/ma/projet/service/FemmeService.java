package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;

public class FemmeService extends BaseService implements IDao<Femme> {
    @Override
    public boolean create(Femme o) { return tx(s -> { s.save(o); return true; }); }
    @Override
    public boolean update(Femme o) { return tx(s -> { s.update(o); return true; }); }
    @Override
    public boolean delete(Femme o) { return tx(s -> { s.delete(o); return true; }); }

    @Override
    public Femme findById(Long id) { Session s = getSession(); try { return s.get(Femme.class, id); } finally { s.close(); } }

    @Override
    public List<Femme> findAll() { Session s = getSession(); try { return s.createQuery("from Femme", Femme.class).list(); } finally { s.close(); } }

    // Requête native nommée: nombre d'enfants d'une femme entre deux dates
    public long nombreEnfantsEntreDates(Long femmeId, Date d1, Date d2) {
        Session s = getSession();
        try {
            NativeQuery<?> q = s.getNamedNativeQuery("Femme.countEnfantsBetweenDates");
            q.setParameter("femmeId", femmeId);
            q.setParameter("d1", d1);
            q.setParameter("d2", d2);
            Object res = q.getSingleResult();
            if (res == null) return 0L;
            if (res instanceof Number) return ((Number) res).longValue();
            return Long.parseLong(res.toString());
        } finally { s.close(); }
    }

    // Requête nommée: femmes mariées au moins deux fois
    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        Session s = getSession();
        try { return s.createNamedQuery("Femme.marriedAtLeastTwice", Femme.class).getResultList(); }
        finally { s.close(); }
    }

    // Criteria API: nombre d'hommes mariés à quatre femmes entre deux dates
    public long nombreHommesMarieesAQuatreFemmesEntre(Date d1, Date d2) {
        Session s = getSession();
        try {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            // Subquery to select hommes with exactly 4 distinct femmes between dates
            Subquery<Long> sub = cq.subquery(Long.class);
            Root<Mariage> m = sub.from(Mariage.class);
            sub.select(m.get("homme").get("id"));
            sub.where(cb.between(m.get("dateDebut"), d1, d2));
            sub.groupBy(m.get("homme").get("id"));
            sub.having(cb.equal(cb.countDistinct(m.get("femme").get("id")), 4L));

            // Count rows in subquery
            Root<Homme> h = cq.from(Homme.class);
            cq.select(cb.count(h));
            cq.where(h.get("id").in(sub));

            return s.createQuery(cq).getSingleResult();
        } finally { s.close(); }
    }
}

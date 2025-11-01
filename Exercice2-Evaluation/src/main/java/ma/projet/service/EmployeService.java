package ma.projet.service;

import java.util.ArrayList;
import java.util.List;
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class EmployeService extends BaseService<Employe> {
    public EmployeService() { super(Employe.class); }

    public List<Tache> listTasksByEmployee(Long employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> ets = session.createQuery("from EmployeTache et where et.employe.id = :eid")
                    .setLong("eid", employeId).list();
            List<Tache> res = new ArrayList<Tache>();
            for (EmployeTache et : ets) res.add(et.getTache());
            return res;
        }
    }

    public List<ma.projet.classes.Projet> listProjectsManagedByEmployee(Long employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Projet p where p.gerant.id = :eid").setLong("eid", employeId).list();
        }
    }
}

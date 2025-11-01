package ma.projet.service;

import java.util.List;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class ProjetService extends BaseService<Projet> {
    public ProjetService() { super(Projet.class); }

    public List<Tache> listPlannedTasksByProject(Long projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Tache t where t.projet.id = :pid").setLong("pid", projetId).list();
        }
    }

    public List<Tache> listRealizedTasksWithRealDates(Long projetId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Tache t where t.projet.id = :pid and t.dateDebutReelle is not null and t.dateFinReelle is not null").setLong("pid", projetId).list();
        }
    }
}

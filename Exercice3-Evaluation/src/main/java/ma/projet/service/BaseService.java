package ma.projet.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;

public abstract class BaseService {
    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    protected interface Work<T> { T run(Session s); }

    protected <T> T tx(Work<T> work) {
        Session s = getSession();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            T res = work.run(s);
            t.commit();
            return res;
        } catch (RuntimeException e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            s.close();
        }
    }
}

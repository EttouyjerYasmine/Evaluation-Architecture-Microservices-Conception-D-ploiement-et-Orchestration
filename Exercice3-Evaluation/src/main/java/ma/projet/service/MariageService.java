package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;

import java.util.List;

public class MariageService extends BaseService implements IDao<Mariage> {
    @Override
    public boolean create(Mariage o) { return tx(s -> { s.save(o); return true; }); }
    @Override
    public boolean update(Mariage o) { return tx(s -> { s.update(o); return true; }); }
    @Override
    public boolean delete(Mariage o) { return tx(s -> { s.delete(o); return true; }); }

    @Override
    public Mariage findById(Long id) { Session s = getSession(); try { return s.get(Mariage.class, id); } finally { s.close(); } }

    @Override
    public List<Mariage> findAll() { Session s = getSession(); try { return s.createQuery("from Mariage", Mariage.class).list(); } finally { s.close(); } }
}

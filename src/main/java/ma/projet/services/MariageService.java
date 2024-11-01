package ma.projet.services;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MariageService implements IDao<Mariage> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Mariage mariage) {
        Transaction tx = null;
        boolean status = false;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(mariage);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Mariage getById(int id) {
        Transaction tx = null;
        Mariage mariage = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            mariage = session.get(Mariage.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return mariage;
    }

    @Override
    public List<Mariage> getAll() {
        Transaction tx = null;
        List<Mariage> mariages = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            mariages = session.createQuery("from Mariage", Mariage.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return mariages;
    }
}

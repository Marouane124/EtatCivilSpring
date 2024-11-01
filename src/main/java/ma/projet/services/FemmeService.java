package ma.projet.services;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Service
public class FemmeService implements IDao<Femme> {

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Femme femme) {
        Transaction tx = null;
        boolean status = false;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(femme);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Femme getById(int id) {
        Transaction tx = null;
        Femme femme = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            femme = session.get(Femme.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return femme;
    }

    @Override
    public List<Femme> getAll() {
        Transaction tx = null;
        List<Femme> femmes = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            femmes = session.createQuery("from Femme", Femme.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return femmes;
    }

    /*public int countEnfantsByFemmeAndDates(int femmeId, Date startDate, Date endDate) {
        Integer count = 0;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            count = (Integer) session.createNamedQuery("Mariage.countEnfantsByFemmeAndDates")
                    .setParameter("femmeId", femmeId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return count;
    }*/

    public List<Femme> findMarriedMoreThanTwice() {
        TypedQuery<Femme> query = entityManager.createNamedQuery("Femme.findMarriedMoreThanTwice", Femme.class);
        return query.getResultList();
    }

}

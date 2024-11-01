package ma.projet.services;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HommeService implements IDao<Homme> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Homme homme) {
        Transaction tx = null;
        boolean status = false;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(homme);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Homme getById(int id) {
        Transaction tx = null;
        Homme homme = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            homme = session.get(Homme.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return homme;
    }

    @Override
    public List<Homme> getAll() {
        Transaction tx = null;
        List<Homme> hommes = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            hommes = session.createQuery("from Homme", Homme.class).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return hommes;
    }

    public List<Femme> getEpousesEntreDates(Homme homme, Date dateDebut, Date dateFin) {
        Transaction tx = null;
        List<Femme> epouses = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            List<Mariage> mariages = session.createQuery(
                            "from Mariage m where m.homme = :homme and m.dateDebut >= :dateDebut and m.dateFin <= :dateFin",
                            Mariage.class)
                    .setParameter("homme", homme)
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
                    .getResultList();
            for (Mariage mariage : mariages) {
                epouses.add(mariage.getFemme());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return epouses;
    }

    /*public Long countHommesMarriedToFourFemmesBetweenDates(Date startDate, Date endDate) {
        Long count = 0L;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Mariage.class, "mariage");
            criteria.createAlias("mariage.homme", "homme");

            criteria.setProjection(Projections.projectionList()
                    .add(Projections.groupProperty("homme.id"))
                    .add(Projections.rowCount(), "nombreFemmes"));

            criteria.add(Restrictions.ge("mariage.dateDebut", startDate));
            criteria.add(Restrictions.le("mariage.dateFin", endDate));

            criteria.add(Restrictions.eq("nombreFemmes", 4));

            count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }*/

    /*public void afficherMariagesDunHomme(int hommeId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            String queryEnCours = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL";
            Query<Mariage> query1 = session.createQuery(queryEnCours, Mariage.class);
            query1.setParameter("hommeId", hommeId);
            List<Mariage> mariagesEnCours = query1.getResultList();

            String queryEchoues = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL";
            Query<Mariage> query2 = session.createQuery(queryEchoues, Mariage.class);
            query2.setParameter("hommeId", hommeId);
            List<Mariage> mariagesEchoues = query2.getResultList();

            tx.commit();

            Homme homme = session.get(Homme.class, hommeId);
            System.out.println("Nom: " + homme.getNom());
            System.out.println("Mariages En Cours :");
            int index = 1;
            for (Mariage mariage : mariagesEnCours) {
                System.out.println(index++ + ". Femme : " + mariage.getFemme().getNom() +
                        " Date Début : " + mariage.getDateDebut() +
                        " Nbr Enfants : " + mariage.getNbrEnfant());
            }

            System.out.println("Mariages échoués :");
            index = 1;
            for (Mariage mariage : mariagesEchoues) {
                System.out.println(index++ + ". Femme : " + mariage.getFemme().getNom() +
                        " Date Début : " + mariage.getDateDebut() +
                        " Date Fin : " + mariage.getDateFin() +
                        " Nbr Enfants : " + mariage.getNbrEnfant());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

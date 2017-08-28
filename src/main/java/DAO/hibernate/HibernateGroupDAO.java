package DAO.hibernate;

import ConModel.Group;
import DAO.GroupDAO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateGroupDAO implements GroupDAO {
    private static final Logger log = Logger.getLogger(HibernateGroupDAO.class);
    private static volatile HibernateGroupDAO instace;

    public static HibernateGroupDAO getInstace() {
        if (instace == null)
            synchronized (HibernateUserDAO.class) {
                if (instace == null)
                    instace = new HibernateGroupDAO();
            }
        return instace;
    }
    private HibernateGroupDAO() {
    }

    @Override
    public void create(Group group) {
        Transaction transaction = null;
        try (Session session = HibernateConnector.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.save(group);
            session.flush();
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("Can't add group", e);
            e.printStackTrace();
        }
    }

    @Override
    public Group read(String id) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            return session.get(Group.class, Integer.parseInt(id));
        } catch (Exception e) {
            log.error("Can't get group", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Group> readAll(String user) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            String queryString = "Select g from Group g where g.user = '" + user + "'";
            Query query = session.createQuery(queryString);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Group newGroup) {
        Transaction transaction = null;
        try (Session session = HibernateConnector.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(newGroup);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            log.error("Can't update group", e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Group group) {
        Transaction transaction = null;
        try (Session session = HibernateConnector.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.delete(group);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            log.error("Can't delete group", e);
            e.printStackTrace();
        }
    }
}

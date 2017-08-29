package DAO.hibernate;

import ConModel.Group;
import DAO.Constants;
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
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            session.save(group);
            session.flush();
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("Can't add group", e);
        }
    }

    @Override
    public Group read(String id) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            return session.get(Group.class, Integer.parseInt(id));
        } catch (Exception e) {
            log.error("Can't get group", e);
            return null;
        }
    }

    @Override
    public List<Group> readAll(String user) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            String queryString = String.format(Constants.groupList, user);
            Query query = session.createQuery(queryString);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Cat't get group list of user "+ user,e);
            return null;
        }
    }

    @Override
    public void update(Group newGroup) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            session.saveOrUpdate(newGroup);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            log.error("Can't update group", e);
        }
    }

    @Override
    public void delete(Group group) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            session.delete(group);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            log.error("Can't delete group", e);
        }
    }
}

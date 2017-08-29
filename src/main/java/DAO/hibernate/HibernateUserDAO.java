package DAO.hibernate;

import ConModel.User;
import DAO.Constants;
import DAO.UserDAO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserDAO implements UserDAO {
    private static final Logger log = Logger.getLogger(HibernateContactDAO.class);
    private static volatile HibernateUserDAO instace;

    private HibernateUserDAO() {
    }

    public static HibernateUserDAO getInstace() {
        if (instace == null)
                synchronized (HibernateUserDAO.class) {
                    if (instace == null)
                        instace = new HibernateUserDAO();
                }
        return instace;
    }

    @Override
    public void create(User ob) {
    }

    @Override
    public User read(String loginid) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            return session.get(User.class, loginid);
        } catch (Exception e) {
            log.error("Can't get user: "+loginid+" from DB",e);
            return null;
        }
    }

    @Override
    public List<User> readAll(String ob) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            String queryString = Constants.allUserList;
            Query query = session.createQuery(queryString);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Can't get user list",e);
            return null;
        }
    }

    @Override
    public void update(User newOb) {
    }

    @Override
    public void delete(User ob) {
    }
}

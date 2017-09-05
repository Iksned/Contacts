package DAO.hibernate;

import model.User;
import DAO.Constants;
import DAO.UserDAO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class HibernateUserDAO implements UserDAO {
    private static final Logger log = Logger.getLogger(HibernateContactDAO.class);
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(User ob) {
    }

    @Override
    public User read(String loginid) {
        try {
            return session().get(User.class, loginid);
        } catch (Exception e) {
            log.error("Can't get user: "+loginid+" from DB",e);
            return null;
        }
    }

    @Override
    public List<User> readAll(String ob) {
        try {
            String queryString = Constants.allUserList;
            Query query = session().createQuery(queryString);
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

package DAO.hibernate;

import model.Contact;
import model.Group;
import DAO.Constants;
import DAO.GroupDAO;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class HibernateGroupDAO implements GroupDAO {
    private static final Logger log = Logger.getLogger(HibernateGroupDAO.class);
    SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(Group group) {
        try {
            session().save(group);
            session().flush();
        } catch (RuntimeException e) {
            log.error("Can't add group", e);
        }
    }

    @Override
    public Group read(String id) {
        try {
            Group group = session().get(Group.class, Integer.parseInt(id));
            User user = group.getUser();
            List<Group> groupList = user.getGrouplist();
            List<Contact> contactList = user.getContactList();
            return group;
        } catch (Exception e) {
            log.error("Can't get group", e);
            return null;
        }
    }

    @Override
    public List<Group> readAll(String user) {
        try {
            String queryString = String.format(Constants.groupList, user);
            Query query = session().createQuery(queryString);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Cat't get group list of user "+ user,e);
            return null;
        }
    }

    @Override
    public void update(Group newGroup) {
        try{
            session().saveOrUpdate(newGroup);
            session().flush();
        } catch (Exception e) {
            log.error("Can't update group", e);
        }
    }

    @Override
    public void delete(Group group) {
        try {
            session().delete(group);
            session().flush();
        } catch (Exception e) {
            log.error("Can't delete group", e);
        }
    }
}

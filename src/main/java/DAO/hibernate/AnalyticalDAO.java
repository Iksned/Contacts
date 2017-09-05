package DAO.hibernate;

import model.Contact;
import model.Group;
import model.User;
import org.hibernate.SessionFactory;
import services.ResultTable;
import DAO.Constants;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class AnalyticalDAO {
    private static final Logger log = Logger.getLogger(AnalyticalDAO.class);
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

 /*   public boolean chekUser(String username, String password){
        log.info("Start checking user");
        log.debug("Attempted username and password "+username + " " + password);
        try (Session session = HibernateConnector.getInstance().getSession()){
            User checkedUser =   session.get(User.class, username);
            return (checkedUser.getPassword().equals(password));
        } catch (Exception e) {
            log.error("Check user error ",e);
            return false;
        }
    } */

    public long countUsers()  {
        try {
            String queryString = Constants.CountallUserList;
            Query query =  session().createQuery(queryString);
            return (Long) query.uniqueResult();
        } catch (Exception e) {
            log.error("Count users error ",e);
            return 0;
        }
    }

    public List<ResultTable> countUsersContacts()  {
        try {
            String queryString = Constants.allUserList;
            Query query =  session().createQuery(queryString);
            List<User> userList = query.getResultList();
            List<ResultTable> result = new ArrayList<>();
            for (User user : userList) {
                result.add(new ResultTable(user.getUsername(), user.getContactList().size()));
            }
            return result;
        } catch (Exception e) {
            log.error("Count users contacts error ",e);
            return null;
        }
    }

    public List<ResultTable> countUsersGroups()  {
        try {
            String queryString = Constants.allUserList;
            Query query =  session().createQuery(queryString);
            List<User> userList = query.getResultList();
            List<ResultTable> result = new ArrayList<>();
            for (User user : userList) {
                result.add(new ResultTable(user.getUsername(), user.getGrouplist().size()));
            }
            return result;
        } catch (Exception e) {
            log.error("Count users groups error ",e);
            return null;
        }
    }

    public int avgContactsInGroups()  {
        try {
            String queryString = Constants.allContactList;
            Query contactQuery =  session().createQuery(queryString);
            List<Contact> contactList = contactQuery.getResultList();
            String queryString2 = Constants.allGroupList;
            Query groupQuery =  session().createQuery(queryString2);
            List<Group> groupList = groupQuery.getResultList();
            return contactList.size() / groupList.size();
        } catch (Exception e) {
            log.error("avg contacts error ",e);
            return 0;
        }
    }

    public int avgUsersContacts()  {
        try {
            String queryString = Constants.allContactList;
            Query contactQuery =  session().createQuery(queryString);
            List<Contact> contactList = contactQuery.getResultList();
            String queryString2 = Constants.allUserList;
            Query userQuery =  session().createQuery(queryString2);
            List<User> userList = userQuery.getResultList();
            return contactList.size() / userList.size();
        } catch (Exception e) {
            log.error("avg users contacts error ",e);
            return 0;
        }
    }

    public List<String> inactiveUsers()  {
        try {
            String queryString2 = Constants.allUserList;
            Query userQuery =  session().createQuery(queryString2);
            List<User> userList = userQuery.getResultList();
            List<String> result = new ArrayList<>();
            for (User user : userList) {
                if (user.getContactList().size() < 10)
                    result.add(user.getUsername());
            }
            return result;
        } catch (Exception e) {
            log.error("inactive user method error ",e);
            return null;
        }
    }
}

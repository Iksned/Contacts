package DAO.hibernate;

import model.Contact;
import DAO.Constants;
import DAO.ContactDAO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class HibernateContactDAO implements ContactDAO{
    private static final Logger log = Logger.getLogger(HibernateContactDAO.class);
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

        @Override
        public void create(Contact contact) {
            try {
                session().save(contact);
                session().flush();
            } catch (RuntimeException e) {
                log.error("Can't add contact", e);
            }
        }

        @Override
        public Contact read(String id) {
            try {
                return session().get(Contact.class, Integer.parseInt(id));
            } catch (Exception e) {
                log.error("Can't get contact", e);
                return null;
            }
        }

        @Override
        public List<Contact> readAll(String user){
            try {
                String queryString = String.format(Constants.contactList, user);
                Query query = session().createQuery(queryString);
                return query.getResultList();
            } catch (Exception e) {
                log.error("Cat't get contact list of user: "+ user,e);
                return null;
            }
        }

        @Override
        public void update(Contact newContact) {
            try {
                session().saveOrUpdate(newContact);
                session().flush();
            } catch (Exception e) {
                log.error("Can't update contact", e);
            }
        }

        @Override
        public void delete(Contact contact) {
            try {
                session().delete(contact);
                session().flush();
            } catch (Exception e) {
                log.error("Can't delete contact", e);
            }
        }
}

package DAO.hibernate;

import ConModel.Contact;
import DAO.Constants;
import DAO.ContactDAO;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateContactDAO implements ContactDAO{
    private static final Logger log = Logger.getLogger(HibernateContactDAO.class);
        private static volatile HibernateContactDAO instace;

        public static HibernateContactDAO getInstace() {
        if (instace == null)
            synchronized (HibernateUserDAO.class) {
                if (instace == null)
                    instace = new HibernateContactDAO();
            }
        return instace;
        }

        private HibernateContactDAO() {
        }

        @Override
        public void create(Contact contact) {
            try (Session session = HibernateConnector.getInstance().getSession()) {
                Transaction transaction = null;
                transaction = session.beginTransaction();
                session.save(contact);
                session.flush();
                transaction.commit();
            } catch (RuntimeException e) {
                log.error("Can't add contact", e);
            }
        }

        @Override
        public Contact read(String id) {
            try (Session session = HibernateConnector.getInstance().getSession()) {
                return session.get(Contact.class, Integer.parseInt(id));
            } catch (Exception e) {
                log.error("Can't get contact", e);
                return null;
            }
        }

        @Override
        public List<Contact> readAll(String user){
            try (Session session = HibernateConnector.getInstance().getSession()) {
                String queryString = String.format(Constants.contactList, user);
                Query query = session.createQuery(queryString);
                return query.getResultList();
            } catch (Exception e) {
                log.error("Cat't get contact list of user: "+ user,e);
                return null;
            }
        }

        @Override
        public void update(Contact newContact) {
            try (Session session = HibernateConnector.getInstance().getSession()) {
                Transaction transaction = null;
                transaction = session.beginTransaction();
                session.saveOrUpdate(newContact);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                log.error("Can't update contact", e);
            }
        }

        @Override
        public void delete(Contact contact) {
            try (Session session = HibernateConnector.getInstance().getSession()) {
                Transaction transaction = null;
                transaction = session.beginTransaction();
                session.delete(contact);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                log.error("Can't delete contact", e);
            }
        }
}

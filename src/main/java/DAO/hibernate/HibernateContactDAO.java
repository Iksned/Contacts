package DAO.hibernate;

import ConModel.Contact;
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
            Transaction transaction = null;
            try (Session session = HibernateConnector.getInstance().getSession()) {
                transaction = session.beginTransaction();
                session.save(contact);
                session.flush();
                transaction.commit();
            } catch (RuntimeException e) {
                log.error("Can't add contact", e);
                e.printStackTrace();
            }
        }

        @Override
        public Contact read(String id) {
            try (Session session = HibernateConnector.getInstance().getSession()) {
                return session.get(Contact.class, Integer.parseInt(id));
            } catch (Exception e) {
                log.error("Can't get contact", e);
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<Contact> readAll(String user){
            try (Session session = HibernateConnector.getInstance().getSession()) {
                String queryString = "Select c from Contact c where c.user = '" + user + "'";
                Query query = session.createQuery(queryString);
                return query.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void update(Contact newContact) {
            Transaction transaction = null;
            try (Session session = HibernateConnector.getInstance().getSession()) {
                transaction = session.beginTransaction();
                session.saveOrUpdate(newContact);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                log.error("Can't update contact", e);
                e.printStackTrace();
            }
        }

        @Override
        public void delete(Contact contact) {
            Transaction transaction = null;
            try (Session session = HibernateConnector.getInstance().getSession()) {
                transaction = session.beginTransaction();
                session.delete(contact);
                session.flush();
                transaction.commit();
            } catch (Exception e) {
                log.error("Can't delete contact", e);
                e.printStackTrace();
            }
        }
}

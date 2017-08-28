package DAO.hibernate;

import ConModel.User;
import DAO.BaseUserDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateUserDAO implements BaseUserDAO{

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
    public User read(String input) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            return session.get(User.class, input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> readAll(String ob) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            String queryString = "Select u from User u";
            Query query = session.createQuery(queryString);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
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

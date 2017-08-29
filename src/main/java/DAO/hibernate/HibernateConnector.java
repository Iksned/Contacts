package DAO.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnector {
    private static final Logger log = Logger.getLogger(HibernateConnector.class);
    private static volatile HibernateConnector instance;
    private Configuration cfg;
    private SessionFactory sessionFactory;

    private HibernateConnector() throws HibernateException {
        try {
            cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            log.error("Cannot create Configuration",e);
        }
    }

    public static HibernateConnector getInstance() throws HibernateException {
        if (instance == null)
            synchronized (HibernateConnector.class) {
                if (instance == null)
                    instance = new HibernateConnector();
            }
        return instance;
    }

    public Session getSession() throws HibernateException {
        Session session = sessionFactory.openSession();
        if (!session.isConnected()) {
            log.info("Reconnecting");
            this.reconnect();
        }
        return session;
    }

    private void reconnect() throws HibernateException {
        this.sessionFactory = cfg.buildSessionFactory();
    }
}
package DAO;

import DAO.hibernate.HibernateContactDAO;
import DAO.hibernate.HibernateGroupDAO;
import DAO.hibernate.HibernateUserDAO;

public class DAOcreatorImpl implements DAOcreator {
    @Override
    public CatalogDAO getDAO(String parserName) {
        if (parserName.equals("User"))
            return HibernateUserDAO.getInstace();
        else if (parserName.equals("Contact"))
            return HibernateContactDAO.getInstace();
        else if (parserName.equals("Group"))
            return HibernateGroupDAO.getInstace();
        return null;
    }
}

package services;


import DAO.hibernate.AnalyticalDAO;

import java.util.List;

public class AnalyticalService {
    private AnalyticalDAO analyticalDAO;

    public void setAnalyticalDAO(AnalyticalDAO analyticalDAO) {
        this.analyticalDAO = analyticalDAO;
    }

    public synchronized long countUsers() {
        return analyticalDAO.countUsers();
    }

    public synchronized List<ResultTable> countUserContacts() {
        return analyticalDAO.countUsersContacts();
    }

    public synchronized List<ResultTable> countUserGroups() {
        return analyticalDAO.countUsersGroups();
    }

    public synchronized int avgContactsInGroups() {
        return analyticalDAO.avgContactsInGroups();
    }

    public synchronized int avgUserContacts() {
        return analyticalDAO.avgUsersContacts();
    }

    public synchronized List<String> inactiveUsers() {
        return analyticalDAO.inactiveUsers();
    }
}

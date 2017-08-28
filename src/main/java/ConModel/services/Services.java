package ConModel.services;

import ConModel.Contact;
import ConModel.Group;
import ConModel.User;
import DAO.*;
import DAO.hibernate.AnalyticalDAO;
import org.apache.log4j.Logger;

import java.util.List;

public class Services {
    private static final Logger log = Logger.getLogger(Services.class);
    private DAOcreator creator = new BaserDAOcreator();
    private  CatalogDAO parser;

    private volatile static Services instace;

    private Services() {
    }

    public static Services getInstace() {
        if (instace == null)
            synchronized (Services.class) {
                if (instace == null)
                    instace = new Services();
            }
        return instace;
    }

    private synchronized void setParser(String chosen)
    {
        parser  = creator.getDAO(chosen);
    }

    public synchronized List<Contact> getAllContacts(String user) {
        setParser("Contact");
        return  (List<Contact>) parser.readAll(user);
    }

    public synchronized List<Group> getGroups(String user) {
        setParser("Group");
        List<Group> groupList = (List<Group>)parser.readAll(user);
        return groupList;
    }

    public synchronized Group getGroupById(int id) {
        setParser("Group");
        Group group = (Group)parser.read(""+id);
        return group;
    }

    public synchronized Contact getContactById(int id) {
        setParser("Contact");
        return (Contact) parser.read(""+id);
    }

    public synchronized void updateContact(Contact contact) {
        setParser("Contact");
        parser.update(contact);
    }

    public synchronized void updateGroup(Group newGroup) {
        setParser("Group");
        parser.update(newGroup);
    }

    public synchronized void addGroup(Group newGroup) {
        setParser("Group");
        parser.create(newGroup);
    }

    public synchronized void addContact(Contact contact) {
        setParser("Contact");
        parser.create(contact);
    }

    public synchronized void delContact(Contact contact) {
        setParser("Contact");
        parser.delete(contact);
    }

    public synchronized void delGroup(Group group) {
        setParser("Group");
        parser.delete(group);
    }

    public synchronized User getUserById(String username){
        setParser("User");
        return (User)parser.read(username);
    }

    public synchronized List<User> getAllUsers(){
        setParser("User");
        return parser.readAll("users");
    }

    public synchronized boolean checkUser(String username, String password) {
        return AnalyticalDAO.getInstace().chekUser(username,password);
    }

    public synchronized long countUsers() {
        return AnalyticalDAO.getInstace().countUsers();
    }

    public synchronized List<ResultTable> countUserContacts() {
          return AnalyticalDAO.getInstace().countUsersContacts();
    }

    public synchronized List<ResultTable> countUserGroups() {
       return AnalyticalDAO.getInstace().countUsersGroups();
    }

    public synchronized int avgContactsInGroups() {
        return AnalyticalDAO.getInstace().avgContactsInGroups();
    }

    public synchronized int avgUserContacts() {
        return AnalyticalDAO.getInstace().avgUsersContacts();
    }

    public synchronized List<String> inactiveUsers() {
        return AnalyticalDAO.getInstace().inactiveUsers();
    }
}

package ConModel.services;

import ConModel.Contact;
import ConModel.Group;
import DAO.*;

import java.util.ArrayList;
import java.util.List;

public class Services {
    private static ParserCreator creator = new BaserParserCreator();
    private static CatalogDAO parser;

    private static Services instace;

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
        parser  = creator.getParser(chosen);
    }

    public synchronized List<String> getAllNames(String user) {
        setParser("Contact");
        return  (List<String>) parser.readAll(user);
    }

    public synchronized List<String> getNamesByGroup(String group,String user) {
        setParser("Contact");
        return  (List<String>) parser.read("getNameByGroup "+group +" "+user);
    }

    public synchronized List<Group> getGroups(String user) {
        setParser("Group");
        List<String> finList = (List<String>)parser.readAll(user);
        List<Group> groupList = new ArrayList<>();
        for (int i = 0;i<finList.size();i++)
        groupList.add(i,new Group(finList.get(i)));
        return groupList;
    }

    public synchronized Group getGroupByName(String newGroup,String user) {
        setParser("Group");
        String[] finList = (String[])parser.read("getGroupByName " + newGroup+" "+user);
        if (finList.length == 0)
            return new Group("");
        return new Group(finList[0]);
    }

    public synchronized Contact getContactByName(String selectedValue,String user) {
        setParser("Contact");
        return (Contact) parser.read("getContactByName "+selectedValue + " " + user);
    }

    public synchronized void updateContact(Contact contact, String name, String phnumber, Group group) {
        setParser("Contact");
        parser.update(contact, new Contact(name, phnumber, group));
    }

    public synchronized void updateGroup(String oldSt, String text) {
        setParser("Group");
        parser.update(new Group(oldSt), new Group(text));
    }

    public synchronized void addGroup(String user,Group newGroup) {
        setParser("Group");
        parser.create(user, newGroup);
    }

    public synchronized void addContact(String user,Contact contact) {
        setParser("Contact");
        parser.create(user, contact);
    }

    public synchronized void delContact(Contact contact) {
        setParser("Contact");
        parser.delete(contact);
    }

    public synchronized void delGroup(Group group) {
        setParser("Group");
        parser.delete(group);
    }

    public synchronized boolean checkUser(String loginText, String passText) {
        setParser("User");
        String result = (String)parser.read("logpass "+loginText +" "+ passText);
        return result.equals("Pass");
    }

    public synchronized int countUsers() {
        setParser("User");
        return (Integer)parser.read("countUsers");
    }

    public synchronized List<ResultTable> countUserContacts() {
        setParser("User");
        return (List<ResultTable>)parser.read("countUserContacts");
    }

    public synchronized List<ResultTable> countUserGroups() {
        setParser("User");
        return (List<ResultTable>)parser.read("countUserGroups");
    }

    public synchronized int avgContactsInGroups() {
        setParser("User");
        return (Integer)parser.read("avgContactsInGroups");
    }

    public synchronized int avgUserContacts() {
        setParser("User");
        return (Integer)parser.read("avgUserContacts");
    }

    public synchronized List<String> inactiveUsers() {
        setParser("User");
        return (List<String>)parser.read("inactiveUsers");
    }
}

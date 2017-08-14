package ConModel.services;

import ConModel.Contact;
import ConModel.Group;
import DAO.*;

import java.util.ArrayList;
import java.util.List;

public class Services {
    private static ParserCreator creator = new BaserParserCreator();
    private static CatalogDAO parser;
    private static ObserverService observerService = new ObserverService();

    private static synchronized void setParser(String chosen)
    {
        parser  = creator.getParser(chosen);;
    }

    public static synchronized String[] getAllNames(String user) {
        setParser("Contact");
        return  (String[]) parser.readAll(user);
    }

    public static synchronized String[] getNamesByGroup(String group,String user) {
        setParser("Contact");
        return  (String[]) parser.read("getNameByGroup "+group +" "+user);
    }

    public static synchronized List<Group> getGroups(String user) {
        setParser("Group");
        String[] finList = (String[])parser.readAll(user);
        List<Group> groupList = new ArrayList<>();
        for (int i = 0;i<finList.length;i++)
        groupList.add(i,new Group(finList[i]));
        return groupList;
    }

    public static synchronized Group getGroupByName(String newGroup,String user) {
        setParser("Group");
        String[] finList = (String[])parser.read("getGroupByName " + newGroup+" "+user);
        if (finList.length == 0)
            return new Group("");
        return new Group(finList[0]);
    }

    public static synchronized Contact getContactByName(String selectedValue,String user) {
        setParser("Contact");
        return (Contact) parser.read("getContactByName "+selectedValue + " " + user);
    }

    public static synchronized void updateContact(Contact contact, String name, String phnumber, Group group) {
        setParser("Contact");
        parser.update(contact, new Contact(name, phnumber, group));
        observerService.notifyObserver();
    }

    public static synchronized void updateGroup(String oldSt, String text) {
        setParser("Group");
        parser.update(new Group(oldSt), new Group(text));
        observerService.notifyObserver();
    }

    public static synchronized void addGroup(String user,Group newGroup) {
        setParser("Group");
        parser.create(user, newGroup);
        observerService.notifyObserver();
    }

    public static synchronized void addContact(String user,Contact contact) {
        setParser("Contact");
            parser.create(user, contact);
        observerService.notifyObserver();
    }

    public static synchronized void delContact(Contact contact) {
        setParser("Contact");
        parser.delete(contact);
        observerService.notifyObserver();
    }

    public static synchronized void delGroup(Group group) {
        setParser("Group");
        parser.delete(group);
        observerService.notifyObserver();
    }


    public static ObserverService getObserverService()
    {
        return observerService;
    }

    public static synchronized boolean checkUser(String loginText, String passText) {
        setParser("User");
        String result = (String)parser.read("logpass "+loginText +" "+ passText);
        return result.equals("Pass");
    }
}

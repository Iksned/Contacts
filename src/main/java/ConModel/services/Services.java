package ConModel.services;

import ConModel.Contact;
import ConModel.Group;
import DAO.*;
import DAO.impl.BaseParser;

import java.util.ArrayList;
import java.util.List;

public class Services {
    private static ParserCreator creator;
    private static CatalogDAO parser = new BaseParser();
    private static ObserverService observerService = new ObserverService();

    // Неиспользуемый метод предыдущего задания
    public static void setParser(String chosen)
    {
        if (chosen.equals("DOM"))
            creator = new DOMcreator();
        else if (chosen.equals("SAX"))
            creator = new SAXcreator();
        else if (chosen.equals("Jackson"))
            creator = new JacksonCreator();

        parser  = creator.factory_method();;
    }

    public static synchronized String[] getAllNames(String user) {
        return  (String[]) parser.read("Allnames "+user);
    }

    public static synchronized String[] getNamesByGroup(String group,String user) {
        return  (String[]) parser.read("getNameByGroup "+group +" "+user);
    }

    public static synchronized List<Group> getGroups(String user) {
        String[] finList = (String[])parser.read("Allgroups "+user);
        List<Group> groupList = new ArrayList<>();
        for (int i = 0;i<finList.length;i++)
        groupList.add(i,new Group(finList[i]));
        return groupList;
    }

    public static synchronized Group getGroupByName(String newGroup,String user) {
        String[] finList = (String[])parser.read("getGroupByName " + newGroup+" "+user);
        if (finList.length == 0)
            return new Group("");
        return new Group(finList[0]);
    }

    public static synchronized Contact getContactByName(String selectedValue,String user) {
        return (Contact) parser.read("getContactByName "+selectedValue + " " + user);
    }

    public static synchronized void updateContact(Contact contact, String name, String phnumber, Group group) {
            parser.update(contact, new Contact(name, phnumber, group));
        observerService.notifyObserver();
    }

    public static synchronized void updateGroup(String oldSt, String text) {
            parser.update(new Group(oldSt), new Group(text));
        observerService.notifyObserver();
    }

    public static synchronized void addGroup(String user,Group newGroup) {
            parser.create(user, newGroup);
        observerService.notifyObserver();
    }

    public static synchronized void addContact(String user,Contact contact) {
            parser.create(user, contact);
        observerService.notifyObserver();
    }

    public static synchronized void delContact(Contact contact) {
            parser.delete(contact);
        observerService.notifyObserver();
    }

    public static synchronized void delGroup(Group group) {
            parser.delete(group);
        observerService.notifyObserver();
    }


    public static synchronized ObserverService getObserverService()
    {
        return observerService;
    }

    public static synchronized boolean checkUser(String loginText, String passText) {
        String result = (String)parser.read("logpass "+loginText +" "+ passText);
        return result.equals("Pass");
    }
}

package ConModel.services;

import ConModel.Contact;
import ConModel.Group;
import DAO.*;

import java.util.ArrayList;
import java.util.List;

public class Services {
    private static ParserCreator creator;
    private static CatalogDAO parser;
    private static ObserverService observerService = new ObserverService();

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

    public static String[] getAllNames() {
        return  (String[]) parser.read("Allnames all");
    }

    public static String[] getNamesByGroup(String group) {
        return  (String[]) parser.read("getNameByGroup "+group);
    }

    public static List<Group> getGroups() {
        String[] finList = (String[])parser.read("Allgroups all");
        List<Group> groupList = new ArrayList<>();
        for (int i = 0;i<finList.length;i++)
        groupList.add(i,new Group(finList[i]));
        return groupList;
    }

    public static Group getGroupByName(String newGroup) {
        String[] finList = (String[])parser.read("getGroupByName " + newGroup);
        if (finList.length == 0)
            return new Group("");
        return new Group(finList[0]);
    }

    public static Contact getContactByName(String selectedValue) {
        return (Contact) parser.read("getContactByName "+selectedValue);
    }

    public static void updateContact(Contact contact, String name, String phnumber, Group group) {
        parser.update(contact,new Contact(name,phnumber,group));
        observerService.notifyObserver();
    }

    public static void updateGroup(String oldSt, String text) {
        parser.update(new Group(oldSt),new Group(text));
        observerService.notifyObserver();
    }

    public static void addGroup(Group newGroup) {
        parser.create(newGroup);
        observerService.notifyObserver();
    }

    public static void addContact(Contact contact) {
        parser.create(contact);
        observerService.notifyObserver();
    }

    public static void delContact(Contact contact) {
        parser.delete(contact);
        observerService.notifyObserver();
    }

    public static void delGroup(Group group) {
        parser.delete(group);
        observerService.notifyObserver();
    }


    public static ObserverService getObserverService()
    {
        return observerService;
    }
}

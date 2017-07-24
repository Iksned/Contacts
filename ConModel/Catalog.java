package ConModel;

import concontrol.CatalogObserver;
import concontrol.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable, Observable {
    private static Catalog ourInstance;


    public static Catalog getInstance() {
        if (ourInstance == null)
            synchronized (Catalog.class) {
                if (ourInstance == null)
                    ourInstance = new CatDaoCloud().loadCatalog();
                if (ourInstance == null)
                    ourInstance = new Catalog();
            }
        return ourInstance;
    }

    private Catalog() {
    }

    private List<Contact> contacts = new ArrayList<Contact>();
    private List<Group> groups = new ArrayList<Group>();
    private List<CatalogObserver> observers = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }



    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        notifyObserver();

    }

    public void addContact(String name,String phNumber,String group) {
        addContact(new Contact(name,phNumber,group));
    }

    public String[] getNames() {
        String[] names = new String[contacts.size()];
        for (int i = 0;i<contacts.size();i++)
        {
            names[i]=contacts.get(i).getName();
        }
        return names;
    }

    public Contact getContactByName(String selectedValue) {
        Contact contact = null;
        for (Contact contact1 : contacts)
            if (contact1.getName().equals(selectedValue))
                contact = contact1;
        return contact;
    }

    public void delContact(String selectedValue) {
        Contact contact = null;
        for (Contact contact1 : contacts)
            if (contact1.getName().equals(selectedValue))
               contact = contact1;
        if (contact != null) {
            contacts.remove(contact);
            notifyObserver();
        }
    }

    public void updateContact(Contact contact,String name,String phNumber,String group)
    {
        for (Contact contact1: contacts)
            if (contact1.equals(contact)) {
                    contact1.setName(name);
                    contact1.setPh_number(phNumber);
                    contact1.setGroup(group);
                    notifyObserver();
            }

    }

    @Override
    public void notifyObserver() {
        for(CatalogObserver observer:observers)
            observer.update();
    }

    @Override
    public void registerObserver(CatalogObserver obs) {
        observers.add(obs);
    }

    public String[] getNamesByGroup(String group) {
        String[] names = new String[contacts.size()];
        int inc = 0;
        for (int i = 0;i<contacts.size();i++)
        {
            if(group.equals(contacts.get(i).getGroup())) {
                names[inc] = contacts.get(i).getName();
                inc++;
            }
        }
        return names;
    }

    public void delGroup(String group) {
        groups.remove(getGroupByName(group));
        notifyObserver();
    }

    public void updateGroup(String oldSt, String text) {
        for(int i =0;i<groups.size();i++)
            if (groups.get(i).getName().equals(oldSt))
                groups.get(i).setName(text);
        notifyObserver();
    }

    public void addGroup(String newGroup) {
        groups.add(new Group(newGroup));
        notifyObserver();
    }

    public Group getGroupByName(String newGroup) {
        for (int i = 0;i<groups.size();i++)
            if (newGroup.equals(groups.get(i).getName()))
                return groups.get(i);
        return null;
    }
}

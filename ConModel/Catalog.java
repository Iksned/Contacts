package ConModel;

import concontrol.CatalogObserver;
import concontrol.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable, Observable {
    private static Catalog ourInstance;
    private CatalogObserver observer;

    public static Catalog getInstance() {
        if (ourInstance == null)
            synchronized (Catalog.class) {
                if (ourInstance == null)
                    ourInstance = new CatDaoCloud().LoadCat();
                if (ourInstance == null)
                    ourInstance = new Catalog();
            }
        return ourInstance;
    }

    private Catalog() {
        groups.add("Group1");
        groups.add("Group2");
        groups.add("Group3");
    }

    private List<Contact> contacts = new ArrayList<Contact>();
    private List<String> groups = new ArrayList<String>();

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
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
        observer.update();
    }

    @Override
    public void registerObserver(CatalogObserver obs) {
        observer = obs;
    }

    public String[] getNamesByGroup(String group) {
        String[] names = new String[contacts.size()];
        for (int i = 0;i<contacts.size();i++)
        {
            if(contacts.get(i).getGroup().equals(group))
            names[i]=contacts.get(i).getName();
        }
        return names;
    }
}

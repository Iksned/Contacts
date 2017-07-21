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
                    ourInstance = Utilits.LoadCat();
                if (ourInstance == null)
                    ourInstance = new Catalog();
            }
        return ourInstance;
    }

    private Catalog() {
    }

    private List<Contact> contacts = new ArrayList<Contact>();

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
        if (contact != null)
            contacts.remove(contact);
    }

    @Override
    public void notifyObserver() {
        observer.update();
    }

    @Override
    public void addObserver(CatalogObserver obs) {
        observer = obs;
    }
}

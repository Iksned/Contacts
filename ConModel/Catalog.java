package ConModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private static Catalog ourInstance;

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
        ourInstance.addContact(new Contact("Vasia"));
    }

    private List<Contact> contacts = new ArrayList<Contact>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
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
}

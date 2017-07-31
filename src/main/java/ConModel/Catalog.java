package ConModel;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import concontrol.CatalogObserver;
import concontrol.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement
public class Catalog implements Serializable {
    private static Catalog ourInstance;

    public static Catalog getInstance() {
        if (ourInstance == null)
            synchronized (Catalog.class) {
                if (ourInstance == null)
                    ourInstance = new Catalog();
            }
        return ourInstance;
    }

    private Catalog() {
    }

    private List<Contact> contacts = new ArrayList<Contact>();
    private List<Group> groups = new ArrayList<Group>();

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }



    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact1) {
        this.contacts.add(contact1);
    }

    public void addContact(String name,String phNumber,Group group) {
        addContact(new Contact(name,phNumber,group));
    }

    public String[] getNames() {
        String[] names = new String[contacts.size()];
        for (int i = 0; i< contacts.size(); i++)
        {
            names[i]= contacts.get(i).getName();
        }
        return names;
    }

    public Contact getContactByName(String selectedValue) {
        Contact contact = null;
        for (Contact contact1 : this.contacts)
            if (contact1.getName().equals(selectedValue))
                contact = contact1;
        return contact;
    }

    public void delContact(String selectedValue) {
        Contact contact = null;
        for (Contact contact1 : this.contacts)
            if (contact1.getName().equals(selectedValue))
               contact = contact1;
        if (contact != null) {
            this.contacts.remove(contact);

        }
    }

    public void updateContact(Contact contact,String name,String phNumber,Group group)
    {
        for (Contact contact1: this.contacts)
            if (contact1.equals(contact)) {
                    contact1.setName(name);
                    contact1.setPh_number(phNumber);
                    contact1.setGroup(group);

            }

    }


    public String[] getNamesByGroup(String group) {
        String[] names = new String[contacts.size()];
        int inc = 0;
        for (int i = 0; i< contacts.size(); i++)
        {
            if (contacts.get(i).getGroup() != null)
            if(group.equals(contacts.get(i).getGroup().getName())) {
                names[inc] = contacts.get(i).getName();
                inc++;
            }
        }
        return names;
    }

    public void delGroup(String group) {
        groups.remove(getGroupByName(group));
    }

    public void updateGroup(String oldSt, String text) {
        for(int i =0;i<groups.size();i++)
            if (groups.get(i).getName().equals(oldSt))
                groups.get(i).setName(text);
    }

    public void addGroup(String newGroup) {
        groups.add(new Group(newGroup));
    }

    public Group getGroupByName(String newGroup) {
        for (int i = 0;i<groups.size();i++)
            if (newGroup.equals(groups.get(i).getName()))
                return groups.get(i);
        return null;
    }
}

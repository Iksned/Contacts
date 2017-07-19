import java.util.ArrayList;

/**
 * Created by Iks on 19.07.2017.
 */
public class Catalog {
    private static Catalog ourInstance = new Catalog();

    public static Catalog getInstance() {
        return ourInstance;
    }

    private Catalog() {
        contacts.add(new Contact("Vasia")); //test sub
        contacts.add(new Contact("Petya"));
    }

    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ArrayList<Contact> getContacts() {
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
}

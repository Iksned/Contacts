package concontrol;

import ConModel.Catalog;
import ConModel.Contact;
import conview.View;

import java.io.Serializable;
import java.util.List;

public class CatController implements Serializable { // to interface

    private Catalog catalog;
    private View view;

    public CatController(Catalog catalog) {
        this.catalog = catalog;
    }

    public CatController(Catalog catalog, View view) {
        this.catalog = catalog;
        this.view = view;

    }

    public void addContact(Contact contact)
    {
        catalog.addContact(contact);
    }

    public void addContact(String name,String ph,String group)
    {
        addContact(new Contact(name,ph,group));
    }

    public void removeContact(String contact)
    {
        catalog.delContact(contact);
    }

    public Contact getContactByName(String selectedValue) {
        return catalog.getContactByName(selectedValue);
    }

    public String[] getNames() {
        return catalog.getNames();
    }

    public String[] getNamesByGruop(String group)
    {
        return catalog.getNamesByGroup(group);
    }

    public Catalog getCatalog()
    {
        return catalog;
    }


    public void updateContact(Contact contact,String name, String phnumber, String group) {
        catalog.updateContact(contact,name,phnumber,group);
    }

    public List<String> getGroups()
    {
        return catalog.getGroups();
    }
}

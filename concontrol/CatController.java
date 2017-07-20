package concontrol;

import ConModel.Catalog;
import ConModel.Contact;


import javax.swing.*;

public class CatController {

    private Catalog catalog;

    private JLabel label;

    public CatController(Catalog catalog, JLabel label) {
        this.catalog = catalog;
        this.label = label;
    }

    public void addContact(Contact contact)
    {
        catalog.addContact(contact);
    }

    public void removeContact(String contact)
    {
        catalog.delContact(contact);
    }

    public void updateInfo(Contact tempCon)
    {
        label.setText(tempCon.getName() + " " + tempCon.getPh_number() + " " + tempCon.getGroup() + " ");
        label.setVisible(true);
    }


    public Contact getContactByName(String selectedValue) {
        return catalog.getContactByName(selectedValue);
    }

    public String[] getNames() {
        return catalog.getNames();
    }

    public Catalog getCatalog()
    {
        return catalog;
    }
}

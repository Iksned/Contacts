package concontrol;

import ConModel.Catalog;
import ConModel.Contact;
import conview.View;

public class CatController {

    private Catalog catalog;
    private View view;

    public CatController(Catalog catalog, View view) {

        this.catalog = catalog;
        this.view = view;
    }

    public void AddContact(Contact contact)
    {
        catalog.addContact(contact);
    }

    public void ShowWindow()
    {
        view.createWindow(catalog);
    }


}

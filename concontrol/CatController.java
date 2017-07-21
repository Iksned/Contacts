package concontrol;

import ConModel.Catalog;
import ConModel.Contact;
import ConModel.Utilits;
import conview.Main_Window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class CatController implements CatalogObserver,Serializable { // to interface

    private Catalog catalog;
    private Main_Window main_window;

    public CatController(Catalog catalog, Main_Window frame) {
        this.catalog = catalog;
        this.main_window = frame;
        catalog.addObserver(this);
        fillList();
    }

    public void initController()
    {
        main_window.getDelMenuItem().addActionListener(e -> deletoFromList());
        main_window.getExitButton().addActionListener(e -> savenexit());
        main_window.getContactList().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listSel(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                invokePopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void savenexit() {
        Utilits.SaveCat(catalog);
        System.exit(0);
    }

    private void invokePopup(MouseEvent e) {
        if (isRightMouseButton(e)) {
            if (main_window.getContactList().getSelectedValue() != null)
                main_window.getListPopup().show(main_window.getContactList(),0,0);
        }
    }

    public void addContact(Contact contact)
    {
        catalog.addContact(contact);
    }

    public void removeContact(String contact)
    {
        catalog.delContact(contact);
    }

    private String updateInfo(Contact tempCon) {
        return (tempCon.getName() + " " + tempCon.getPh_number() + " " + tempCon.getGroup() + " ");
    }

    private void deletoFromList() {
            removeContact(main_window.getContactList().getSelectedValue());
            fillList();
    }

    private void fillList() {
        main_window.getContactList().setListData(getNames());
    }

    private Contact getContactByName(String selectedValue) {
        return catalog.getContactByName(selectedValue);
    }

    private String[] getNames() {
        return catalog.getNames();
    }

    public Catalog getCatalog()
    {
        return catalog;
    }

    private void listSel(MouseEvent e) {
        if (isLeftMouseButton(e)) {
            main_window.getLabel().setText(updateInfo(getContactByName(main_window.getContactList().getSelectedValue())));
        }
    }

    @Override
    public void update() {
        fillList();
    }
}

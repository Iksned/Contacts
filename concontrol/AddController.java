package concontrol;

import ConModel.Catalog;
import conview.Main_Window;


public class AddController {

    private Catalog catalog;
    private Main_Window.AddContactFrame addContactFrame;

    public AddController(Main_Window.AddContactFrame addContactFrame) {
        this.catalog = Catalog.getInstance();
        this.addContactFrame = addContactFrame;
    }

    public void initController() {
        addContactFrame.getOkButton().addActionListener(e -> confimAdd());
    }

    private void confimAdd() {
        String name = addContactFrame.getNameAdd().getText();
        String phnumber = addContactFrame.getPhNumbertAdd().getText();
        String group = (String)addContactFrame.getGroupAdd().getSelectedItem();
        if (!name.isEmpty() & !phnumber.isEmpty())
            try {
                Integer.parseInt(phnumber);
                catalog.addContact(name,phnumber,group);
            }
            catch (NumberFormatException e1) {
                System.out.println("Not Numbers");
            }
            addContactFrame.setVisible(false);

    }
}

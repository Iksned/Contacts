package conview;

import concontrol.AddController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;


public class Main_Window implements Serializable{

    private Dimension MAIN_DIMENSION = new Dimension(200,200);
    private final JList<String> contactList;
    private final JPopupMenu listPopup;
    private final JMenuBar menuBar;
    private JFrame mainWindow;
    private JLabel info;
    private JMenuItem del;
    private JButton exitButton;

    public Main_Window() throws HeadlessException {
        mainWindow = new JFrame();
        mainWindow.setTitle("Main Window");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setPreferredSize(MAIN_DIMENSION);
        mainWindow.setBounds(500,400,300,300);
        contactList = new JList<>();
        menuBar = fillMenuBar();
        listPopup = new JPopupMenu();
        info = new JLabel();
        mainWindow.setJMenuBar(menuBar);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        fillPanel(mainpanel);
        mainWindow.add(mainpanel);
        mainWindow.setVisible(true);
        JMenuItem change = new JMenuItem("Change contact");
        listPopup.add(change);
        del = new JMenuItem("Delete contact");
        listPopup.add(del);

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //TODO
            }
        });
    }

    public JMenuItem getDelMenuItem() {
        return del;
    }

    public JList<String> getContactList() {
        return contactList;
    }

    public JLabel getLabel() {
        return info;
    }

    public JPopupMenu getListPopup() {
        return listPopup;
    }

    public JButton getExitButton()
    {
        return exitButton;
    }

    private void fillPanel(JPanel mainpan) {
        contactList.setPreferredSize(new Dimension(100, 10));
        contactList.setLayoutOrientation(JList.VERTICAL);
        contactList.setVisibleRowCount(0);

        exitButton = new JButton("Close");
        exitButton.setPreferredSize(new Dimension(50, 20));
        mainpan.add(contactList,BorderLayout.WEST);
        mainpan.add(exitButton,BorderLayout.SOUTH);
        mainpan.add(info,BorderLayout.EAST);
    }

    private JMenuBar fillMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu mainMenu = new JMenu("File");
        final JMenuItem addContact = new JMenuItem("Add contact");
        addContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddContactFrame();
            }
        });
        mainMenu.add(addContact);
        return mainMenu;
    }



    public class AddContactFrame extends JFrame{

        private JButton okButton;
        private JTextField nameAdd;
        private JTextField  phNumbertAdd;
        private JComboBox groupAdd;

        AddContactFrame() throws HeadlessException {
            AddController addController = new AddController(this);
            okButton = new JButton("Ok");
            nameAdd = new JTextField();
            nameAdd.setPreferredSize(new Dimension(70, 20));
            phNumbertAdd = new JTextField();
            phNumbertAdd.setPreferredSize(new Dimension(70, 20));
            //Test items
            String[] items = {
                    "",
                    "Group 1",
                    "Group 2",
                    "Group 3"
            };
            groupAdd = new JComboBox(items);
            groupAdd.setPreferredSize(new Dimension(70, 20));
            this.setTitle("Add contact");
            this.setPreferredSize(MAIN_DIMENSION);
            this.setBounds(500,500,200,200);
            JPanel mainpanel = new JPanel();
            mainpanel.setLayout(new GridLayout(4,2));
            this.fillPanel(mainpanel);
            this.add(mainpanel);
            this.validate();
            this.pack();
            this.setVisible(true);
            addController.initController();
         }

        public JButton getOkButton() {
            return okButton;
        }

        public JTextField getNameAdd() {
            return nameAdd;
        }

        public JTextField getPhNumbertAdd() {
            return phNumbertAdd;
        }

        public JComboBox getGroupAdd() {
            return groupAdd;
        }

        private void fillPanel(JPanel mainpan) {
            final JLabel nameLab = new JLabel("Name");
            final JLabel phLab = new JLabel("Phone Number");
            final JLabel groupLab = new JLabel("Group");
            okButton.setPreferredSize(new Dimension(50, 20));
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            mainpan.add(nameAdd);
            mainpan.add(nameLab);
            mainpan.add(phNumbertAdd);
            mainpan.add(phLab);
            mainpan.add(groupAdd);
            mainpan.add(groupLab);
            mainpan.add(okButton);
        }
    }

}

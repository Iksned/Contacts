package conview;

import ConModel.CatDaoCloud;
import ConModel.Contact;
import ConModel.Group;
import concontrol.CatController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;


public class Main_Window implements View,Serializable{

    private Dimension MAIN_DIMENSION = new Dimension(300,300);
    private CatController mainController;
    private JPopupMenu listPopup;
    private JTabbedPane tabbedPane;

    public Main_Window(CatController controller) throws HeadlessException {
        JFrame mainWindow = new JFrame();
        mainController = controller;
        mainWindow.setTitle("Main Window");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setPreferredSize(MAIN_DIMENSION);
        mainWindow.setBounds(500,400,300,300);
        JMenuBar menuBar = fillMenuBar();

        tabbedPane = new JTabbedPane();
        mainWindow.setJMenuBar(menuBar);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        fillPanel(mainpanel,new Group("0"));
        tabbedPane.add("All",mainpanel);
        for(int i = 0;i < mainController.getGroups().size();i++) {
           tabbedPane.add(mainController.getGroups().get(i).getName(),addGroupPanel(mainController.getGroups().get(i)));
        }
        mainWindow.add(tabbedPane);
        mainWindow.setVisible(true);
        mainController.getCatalog().registerObserver(this);

    }

    private JPanel addGroupPanel(Group group) {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());
        fillPanel(groupPanel,group);
        return groupPanel;

    }

    private void fillList() {
        JList<String> groupList;
        for (int i = 0;i<tabbedPane.getTabCount();i++) {
           groupList =  (JList<String>) tabbedPane.getComponentAt(i).getComponentAt(0,0);
           if (i == 0)
                groupList.setListData(mainController.getNames());
           else
               groupList.setListData(mainController.getNamesByGruop(tabbedPane.getTitleAt(i)));
        }
    }

    private String updateInfo(Contact tempCon) {
        return (tempCon.getName() + " " + tempCon.getPh_number() + " " + tempCon.getGroup() + " ");
    }

    private void fillPanel(JPanel mainpan,Group group) {
        JList<String> contactList = new JList<>();
        JLabel infoName;
        JLabel infoPhone;
        JLabel infoGroup;
        contactList.setPreferredSize(new Dimension(100, 10));
        contactList.setLayoutOrientation(JList.VERTICAL);
        contactList.setVisibleRowCount(0);
        //TODO set labels
        infoName = new JLabel();
        infoPhone = new JLabel();
        infoGroup = new JLabel();
        GridLayout labLayout = new GridLayout(3,1);
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(labLayout);
        eastPanel.add(infoName);
        eastPanel.add(infoPhone);
        eastPanel.add(infoGroup);
        listPopup= new JPopupMenu();
        JMenuItem change = new JMenuItem("Change contact");
        listPopup.add(change);
        JMenuItem del = new JMenuItem("Delete contact");
        listPopup.add(del);

        change.addActionListener(e -> new ChangeContactFrame(mainController.getContactByName(((JList<String>)(tabbedPane.getComponent(tabbedPane.getSelectedIndex()).getComponentAt(0,0))).getSelectedValue())));

        del.addActionListener(e -> {
            mainController.removeContact(((JList<String>)(tabbedPane.getComponent(tabbedPane.getSelectedIndex()).getComponentAt(0,0))).getSelectedValue());
        });

        JButton exitButton = new JButton("Close");
        exitButton.setPreferredSize(new Dimension(50, 20));
        exitButton.addActionListener(e -> {new CatDaoCloud().saveCatalog(mainController.getCatalog());
            System.exit(0);});
        contactList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e)) {
                    if(contactList.getModel().getSize()>0)
                    infoName.setText(updateInfo(mainController.getContactByName(contactList.getSelectedValue())));
                }
                if (isRightMouseButton(e)) {
                    if (contactList.getSelectedValue() != null)
                        listPopup.show(contactList,0,0);                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

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
        mainpan.add(contactList,BorderLayout.WEST);
        mainpan.add(exitButton,BorderLayout.SOUTH);
      //  mainpan.add(infoName,BorderLayout.EAST);
        mainpan.add(eastPanel);
        if ("0".equals(group.getName()))
            contactList.setListData(mainController.getNames());
        else
            contactList.setListData(mainController.getNamesByGruop(group.getName()));
    }

    private JMenuBar fillMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createGroupMenu());
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
        final JMenuItem saveList = new JMenuItem("Save catalog");
        saveList.addActionListener(e -> mainController.saveCatalog());
        final JMenuItem showSecondList = new JMenuItem("Show second list");
        showSecondList.addActionListener(e -> new Second_Frame(mainController));
        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> {
            mainController.saveCatalog();
            System.exit(0);});
        mainMenu.add(addContact);
        mainMenu.add(saveList);
        mainMenu.add(showSecondList);
        mainMenu.add(exit);
        return mainMenu;
    }

    private JMenu createGroupMenu() {
        final JMenu groupMenu = new JMenu("Groups");
        final JMenuItem showGroups = new JMenuItem("Show groups");
        showGroups.addActionListener(e -> new GroupFrame());
        groupMenu.add(showGroups);
        return groupMenu;
    }

    @Override
    public void update() {
        fillList();
    }


    public class AddContactFrame extends JFrame{

        private JButton okButton;
        private JTextField nameAdd;
        private JTextField  phNumbertAdd;
        private JComboBox groupAdd;

        AddContactFrame() throws HeadlessException {
            okButton = new JButton("Ok");
            nameAdd = new JTextField();
            nameAdd.setPreferredSize(new Dimension(70, 20));
            phNumbertAdd = new JTextField();
            phNumbertAdd.setPreferredSize(new Dimension(70, 20));
            String[] items = new String[mainController.getGroups().size()];
            for (int i = 0;i<items.length;i++)
                items[i] = mainController.getGroups().get(i).getName();
            groupAdd = new JComboBox(items);
            groupAdd.setPreferredSize(new Dimension(70, 20));
            okButton.addActionListener(e -> confimAdd());
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
         }

        private void confimAdd() {
            String name = nameAdd.getText();
            String phnumber = phNumbertAdd.getText();
            String group = (String)groupAdd.getSelectedItem();
            if (!name.isEmpty() & !phnumber.isEmpty())
                try {
                    Integer.parseInt(phnumber);
                    mainController.addContact(name,phnumber,group);
                }
                catch (NumberFormatException e1) {
                    System.out.println("Not Numbers");
                }
            this.setVisible(false);

        }

        private void fillPanel(JPanel mainpan) {
            final JLabel nameLab = new JLabel("Name");
            final JLabel phLab = new JLabel("Phone Number");
            final JLabel groupLab = new JLabel("Group");
            okButton.setPreferredSize(new Dimension(50, 20));
            mainpan.add(nameAdd);
            mainpan.add(nameLab);
            mainpan.add(phNumbertAdd);
            mainpan.add(phLab);
            mainpan.add(groupAdd);
            mainpan.add(groupLab);
            mainpan.add(okButton);
        }
    }

    public class ChangeContactFrame extends JFrame{

        private JButton okButton;
        private JTextField nameAdd;
        private JTextField  phNumbertAdd;
        private JComboBox groupAdd;
        private Contact contact;

        ChangeContactFrame(Contact contactToChange) throws HeadlessException {
            contact = contactToChange;
            okButton = new JButton("Ok");
            nameAdd = new JTextField();
            nameAdd.setPreferredSize(new Dimension(70, 20));
            nameAdd.setText(contact.getName());
            phNumbertAdd = new JTextField();
            phNumbertAdd.setPreferredSize(new Dimension(70, 20));
            phNumbertAdd.setText(contact.getPh_number());
            String[] items = new String[mainController.getGroups().size()];
            for (int i = 0;i< items.length;i++)
                items[i] = mainController.getGroups().get(i).getName();
            groupAdd = new JComboBox(items);
            groupAdd.setPreferredSize(new Dimension(70, 20));
            okButton.addActionListener(e -> confimChange());
            this.setTitle("Change contact");
            this.setPreferredSize(MAIN_DIMENSION);
            this.setBounds(500,500,200,200);
            JPanel mainpanel = new JPanel();
            mainpanel.setLayout(new GridLayout(4,2));
            this.fillPanel(mainpanel);
            this.add(mainpanel);
            this.validate();
            this.pack();
            this.setVisible(true);
        }

        private void confimChange() {
            String name = nameAdd.getText();
            String phnumber = phNumbertAdd.getText();
            String group = (String)groupAdd.getSelectedItem();
            if (!name.isEmpty() & !phnumber.isEmpty())
                try {
                    Integer.parseInt(phnumber);
                    mainController.updateContact(contact,name,phnumber,group);
                    //info.setText(updateInfo(contact));
                }
                catch (NumberFormatException e1) {
                    System.out.println("Not a phonenumber");
                }
            this.setVisible(false);

        }

        private void fillPanel(JPanel mainpan) {
            final JLabel nameLab = new JLabel("Name");
            final JLabel phLab = new JLabel("Phone Number");
            final JLabel groupLab = new JLabel("Group");
            okButton.setPreferredSize(new Dimension(50, 20));
            mainpan.add(nameAdd);
            mainpan.add(nameLab);
            mainpan.add(phNumbertAdd);
            mainpan.add(phLab);
            mainpan.add(groupAdd);
            mainpan.add(groupLab);
            mainpan.add(okButton);
        }
    }

    public class GroupFrame extends JFrame{

        private JButton addGroupButton;
        private JButton delGroupButton;
        private JButton changeGroupButton;
        private JButton closeButton;
        private JTextField groupToAdd;
        private JList<String> groups;
        private String[] items;



        GroupFrame() throws HeadlessException {
            addGroupButton = new JButton("<-- Add");
            delGroupButton = new JButton("Delete -->");
            changeGroupButton = new JButton("Set Name <--");
            closeButton = new JButton("Close");
            groupToAdd = new JTextField();
            groupToAdd.setPreferredSize(new Dimension(70, 20));
            items = new String[mainController.getGroups().size()];
            for (int i = 0;i<mainController.getGroups().size();i++)
                items[i] = mainController.getGroups().get(i).getName();
            groups = new JList<>(items);
            groups.setPreferredSize(new Dimension(70, 20));
            groups.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    groupToAdd.setText(groups.getSelectedValue());
                }
                @Override
                public void mousePressed(MouseEvent e) {
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
            addGroupButton.addActionListener(e -> addGroup());
            delGroupButton.addActionListener(e -> delGroup());
            changeGroupButton.addActionListener(e -> changeGroup());
            closeButton.addActionListener(e -> this.setVisible(false));
            this.setTitle("Groups");
            this.setPreferredSize(MAIN_DIMENSION);
            this.setBounds(500,500,300,300);
            JPanel mainpanel = new JPanel();
            mainpanel.setLayout(new BorderLayout());
            this.fillPanel(mainpanel);
            this.add(mainpanel);
            this.validate();
            this.pack();
            this.setVisible(true);
        }

        private void changeGroup() {
            String oldSt = groups.getSelectedValue();
            String newSt = groupToAdd.getText();
            if (!newSt.equals("")) {
                mainController.updateGroup(oldSt, newSt);
                if (oldSt != null) {
                    tabbedPane.setTitleAt(getTabNumber(oldSt), newSt);
                    if (((JList<String>) (tabbedPane.getComponent(getTabNumber(newSt)).getComponentAt(0, 0))).getModel().getSize() > 0)
                        for (int i = 0; i < ((JList<String>) (tabbedPane.getComponent(getTabNumber(newSt)).getComponentAt(0, 0))).getComponentCount(); i++) {
                            Contact contact = mainController.getContactByName(mainController.getNamesByGruop(oldSt)[i]);
                            mainController.updateContact(contact, contact.getName(), contact.getPh_number(), newSt);
                        }
                    updateGroupList();
                }
            }
        }

        private void delGroup() {
            String groupToDel = groups.getSelectedValue();
            mainController.delGroup(groupToDel);
            if (((JList<String>) (tabbedPane.getComponent(getTabNumber(groupToDel)).getComponentAt(0, 0))).getModel().getSize() > 0) {
                int f = ((JList<String>) (tabbedPane.getComponent(getTabNumber(groupToDel)).getComponentAt(0, 0))).getComponentCount();
                for (int i = 0; i < ((JList<String>) (tabbedPane.getComponent(getTabNumber(groupToDel)).getComponentAt(0, 0))).getComponentCount(); i++) {
                    Contact contact = mainController.getContactByName(mainController.getNamesByGruop(groupToDel)[i]);
                    mainController.updateContact(contact, contact.getName(), contact.getPh_number(), "");
                }
            }
            updateGroupList();
            tabbedPane.remove(getPanel(groupToDel));
        }

        private void addGroup() {
            String newGroup = groupToAdd.getText();
            if (!newGroup.equals("")) {
                boolean checkForDoubles = false;
                for (int i = 0; i < mainController.getGroups().size(); i++)
                    if (mainController.getGroups().get(i).equals(mainController.getGroupByName(newGroup)))
                        checkForDoubles = true;
                if (!checkForDoubles) {
                    mainController.addGroup(newGroup);
                    updateGroupList();
                    tabbedPane.add(newGroup, addGroupPanel(mainController.getGroupByName(newGroup)));
                }
            }
        }

        private int getTabNumber(String tabName) {
            int panel = -1;
            for (int i = 0;i<tabbedPane.getTabCount();i++)
                if (tabbedPane.getTitleAt(i).equals(tabName))
                    panel = i;
            return panel;
        }

        private JPanel getPanel(String tabName)
        {
            JPanel panel = null;
            for (int i = 0;i<tabbedPane.getTabCount();i++)
                if (tabbedPane.getTitleAt(i).equals(tabName))
                    panel = (JPanel) tabbedPane.getComponent(i);
            return panel;
        }

        private void fillPanel(JPanel mainpan) {
            addGroupButton.setPreferredSize(new Dimension(50, 20));
            mainpan.add(groupToAdd,BorderLayout.EAST);
            mainpan.add(groups,BorderLayout.WEST);
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3,1));
            panel.add(addGroupButton);
            panel.add(changeGroupButton);
            panel.add(delGroupButton);
            mainpan.add(panel,BorderLayout.CENTER);
            mainpan.add(closeButton,BorderLayout.SOUTH);
        }

        private void updateGroupList()
        {
            items = new String[mainController.getGroups().size()];
            for (int i = 0;i<mainController.getGroups().size();i++)
                items[i] = mainController.getGroups().get(i).getName();
            groups.setListData(items);
        }
    }


}

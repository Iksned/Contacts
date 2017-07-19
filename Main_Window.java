import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Main_Window extends JFrame{

    private Dimension MAIN_DIMENSION = new Dimension(200,200);
    private final JList<String> contactList = new JList<String>();
    private final JPopupMenu listPopup = new JPopupMenu();
    private final JMenuItem del = new JMenuItem("Delete");

    public Main_Window() throws HeadlessException {
        this.setTitle("Main Window");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(MAIN_DIMENSION);
        this.setBounds(500,400,300,300);
        final JMenuBar menuBar = fillMenuBar();
        this.setJMenuBar(menuBar);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        this.fillPanel(mainpanel);
        this.add(mainpanel);
        setVisible(true);

        listPopup.add(new JMenuItem("Change..."));
        listPopup.add(del);

        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Catalog.getInstance().delContact(contactList.getSelectedValue());
                fillList();
            }
        });
    }

    private void fillList()
    {
        contactList.setListData(Catalog.getInstance().getNames());
    }

    private void fillPanel(JPanel mainpan) {
        JLabel info = new JLabel();
        contactList.setPreferredSize(new Dimension(100, 10));
        contactList.setLayoutOrientation(JList.VERTICAL);
        contactList.setVisibleRowCount(0);
        contactList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLeftMouseButton(e)) {
                    Contact tempCon = Catalog.getInstance().getContactByName(contactList.getSelectedValue());
                    info.setText(tempCon.getName() + " " + tempCon.getPh_number() + " " + tempCon.getGroup() + " ");
                    info.setVisible(true);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (isRightMouseButton(e))
                {
                    if (contactList.getSelectedValue() != null)
                    listPopup.show(contactList,0,0);

                }
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
        fillList();

        JButton exitButton = new JButton("Close");
        exitButton.setPreferredSize(new Dimension(50, 20));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
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

    public class AddContactFrame extends JFrame {

        public AddContactFrame() throws HeadlessException {
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

        private void fillPanel(JPanel mainpan) {
            final JTextField nameAdd = new JTextField();
            nameAdd.setPreferredSize(new Dimension(70, 20));
            final JTextField phNumbertAdd = new JTextField();
            phNumbertAdd.setPreferredSize(new Dimension(70, 20));
            //Test items
            String[] items = {
                    "",
                    "Group 1",
                    "Group 2",
                    "Group 3"
            };
            final JComboBox groupAdd = new JComboBox(items);
            groupAdd.setPreferredSize(new Dimension(70, 20));
            final JLabel nameLab = new JLabel("Name");
            final JLabel phLab = new JLabel("Phone Number");
            final JLabel groupLab = new JLabel("Group");
            JButton exitButton = new JButton("Ok");
            exitButton.setPreferredSize(new Dimension(50, 20));
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Contact new_Con = null;
                    //noinspection Since15
                    if (!nameAdd.getText().isEmpty() & !phNumbertAdd.getText().isEmpty())
                        try {
                            Integer.parseInt(phNumbertAdd.getText());
                            new_Con = new Contact(nameAdd.getText(),(phNumbertAdd.getText()),(String)groupAdd.getSelectedItem());
                        }
                        catch (NumberFormatException e1)
                        {}

                    if (new_Con != null)
                    {
                        Catalog.getInstance().addContact(new_Con);
                        fillList();
                        setVisible(false);
                    }
                }
            });
            mainpan.add(nameAdd);
            mainpan.add(nameLab);
            mainpan.add(phNumbertAdd);
            mainpan.add(phLab);
            mainpan.add(groupAdd);
            mainpan.add(groupLab);
            mainpan.add(exitButton);
        }
    }
}

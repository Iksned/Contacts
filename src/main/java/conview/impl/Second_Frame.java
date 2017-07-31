package conview.impl;

import concontrol.CatController;
import conview.View;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;


public class Second_Frame implements View,Serializable {

    private CatController addController;
    private JList<String> contactList;

    public Second_Frame(CatController controller) {
        JFrame secondaryWindow = new JFrame();
        addController = controller;
        secondaryWindow.setTitle("Secondary Window");
        secondaryWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        secondaryWindow.setBounds(500,400,300,300);
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.setLayout(new BorderLayout());
        contactList = new JList<>();
        contactList.setPreferredSize(new Dimension(100, 10));
        contactList.setLayoutOrientation(JList.VERTICAL);
        contactList.setVisibleRowCount(0);
        contactList.setListData(addController.getNames());
        secondaryPanel.add(contactList);
        secondaryWindow.add(secondaryPanel);
        secondaryWindow.setVisible(true);
        addController.getObsService().registerObserver(this);
    }

    @Override
    public void update() {
        contactList.setListData(addController.getNames());
    }
}

package conview.impl;

import concontrol.CatController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class FindContactFrame {
    FindContactFrame(CatController catController) {
        JTextField textField = new JTextField();
        JList<String> results = new JList<>();
        JLabel info = new JLabel();
        JFrame findFrame = new JFrame("Find contact by name");
        findFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        findFrame.setPreferredSize(new Dimension(300, 300));
        findFrame.setBounds(500, 400, 300, 300);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        JButton searchButtton = new JButton("Find");
        searchButtton.addActionListener(e -> {
            String request = textField.getText();
            String[] res = new String[1];
            res[0] = catController.getContactByName(request).getName();
            results.setListData(res);
        });
        JButton close = new JButton("close");
        close.addActionListener(e -> {
            findFrame.setVisible(false);
        });

        results.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               info.setText(catController.getContactByName(results.getSelectedValue()).getName() + " "
                            + catController.getContactByName(results.getSelectedValue()).getPh_number() + " "
                            + catController.getContactByName(results.getSelectedValue()).getGroup().getName());
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

        results.setPreferredSize(new Dimension(70, 20));

        mainpanel.add(info,BorderLayout.CENTER);
        mainpanel.add(close,BorderLayout.SOUTH);
        mainpanel.add(results,BorderLayout.WEST);
        mainpanel.add(textField,BorderLayout.NORTH);
        mainpanel.add(searchButtton,BorderLayout.EAST);
        findFrame.setContentPane(mainpanel);
        findFrame.setVisible(true);
    }
}

package conview.impl;

import ConModel.User;
import concontrol.CatController;

import javax.swing.*;
import java.awt.*;

public class Login_Frame {
    public Login_Frame(CatController catController) {
        CatController controller = catController;
        JTextField login = new JTextField();
        JTextField password = new JTextField();
        JLabel loginLab = new JLabel("Login");
        JLabel passwordLab = new JLabel("Password");
        JFrame loginframe = new JFrame("Enter your Login and Password");
        loginframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginframe.setPreferredSize(new Dimension(300, 300));
        loginframe.setBounds(500, 400, 300, 300);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridLayout(3, 2));
        JButton confimButton = new JButton("Ok");
        confimButton.addActionListener(e -> {
            String loginText = login.getText();
            String passText = password.getText();
            if (controller.checkUser(loginText,passText)) {
                new User(loginText,passText);
                controller.chooseParser(loginText);
                loginframe.setVisible(false);
            }
            else
                JOptionPane.showMessageDialog(new JFrame(),
                        "Wrong login or password",
                        "Enter warning",
                        JOptionPane.WARNING_MESSAGE);
        });

        mainpanel.add(loginLab);
        mainpanel.add(login);
        mainpanel.add(passwordLab);
        mainpanel.add(password);
        mainpanel.add(new JLabel(" "));
        mainpanel.add(confimButton);
        loginframe.setContentPane(mainpanel);
        loginframe.setVisible(true);
    }
}


package conview.choose;


import concontrol.CatController;

import javax.swing.*;
import java.awt.*;

public class ChooseFrame {
    public ChooseFrame(CatController catController) {
        CatController controller = catController;
        JFrame chooseFrame = new JFrame("Choose parser");
        chooseFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chooseFrame.setPreferredSize(new Dimension(300,300));
        chooseFrame.setBounds(500,400,300,300);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridLayout(3,1));
        JButton DOMparser = new JButton("DOM parser");
        DOMparser.addActionListener(e -> {controller.chooseParser("DOM");chooseFrame.setVisible(false);});
        JButton SAXparser = new JButton("SAX parser");
        SAXparser.addActionListener(e -> {controller.chooseParser("SAX");chooseFrame.setVisible(false);});
        JButton jacksonParser = new JButton("Jackson parser");
        jacksonParser.addActionListener(e -> {controller.chooseParser("Jackson");chooseFrame.setVisible(false);});
        mainpanel.add(DOMparser);
        mainpanel.add(SAXparser);
        mainpanel.add(jacksonParser);
        chooseFrame.setContentPane(mainpanel);
        chooseFrame.setVisible(true);
    }
}

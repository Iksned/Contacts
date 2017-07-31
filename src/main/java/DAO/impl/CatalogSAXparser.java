package DAO.impl;

import ConModel.Contact;
import ConModel.Group;
import DAO.CatalogDAO;
import DAO.Constants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CatalogSAXparser implements CatalogDAO {
    private List<String> result;
    private String name;
    private String num;
    private String group;
    private boolean contactcheck = false;

    public CatalogSAXparser() {
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(Constants.checkFile);
            Source xmlFile = new StreamSource(Constants.saveFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println("XML is valid");
        } catch (SAXException e) {
            System.out.println("XML is NOT valid reason:" + e);
        } catch (IOException e) {}
    }



    @Override
    public void create(Object ob) {
        JOptionPane.showMessageDialog(new JFrame(),
                "Not supported in this parser",
                "Parser warning",
                JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public Object read(Object path) {
        result = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ReadHandler userhandler = new ReadHandler((String)path);
            saxParser.parse(Constants.saveFile,userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (contactcheck) {
            contactcheck = false;
            return new Contact(name, num, new Group(group));
        }

        List<String> finlist = new ArrayList<>();
        for (String aResult : result)
            if (!aResult.contains("\n"))
                finlist.add(aResult);
        return finlist.toArray(new String[0]);
    }



    @Override
    public void update(Object oldOb, Object newOb) {
        JOptionPane.showMessageDialog(new JFrame(),
                "Not supported in this parser",
                "Parser warning",
                JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void delete(Object ob) {
        JOptionPane.showMessageDialog(new JFrame(),
                "Not supported in this parser",
                "Parser warning",
                JOptionPane.WARNING_MESSAGE);
    }

    class ReadHandler extends DefaultHandler {
        String task;
        String target;
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;
        boolean check4 = false;
        boolean check5 = false;
        boolean check6 = false;

        public ReadHandler(String task1) {
            this.task = task1;
            String[] defPath = task.split(" ");
            if (defPath[0].equals("Allnames"))
                task = "Allnames";
            else if (defPath[0].equals("getNameByGroup")) {
                task = "getNameByGroup";
                target = defPath[1];
            }
            else if (defPath[0].equals("Allgroups"))
                task = "Allgroups";
            else if (defPath[0].equals("getGroupByName")) {
                task = "getGroupByName";
                target =defPath[1];
            }
            else if (defPath[0].equals("getContactByName"))
            {
                task = "getContactByName";
                target = defPath[1];
                contactcheck = true;
            }


        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (task.equals("Allnames")) {
                if (qName.equals("contact"))
                    check1 = true;
                if (qName.equals("group") && check1)
                    check2 = true;
                if (qName.equals("name") && check1 && !check2)
                    check3 = true;
            } else if (task.equals("getNameByGroup")){
                if (qName.equals("contact"))
                    check1 = true;
                if (qName.equals("group") && check1)
                    check2 = true;
                if (qName.equals("name") && check1 && check2)
                    check3 = true;
                if (qName.equals("name") && check4 && !check2 && check1)
                    check5 = true;
            } else if (task.equals("Allgroups")){
                if (qName.equals("groups"))
                    check1 = true;
                if (qName.equals("group"))
                    check2 = true;
                if (qName.equals("name") && check1 && check2)
                    check3 = true;
            } else if (task.equals("getGroupByName")){
                if (qName.equals("contact"))
                    check1 = true;
                if (qName.equals("group") && check1)
                    check2 = true;
                if (qName.equals("name") && check1 && !check2)
                    check3 = true;
                if (qName.equals("name") && check2 && check4 && check1)
                    check5 = true;
            } else if (task.equals("getContactByName")){
                if (qName.equals("contact"))
                    check1 = true;
                if (qName.equals("group") && check1)
                    check2 = true;
                if (qName.equals("name") && check1 && !check2)
                    check3 = true;
                if (qName.equals("name") && check1 && check2 && check4)
                    check5 = true;
                if (qName.equals("ph_number") && check1 && check4)
                    check6 = true;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (task.equals("Allnames")) {
                if (qName.equals("contact"))
                    check1 = false;
                if (qName.equals("group") && check1)
                    check2 = false;
                if (qName.equals("name") && check1 && !check2)
                    check3 = false;
            } else if (task.equals("getNameByGroup")){
                if (qName.equals("contact"))
                    check1 = false;
                if (qName.equals("group") && check1)
                    check2 = false;
                if (qName.equals("name") && check1 && check2)
                    check3 = false;
                if (qName.equals("name") && check4 && !check2 && check1) {
                    check5 = false;
                    check4 = false;
                }
            } else if (task.equals("Allgroups")){
                if (qName.equals("groups"))
                    check1 = false;
                if (qName.equals("group"))
                    check2 = false;
                if (qName.equals("name") && check1 && check2)
                    check3 = false;
            } else if (task.equals("getGroupByName")){
                if (qName.equals("contact"))
                    check1 = false;
                if (qName.equals("group") && check1)
                    check2 = false;
                if (qName.equals("name") && check1 && !check2)
                    check3 = false;
                if (qName.equals("name") && check2 && check4 && check1) {
                    check5 = false;
                    check4 = false;
                }
            } else if (task.equals("getContactByName")){
                if (qName.equals("contact")) {
                    check1 = false;
                    check4 = false;
                }
                if (qName.equals("group") && check1)
                    check2 = false;
                if (qName.equals("name") && check1 && !check2)
                    check3 = false;
                if (qName.equals("name") && check1 && check2 && check4)
                    check5 = false;
                if (qName.equals("ph_number") && check1 && check4)
                    check6 = false;
            }

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (task.equals("Allnames")) {
                if (check3)
                    result.add(new String(ch, start, length));
            } else if (task.equals("getNameByGroup")) {
                if (check3) {
                    if (new String(ch, start, length).equals(target))
                        check4 = true;
                }
                else if (check4 && check5)
                    result.add(new String(ch,start,length));
            } else if (task.equals("Allgroups")){
                if (check3 && !new String(ch,start,length).equals("\n"))
                    result.add(new String(ch,start,length));
            } else if (task.equals("getGroupByName")){
               if (check3 && new String(ch,start,length).equals(target))
                   check4 = true;
               if (check5)
                   result.add(new String(ch,start,length));
            } else if (task.equals("getContactByName"))
            {
                if (check3 && new String(ch,start,length).equals(target)) {
                    check4 = true;
                    name = new String(ch,start,length);
                }
                if (check5)
                    group = new String(ch,start,length);
                if (check6)
                    num = new String(ch,start,length);


            }

        }
    }
}

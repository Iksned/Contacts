package DAO.impl;


import ConModel.Catalog;
import ConModel.Contact;
import ConModel.Group;
import DAO.CatalogDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JacksonMapping implements CatalogDAO {
    private Catalog catalog;
    public JacksonMapping() {
        ObjectMapper objectMapper = new XmlMapper();
        try {
            catalog = objectMapper.readValue(
                    StringUtils.toEncodedString(Files.readAllBytes(Paths.get("src/main/resources/catal.xml")), StandardCharsets.UTF_8), Catalog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCatalog()
    {
        ObjectMapper objectMapper = new XmlMapper();
        try {
            catalog = objectMapper.readValue(
                    StringUtils.toEncodedString(Files.readAllBytes(Paths.get("src/main/resources/catal.xml")), StandardCharsets.UTF_8), Catalog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Object ob) {
       // initCatalog();
        if (ob instanceof Group) {
            Group group = (Group) ob;
            catalog.addGroup(group.getName());
        }
        if (ob instanceof Contact)
        {
            Contact contact = (Contact) ob;
            catalog.addContact(contact);
        }
        saveToFile();

    }

    @Override
    public Object read(Object obj) {
        initCatalog();
        String[] finList = null;
        String path = (String)obj;
        String[] defPath = path.split(" ");
        if (defPath[0].equals("Allnames"))
            finList = catalog.getNames();
        else if (defPath[0].equals("getNameByGroup"))
            finList = catalog.getNamesByGroup(defPath[1]);
        else if (defPath[0].equals("Allgroups")) {
            List<String> groupNames = new ArrayList<>();
            for (int i = 0;i<catalog.getGroups().size();i++)
            groupNames.add(catalog.getGroups().get(i).getName());
            finList = groupNames.toArray(new String[0]);
        }
        else if (defPath[0].equals("getGroupByName")) {
            finList = new String[1];
            String group = "t";
            Group group1 = catalog.getGroupByName(defPath[1]);
            if ( group1== null)
                group = "t";
            else
                group = catalog.getGroupByName(defPath[1]).getName();
            finList[0] = group;
        }
        else if (defPath[0].equals("getContactByName"))
            return catalog.getContactByName(defPath[1]);
        return finList;
    }

    @Override
    public void update(Object oldOb, Object newOb) {
        if (oldOb instanceof Group) {
            Group oldGroup = (Group) oldOb;
            Group newGroup = (Group) newOb;
            for (int i = 0;i<catalog.getGroups().size();i++)
                if (oldGroup.getName().equals(catalog.getGroups().get(i).getName()))
                    catalog.getGroups().get(i).setName(newGroup.getName());
        }
        if (oldOb instanceof Contact)
        {
            Contact contact = (Contact) oldOb;
            Contact newContact = (Contact) newOb;
            for (int i = 0;i<catalog.getContacts().size();i++)
                if (contact.equals(catalog.getContacts().get(i)))
                    catalog.getContacts().set(i,newContact);
        }
        saveToFile();
    }

    @Override
    public void delete(Object ob) {
        if (ob instanceof Group) {
            Group oldGroup = (Group) ob;
            for (int i = 0;i<catalog.getGroups().size();i++)
                if (oldGroup.getName().equals(catalog.getGroups().get(i).getName()))
                    catalog.getGroups().remove(i);

        }
        if (ob instanceof Contact)
        {
            Contact contact = (Contact) ob;
            for (int i = 0;i<catalog.getContacts().size();i++)
                if (contact.equals(catalog.getContacts().get(i)))
                    catalog.getContacts().remove(i);

        }
        saveToFile();

    }

    private void saveToFile() {
        File file = new File("testcatalog.xml");
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(file, catalog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JacksonMapping().saveToFile();
    }
}

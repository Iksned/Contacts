package DAO.impl;


import ConModel.Catalog;
import ConModel.Contact;
import ConModel.Group;
import DAO.CatalogDAO;
import DAO.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
        } catch (IOException ignored) {}

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
       // saveToFile();

    }

    @Override
    public Object read(Object obj) {
        //initCatalog();
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
            catalog.updateGroup(oldGroup.getName(),newGroup.getName());
        }
        if (oldOb instanceof Contact)
        {
            Contact contact = (Contact) oldOb;
            Contact newContact = (Contact) newOb;
            catalog.updateContact(contact,newContact.getName(),newContact.getPh_number(),newContact.getGroup());
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
                    catalog.delContact(contact.getName());
        }
      //  saveToFile();

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
}

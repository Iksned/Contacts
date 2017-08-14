package DAO.impl;

import ConModel.Contact;
import ConModel.Group;
import DAO.CatalogDAO;
import DAO.Constants;
import DAO.PathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;


public class CatalogDOMparser implements CatalogDAO {

    //private Document document;

    public CatalogDOMparser() {
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

    private Document initDocument()
    {
        Document document = null;
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = null;

        try {
            builder = f.newDocumentBuilder();
            document =  builder.parse(Constants.saveFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    public void create(String user,Object ob) {
        Document document = initDocument();
        if (ob instanceof Group) {
            Group group = (Group) ob;
            Element newGroup = document.createElement("group");
            document.getElementsByTagName("groups").item(0).appendChild(newGroup);
            Element groupName = document.createElement("name");
            groupName.setTextContent(group.getName());
            newGroup.appendChild(groupName);
        }
        if (ob instanceof Contact)
        {
            Contact contact = (Contact) ob;
            Element newContact = document.createElement("contact");
            document.getElementsByTagName("contacts").item(0).appendChild(newContact);
            Element group = document.createElement("group");
            newContact.appendChild(group);
            Element groupName = document.createElement("name");
            groupName.setTextContent(contact.getGroup().getName());
            group.appendChild(groupName);
            Element name = document.createElement("name");
            name.setTextContent(contact.getName());
            newContact.appendChild(name);
            Element phNumber = document.createElement("ph_number");
            phNumber.setTextContent(contact.getPh_number());
            newContact.appendChild(phNumber);
        }
        saveToFile(document);
    }

    @Override
    public Object read(Object obj) {
        Document document = initDocument();
        String path = (String)obj;
        String[] defPath = path.split(" ");
        if (defPath[0].equals("Allnames"))
            path = PathConstants.allNames;
        else if (defPath[0].equals("getNameByGroup"))
            path = PathConstants.getNameByGroup(defPath[1]);
        else if (defPath[0].equals("Allgroups"))
            path = PathConstants.allGroups;
        else if (defPath[0].equals("getGroupByName"))
            path = PathConstants.getGroupByName(defPath[1]);
        else if (defPath[0].equals("Contact"))
            path = defPath[1];
        else if (defPath[0].equals("getContactByName"))
            return getContactByName(defPath[1]);
        String[] finList = null;
        String currentPath = (String)path;
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        try {
            XPathExpression expr = xpath.compile(currentPath);
            NodeList list = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            finList = new String[list.getLength()];
            for (int i = 0;i<list.getLength();i++)
                finList[i] = list.item(i).getTextContent();
        } catch (Exception exception) {
            String message = "XML parsing error!";
        }
        return finList;
    }

    private Object getContactByName(String selectedValue) {
        String[] name = (String[])read("Contact /catalog/contacts//contact[name=\""+selectedValue+"\"]/name");
        String[] phnumber = (String[])read("Contact /catalog/contacts//contact[name=\""+selectedValue+"\"]/ph_number");
        String[] group = (String[])read("Contact /catalog/contacts//contact[name=\""+selectedValue+"\"]/group/name");
        if (group.length == 0) {
            group = new String[1];
            group[0] = "";
        }

        return new Contact(name[0],phnumber[0],new Group(group[0]));
    }

    @Override
    public void update(Object oldOb,Object newOb) {
        Document document = initDocument();
        if (oldOb instanceof Group) {
            Group oldGroup = (Group) oldOb;
            NodeList list = document.getElementsByTagName("groups").item(0).getChildNodes();
            for (int i = 0;i<list.getLength();i++)
            {
                if(list.item(i).getNodeName().equals("group"))
                {
                    Node node = list.item(i);
                    NodeList groupNodes = node.getChildNodes();
                    for (int j=0;j<groupNodes.getLength();j++)
                    {
                        Node node1 = groupNodes.item(j);
                        if (node1.getTextContent().equals((oldGroup).getName()))
                        {
                            node1.setTextContent(((Group) newOb).getName());
                        }
                    }
                }
            }
        }
        if (oldOb instanceof Contact)
        {
            Contact contact = (Contact) oldOb;
            Contact newContact = (Contact) newOb;
            NodeList list = document.getElementsByTagName("contacts").item(0).getChildNodes();
            for (int i = 0;i<list.getLength();i++) {
                if(list.item(i).getNodeName().equals("contact")) {
                    NodeList contactNode = list.item(i).getChildNodes();
                    for (int j = 0;j<contactNode.getLength();j++) {
                        if (contactNode.item(j).getNodeName().equals("name")) {
                            if (contactNode.item(j).getFirstChild().getTextContent().equals(contact.getName())) {
                                for (int k = 0; k<contactNode.getLength();k++) {
                                    if (contactNode.item(k).getNodeName().equals("name"))
                                    {
                                        contactNode.item(k).setTextContent(newContact.getName());
                                    }
                                    if (contactNode.item(k).getNodeName().equals("ph_number"))
                                    {
                                        contactNode.item(k).setTextContent(newContact.getPh_number());
                                    }
                                    if (contactNode.item(k).getNodeName().equals("group"))
                                    {
                                        NodeList groupnode = contactNode.item(k).getChildNodes();
                                        for (int z= 0;z<groupnode.getLength();z++)
                                            if (groupnode.item(z).getNodeName().equals("name"))
                                                groupnode.item(z).setTextContent(newContact.getGroup().getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        saveToFile(document);
    }

    @Override
    public void delete(Object ob) {
        Document document = initDocument();
        if (ob instanceof Group) {
            Group oldGroup = (Group) ob;
            NodeList list = document.getElementsByTagName("groups").item(0).getChildNodes();
            for (int i = 0;i<list.getLength();i++)
            {
                if(list.item(i).getNodeName().equals("group"))
                {
                    Node node = list.item(i);
                    NodeList groupNodes = node.getChildNodes();
                    for (int j=0;j<groupNodes.getLength();j++)
                    {
                        Node node1 = groupNodes.item(j);
                        if (node1.getTextContent().equals((oldGroup).getName()))
                        {
                            document.getElementsByTagName("groups").item(0).removeChild(node);
                        }
                    }
                }
            }
        }
        if (ob instanceof Contact)
        {
            Contact contact = (Contact) ob;
            NodeList list = document.getElementsByTagName("contacts").item(0).getChildNodes();
            for (int i = 0;i<list.getLength();i++) {
                if(list.item(i).getNodeName().equals("contact")) {
                    Node contactNode = list.item(i);
                    NodeList contactNodes = list.item(i).getChildNodes();
                    for (int j = 0;j<contactNodes.getLength();j++) {
                        if (contactNodes.item(j).getNodeName().equals("name")) {
                            if (contactNodes.item(j).getFirstChild().getTextContent().equals(contact.getName())) {
                                document.getElementsByTagName("contacts").item(0).removeChild(contactNode);
                            }
                        }
                    }
                }
            }
        }
        saveToFile(document);
    }

    private void saveToFile(Document document)
    {
        File file = Constants.saveFile;
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

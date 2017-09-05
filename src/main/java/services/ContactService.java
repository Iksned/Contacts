package services;

import model.Contact;
import DAO.ContactDAO;

import java.util.List;

public class ContactService {
    private ContactDAO contactDAO;

    public void setContactDAO(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public synchronized List<Contact> getAllContacts(String user) {
        return  contactDAO.readAll(user);
    }

    public synchronized Contact getContactById(int id) {
        return contactDAO.read(""+id);
    }

    public synchronized void updateContact(Contact contact) {
        contactDAO.update(contact);
    }

    public synchronized void addContact(Contact contact) {
        contactDAO.create(contact);
    }

    public synchronized void delContact(Contact contact) {
        contactDAO.delete(contact);
    }
}

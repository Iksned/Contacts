package concontrol;

import ConModel.*;
import ConModel.services.Services;
import conview.impl.Main_Window;

import java.io.Serializable;
import java.util.List;

public class CatController implements Serializable { // to interface

       public CatController(){
    }

    public void delGroup(Group group) {
        Services.delGroup(group);
    }

    public void addContact(Contact contact)
    {
        Services.addContact(contact);
    }

    public void addContact(String name,String ph,Group group)
    {
        addContact(new Contact(name,ph,group));
    }

    public void removeContact(Contact contact)
    {
        Services.delContact(contact);
    }

    public Contact getContactByName(String selectedValue) {
        return Services.getContactByName(selectedValue);
    }

    public String[] getNames() {
        return Services.getAllNames();
    }

    public String[] getNamesByGruop(String group)
    {
        return Services.getNamesByGroup(group);
    }

    public void updateContact(Contact contact,String name, String phnumber, Group group) {
        Services.updateContact(contact,name,phnumber,group);
    }

    public List<Group> getGroups()
    {
        return Services.getGroups();
    }

    public void updateGroup(String oldSt, String text) {
        Services.updateGroup(oldSt,text);
    }

    public void addGroup(Group newGroup) {
        Services.addGroup(newGroup);
    }

    public Group getGroupByName(String newGroup) {
       return Services.getGroupByName(newGroup);
    }

    public Observable getObsService() {
           return Services.getObserverService();
    }

    public void chooseParser(String chosen) {
            Services.setParser(chosen);
           new Main_Window(this);
    }
}

package concontrol;

import ConModel.*;
import ConModel.services.Services;

import java.util.List;

public class CatController {

    private User currentUser;

       public CatController(){
    }
    public String getUsername()
    {return currentUser.username;}

    public CatController(User currentUser) {
        this.currentUser = currentUser;
    }

    public void delGroup(Group group) {
        Services.delGroup(group);
    }

    public void addContact(Contact contact)
    {
        Services.addContact(currentUser.username,contact);
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
        return Services.getContactByName(selectedValue,currentUser.username);
    }

    public String[] getNames() {
        return Services.getAllNames(currentUser.username);
    }

    public String[] getNamesByGruop(String group)
    {
        return Services.getNamesByGroup(group,currentUser.username);
    }

    public void updateContact(Contact contact,String name, String phnumber, Group group) {
        Services.updateContact(contact,name,phnumber,group);
    }

    public List<Group> getGroups()
    {
        return Services.getGroups(currentUser.username);
    }

    public void updateGroup(String oldSt, String text) {
        Services.updateGroup(oldSt,text);
    }

    public void addGroup(Group newGroup) {
        Services.addGroup(currentUser.username,newGroup);
    }

    public Group getGroupByName(String newGroup) {
       return Services.getGroupByName(newGroup,currentUser.username);
    }

    public Observable getObsService() {
           return Services.getObserverService();
    }

    public void chooseParser(User chosen) {
           currentUser = chosen;
    }

    public boolean checkUser(String loginText, String passText) {
         return   Services.checkUser(loginText,passText);
    }
}

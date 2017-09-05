package services;

import model.Group;
import DAO.GroupDAO;

import java.util.List;

public class GroupService {
    private GroupDAO groupDAO;

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public synchronized List<Group> getAllGroups(String user) {
        return groupDAO.readAll(user);
    }

    public synchronized Group getGroupById(int id) {
        return groupDAO.read(""+id);
    }

    public synchronized void updateGroup(Group newGroup) {
        groupDAO.update(newGroup);
    }

    public synchronized void addGroup(Group newGroup) {
        System.out.println("WHATTHE");
        groupDAO.create(newGroup);
    }

    public synchronized void delGroup(Group group) {
        groupDAO.delete(group);
    }
}

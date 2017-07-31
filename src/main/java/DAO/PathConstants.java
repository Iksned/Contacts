package DAO;

public class PathConstants {
    public final static String allNames = "/catalog/contacts//contact/name";
    public final static String allGroups = "/catalog/groups//group/name";

    public static String getNameByGroup(String group) {
        return "/catalog/contacts//contact[group/name=\""+group+"\"]/name";
    }

    public static String getGroupByName(String newGroup) {
        return "/catalog/groups/group[name=\"" + newGroup + "\"]/name";
    }
}

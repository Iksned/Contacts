package DAO;

public class Constants {
    public final static String groupList = "Select g from Group g where g.user = '%s'";
    public final static String contactList = "Select c from Contact c where c.user = '%s'";
    public final static String allUserList = "Select u from User u";
    public final static String allContactList = "Select c from Contact c";
    public final static String allGroupList = "Select g from Group g";
    public final static String CountallUserList = "Select count(u) from User u";;
}

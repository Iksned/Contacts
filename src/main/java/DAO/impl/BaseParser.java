package DAO.impl;

import ConModel.Contact;
import ConModel.Group;
import DAO.CatalogDAO;
import DAO.BaseMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseParser implements CatalogDAO {
   private static final String DB_URL = "jdbc:postgresql://localhost:5432:postgres";

   private static final String USER = "postgres";
   private static final String PASS = "postgres";

    private static final String addGroupSQL = "{call addGroup(?,?)}";
   private static final String addContactSQL = "{call addContact(?,?,?,?)}";
   private static final String checkUserSQL = "{call checkUser(?,?)}";
   private static final String getAllContactsSQL = "{call getallcontacts(?)}";
   private static final String getAllGroupsSQL = "{call getallgroups(?)}";
   private static final String getNameByGroupSQL = "{call getNameByGroup(?,?)}";
   private static final String getGroupByNameSQL = "{call getGroup(?,?)}";
   private static final String getContactByNameSQL = "{call getContactByName(?,?)}";
   private static final String updateGroupSQL = "{call updateGroup(?,?)}";
   private static final String updateContactSQL = "{call  updateContact(?,?,?,?,?)}";
   private static final String delGroupSQL = "{call delGroup(?)}";
   private static final String delContactSQL = "{call delContact(?,?)}";


   private Connection conn = null;

    public BaseParser() {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void create(String user,Object ob) {
        PreparedStatement stmt;
        if (ob instanceof Group) {
            Group group = (Group) ob;
            try {
                stmt = conn.prepareCall(addGroupSQL);
                stmt.setString(1,user);
                stmt.setString(2,group.getName());
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ob instanceof Contact) {
            Contact contact = (Contact)ob;
            try {
                stmt = conn.prepareCall(addContactSQL);
                stmt.setString(1,user);
                stmt.setString(2,contact.getName());
                stmt.setInt(3,Integer.parseInt(contact.getPh_number()));
                stmt.setString(4,contact.getGroup().getName());
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized Object read(Object ob) {
        Object result = null;
        PreparedStatement stmt = null;
        String path = (String)ob;
        String[] defPath = path.split(" ");
        BaseMapper mapper;
        if (defPath[0].equals("logpass")) {
            try {
                mapper = new CheckUser();
                stmt = conn.prepareCall(checkUserSQL);
                if (defPath.length > 1) {
                    stmt.setString(1, defPath[1]);
                    stmt.setString(2, defPath[2]);
                }
                else {
                    stmt.setString(1, "null");
                    stmt.setString(2, "null");
                }
                ResultSet rs = stmt.executeQuery();
                if (rs != null)
                result = mapper.map(rs);
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (defPath[0].equals("Allnames")) {
            try {
                mapper = new ContactMapper();
                stmt = conn.prepareCall(getAllContactsSQL);
                stmt.setString(1,defPath[1]);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (defPath[0].equals("Allgroups")) {
            try {
                mapper = new GroupMapper();
                stmt = conn.prepareCall(getAllGroupsSQL);
                stmt.setString(1,defPath[1]);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (defPath[0].equals("getNameByGroup")){
            try {
                mapper = new ContactMapper();
                stmt = conn.prepareCall(getNameByGroupSQL);
                stmt.setString(1,defPath[2]);
                stmt.setString(2,defPath[1]);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (defPath[0].equals("getGroupByName")){
            try {
                mapper = new GroupMapper();
                stmt = conn.prepareCall(getGroupByNameSQL);
                stmt.setString(1,defPath[2]);
                stmt.setString(2,defPath[1]);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (defPath[0].equals("getContactByName")){
            try {
                mapper = new ContactMapCreator();
                stmt = conn.prepareCall(getContactByNameSQL);
                stmt.setString(1,defPath[2]);
                stmt.setString(2,defPath[1]);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Object oldOb, Object newOb) {
        PreparedStatement stmt;
        if (oldOb instanceof Group) {
            Group group = (Group) oldOb;
            Group newGroup = (Group) newOb;
            try {
                stmt = conn.prepareCall(updateGroupSQL);
                stmt.setString(1,newGroup.getName());
                stmt.setString(2,group.getName());
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (oldOb instanceof Contact) {
            Contact contact = (Contact)oldOb;
            Contact newContact = (Contact)newOb;
            try {
                stmt = conn.prepareCall(updateContactSQL);
                stmt.setString(1,newContact.getName());
                stmt.setInt(2,Integer.parseInt(newContact.getPh_number()));
                stmt.setString(3,newContact.getGroup().getName());
                stmt.setString(4,contact.getGroup().getName());
                stmt.setInt(5,Integer.parseInt(contact.getPh_number()));
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Object ob) {
        PreparedStatement stmt;
        if (ob instanceof Group) {
            Group group = (Group) ob;
            try {
                stmt = conn.prepareCall(delGroupSQL);
                stmt.setString(1,group.getName());
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ob instanceof Contact) {
            Contact contact = (Contact)ob;
            try {
                stmt = conn.prepareCall(delContactSQL);
                stmt.setString(1,contact.getName());
                stmt.setInt(2,Integer.parseInt(contact.getPh_number()));
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    static class ContactMapper implements BaseMapper
    {

        @Override
        public Object map(ResultSet rs) {
            List<String> res = new ArrayList<>();
            try {
                while (rs.next()) {
                    res.add(rs.getString("Name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return res.toArray(new String[0]);
        }
    }
    static class CheckUser implements BaseMapper{
        @Override
        public Object map(ResultSet rs) {
            String result= "";
            try {
                while (rs.next()) {
                    int count = rs.getInt(1);
                    if (count == 1)
                        result = "Pass";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    static class GroupMapper implements BaseMapper{
        @Override
        public Object map(ResultSet rs) {
            List<String> res = new ArrayList<>();
            try {
                while (rs.next()) {
                    res.add(rs.getString("groupname"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return res.toArray(new String[0]);
        }
    }
    static class ContactMapCreator implements BaseMapper
    {
        @Override
        public Object map(ResultSet rs) {
            String name;
            int ph_num;
            String group;
            try {
                while (rs.next()) {
                    name = rs.getString(1);
                    ph_num = rs.getInt(2);
                    group = rs.getString(3);
                    Contact contact;
                    if (group == null)
                        contact = new Contact(name,""+ph_num,new Group(""));
                    else
                        contact = new Contact(name,""+ph_num,new Group(group));
                    return contact;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

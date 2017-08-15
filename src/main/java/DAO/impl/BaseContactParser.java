package DAO.impl;

import ConModel.Contact;
import ConModel.Group;
import DAO.BaseMapper;
import DAO.ContactDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseContactParser implements ContactDAO{

        private static final String DB_URL = "jdbc:postgresql://localhost:5432:postgres";

        private static final String USER = "postgres";
        private static final String PASS = "postgres";

        private static final String addContactSQL = "{call addContact(?,?,?,?)}";
        private static final String getAllContactsSQL = "{call getallcontacts(?)}";
        private static final String getNameByGroupSQL = "{call getNameByGroup(?,?)}";
        private static final String getContactByNameSQL = "{call getContactByName(?,?)}";
        private static final String updateContactSQL = "{call  updateContact(?,?,?,?,?)}";
        private static final String delContactSQL = "{call delContact(?,?)}";

        private Connection conn = null;

        public BaseContactParser() {
            try {
                conn = DriverManager.getConnection(DB_URL,USER,PASS);
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }
        @Override
        public void create(String user,Contact con) {
            PreparedStatement stmt;
            try {
                    stmt = conn.prepareCall(addContactSQL);
                    stmt.setString(1,user);
                    stmt.setString(2, con.getName());
                    stmt.setInt(3,Integer.parseInt(con.getPh_number()));
                    stmt.setString(4, con.getGroup().getName());
                    stmt.executeQuery();
                    stmt.close();
                    conn.close();
            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }

        @Override
        public Object read(String ob) {
            Object result = null;
            PreparedStatement stmt = null;
            String path = (String)ob;
            String[] defPath = path.split(" ");
            BaseMapper mapper;
            if (defPath[0].equals("getNameByGroup")){
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
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public Object readAll(String user){
            Object result = null;
            PreparedStatement stmt = null;
            BaseMapper mapper;
                try {
                    mapper = new ContactMapper();
                    stmt = conn.prepareCall(getAllContactsSQL);
                    stmt.setString(1,user);
                    ResultSet rs = stmt.executeQuery();
                    result = mapper.map(rs);
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void update(Contact oldContact, Contact newContact) {
            PreparedStatement stmt;
            try {
                stmt = conn.prepareCall(updateContactSQL);
                stmt.setString(1, newContact.getName());
                stmt.setInt(2,Integer.parseInt(newContact.getPh_number()));
                stmt.setString(3, newContact.getGroup().getName());
                stmt.setString(4, oldContact.getGroup().getName());
                stmt.setInt(5,Integer.parseInt(oldContact.getPh_number()));
                stmt.executeQuery();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete(Contact contact) {
            PreparedStatement stmt;
            try {
                    stmt = conn.prepareCall(delContactSQL);
                    stmt.setString(1, contact.getName());
                    stmt.setInt(2,Integer.parseInt(contact.getPh_number()));
                    stmt.executeQuery();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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

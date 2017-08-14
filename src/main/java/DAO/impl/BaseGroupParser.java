package DAO.impl;

import ConModel.Group;
import DAO.BaseMapper;
import DAO.GroupDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseGroupParser implements GroupDao {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432:postgres";

    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private static final String addGroupSQL = "{call addGroup(?,?)}";
    private static final String getAllGroupsSQL = "{call getallgroups(?)}";
    private static final String getGroupByNameSQL = "{call getGroup(?,?)}";
    private static final String updateGroupSQL = "{call updateGroup(?,?)}";
    private static final String delGroupSQL = "{call delGroup(?)}";

    private Connection conn = null;

    public BaseGroupParser() {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void create(String user,Group group) {
        PreparedStatement stmt;
            try {
                stmt = conn.prepareCall(addGroupSQL);
                stmt.setString(1,user);
                stmt.setString(2, group.getName());
                stmt.executeQuery();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public synchronized Object read(String ob) {
        Object result = null;
        PreparedStatement stmt = null;
        String path = (String)ob;
        String[] defPath = path.split(" ");
        BaseMapper mapper;
        if (defPath[0].equals("getGroupByName")){
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
    public Object readAll(String user) {
        Object result = null;
        PreparedStatement stmt = null;
        BaseMapper mapper;
        try {
                mapper = new GroupMapper();
                stmt = conn.prepareCall(getAllGroupsSQL);
                stmt.setString(1,user);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        try {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(Group oldGroup, Group newGroup) {
        PreparedStatement stmt;
        try {
                stmt = conn.prepareCall(updateGroupSQL);
                stmt.setString(1, newGroup.getName());
                stmt.setString(2, oldGroup.getName());
                stmt.executeQuery();
                stmt.close();
                conn.close();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    @Override
    public void delete(Group group) {
        PreparedStatement stmt;
            try {
                stmt = conn.prepareCall(delGroupSQL);
                stmt.setString(1, group.getName());
                stmt.executeQuery();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
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
}

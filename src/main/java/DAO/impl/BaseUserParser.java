package DAO.impl;

import ConModel.User;
import DAO.BaseMapper;
import DAO.BaseUserDAO;
import ConModel.services.ResultTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseUserParser extends BaseParser implements BaseUserDAO{

    private static final String checkUserSQL = "{call checkUser(?,?)}";
    private static final String countUsers = "{call countUsers()}";
    private static final String countUserContacts = "{call countUserContacts()}";
    private static final String countUserGroups = "{call countUserGroups()}";
    private static final String avgContactsInGroups = "{call avgUsersInGroups()}";
    private static final String avgUserContacts = "{call avgUserContacts()}";
    private static final String inactiveUsers = "{call inactiveUsers()}";

    private static BaseUserParser instace;

    private BaseUserParser() {
        super();
    }

    public static BaseUserParser getInstace() {
        if (instace == null)
                synchronized (BaseUserParser.class) {
                    if (instace == null)
                        instace = new BaseUserParser();
                }
        return instace;
    }

    @Override
    public void create(String user, User ob) {
    }

    @Override
    public Object read(String ob) {
        Object result = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = initConnection();
            String path = (String) ob;
            String[] defPath = path.split(" ");
            BaseMapper mapper;
            if (defPath[0].equals("logpass")) {
                try {
                    mapper = new CheckUser();
                    stmt = connection.prepareCall(checkUserSQL);
                    if (defPath.length > 2) {
                        stmt.setString(1, defPath[1]);
                        stmt.setString(2, defPath[2]);
                    } else {
                        stmt.setString(1, "null");
                        stmt.setString(2, "null");
                    }
                    ResultSet rs = stmt.executeQuery();

                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (defPath[0].equals("countUsers")) {
                try {
                    mapper = new CountUsers();
                    stmt = connection.prepareCall(countUsers);
                    ResultSet rs = stmt.executeQuery();
                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (defPath[0].equals("countUserContacts")) {
                try {
                    mapper = new TableMapper();
                    stmt = connection.prepareCall(countUserContacts);
                    ResultSet rs = stmt.executeQuery();
                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (defPath[0].equals("countUserGroups")) {
                try {
                    mapper = new TableMapper();
                    stmt = connection.prepareCall(countUserGroups);
                    ResultSet rs = stmt.executeQuery();
                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (defPath[0].equals("avgContactsInGroups")) {
                try {
                    mapper = new CountUsers();
                    stmt = connection.prepareCall(avgContactsInGroups);
                    ResultSet rs = stmt.executeQuery();
                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (defPath[0].equals("avgUserContacts")) {
                try {
                    mapper = new CountUsers();
                    stmt = connection.prepareCall(avgUserContacts);
                    ResultSet rs = stmt.executeQuery();
                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (defPath[0].equals("inactiveUsers")) {
                try {
                    mapper = new LoginMapper();
                    stmt = connection.prepareCall(inactiveUsers);
                    ResultSet rs = stmt.executeQuery();
                    if (rs != null) {
                        result = mapper.map(rs);
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                if (connection != null)
                        connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public Object readAll(String ob) {
        return null;
    }

    @Override
    public void update(User oldOb, User newOb) {
    }

    @Override
    public void delete(User ob) {
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

    static class CountUsers implements BaseMapper{
        @Override
        public Object map(ResultSet rs) {
            int result = 0;
            try {
                while (rs.next()) {
                    result = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    static class TableMapper implements BaseMapper{
        @Override
        public Object map(ResultSet rs) {
            List<ResultTable> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new ResultTable(rs.getString(1),rs.getInt(2)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    static class LoginMapper implements BaseMapper{
        @Override
        public Object map(ResultSet rs) {
            List<String> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}

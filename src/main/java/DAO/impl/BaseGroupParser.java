package DAO.impl;

import ConModel.Group;
import DAO.BaseMapper;
import DAO.GroupDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseGroupParser extends BaseParser implements GroupDao {

    private static final String addGroupSQL = "{call addGroup(?,?)}";
    private static final String getAllGroupsSQL = "{call getallgroups(?)}";
    private static final String getGroupByNameSQL = "{call getGroup(?,?)}";
    private static final String updateGroupSQL = "{call updateGroup(?,?)}";
    private static final String delGroupSQL = "{call delGroup(?)}";

    private static BaseGroupParser instace;

    public static BaseGroupParser getInstace() {
        if (instace == null)
            synchronized (BaseUserParser.class) {
                if (instace == null)
                    instace = new BaseGroupParser();
            }
        return instace;
    }
    private BaseGroupParser() {
    }

    @Override
    public void create(String user,Group group) {
        PreparedStatement stmt = null;
        Connection connection = null;
            try {
                connection = initConnection();
                stmt = connection.prepareCall(addGroupSQL);
                stmt.setString(1,user);
                stmt.setString(2, group.getName());
                stmt.executeQuery();
                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
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
            if (defPath[0].equals("getGroupByName")) {
                try {
                    mapper = new GroupMapper();
                    stmt = connection.prepareCall(getGroupByNameSQL);
                    stmt.setString(1, defPath[2]);
                    stmt.setString(2, defPath[1]);
                    ResultSet rs = stmt.executeQuery();
                    result = mapper.map(rs);
                    rs.close();
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
    public Object readAll(String user) {
        Object result = null;
        Connection connection = null;
        connection = initConnection();
        PreparedStatement stmt = null;
        BaseMapper mapper;
        try {
                mapper = new GroupMapper();
                stmt = connection.prepareCall(getAllGroupsSQL);
                stmt.setString(1,user);
                ResultSet rs = stmt.executeQuery();
                result = mapper.map(rs);
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
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
    public void update(Group oldGroup, Group newGroup) {
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
                connection = initConnection();
                stmt = connection.prepareCall(updateGroupSQL);
                stmt.setString(1, newGroup.getName());
                stmt.setString(2, oldGroup.getName());
                stmt.executeQuery();
                stmt.close();
        } catch (SQLException e) {
                e.printStackTrace();
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
    }

    @Override
    public void delete(Group group) {
        PreparedStatement stmt = null;
        Connection connection = null;
            try {
                connection = initConnection();
                stmt = connection.prepareCall(delGroupSQL);
                stmt.setString(1, group.getName());
                stmt.executeQuery();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
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

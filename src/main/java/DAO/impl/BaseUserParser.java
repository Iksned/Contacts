package DAO.impl;

import ConModel.User;
import DAO.BaseMapper;
import DAO.BaseUserDAO;

import java.sql.*;

public class BaseUserParser implements BaseUserDAO{
    private static final String DB_URL = "jdbc:postgresql://localhost:5432:postgres";

    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private static final String checkUserSQL = "{call checkUser(?,?)}";

    private Connection conn = null;

    public BaseUserParser() {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void create(String user, User ob) {
    }

    @Override
    public synchronized Object read(String ob) {
        Object result = null;
        PreparedStatement stmt = null;
        String path = (String)ob;
        String[] defPath = path.split(" ");
        BaseMapper mapper;
        if (defPath[0].equals("logpass")) {
            try {
                mapper = new CheckUser();
                stmt = conn.prepareCall(checkUserSQL);
                if (defPath.length > 2) {
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
}

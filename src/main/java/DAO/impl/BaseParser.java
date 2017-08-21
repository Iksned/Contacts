package DAO.impl;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

abstract class BaseParser {
    Connection connection;

    BaseParser() {
        InitialContext cxt = null;
        try {
            cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
            if (ds == null) {
                throw new Exception("Data source not found!");
            }
            connection = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

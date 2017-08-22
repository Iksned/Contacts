package DAO.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class BaseParser {


    BaseParser() {
    }

    Connection initConnection(){
       Connection connection = null;
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
       return connection;
    }
}

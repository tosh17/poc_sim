/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.sql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author qasta
 */
public class DbConnectionPool {
   
    private final ComboPooledDataSource cpds;
    
    public DbConnectionPool() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
            cpds.setJdbcUrl("jdbc:postgresql://localhost/mytest");
            cpds.setUser("user");
            cpds.setPassword("654321");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public void close() {
        cpds.close();
    }


}

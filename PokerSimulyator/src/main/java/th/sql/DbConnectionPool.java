/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.sql;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author qasta
 */
public class DbConnectionPool {
/*    
    private final ComboPooledDataSource cpds;
    
    public DbConnectionPool() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver"); //loads the jdbc driver
            cpds.setJdbcUrl("jdbc:postgresql://localhost/testdb");
            cpds.setUser("testdb");
            cpds.setPassword("testdb");
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

*/
}

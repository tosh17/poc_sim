package th.sql;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author qasta
 */
public class DBHolder {



    private DBHolder() {
    }
    
    private static final DbConnectionPool POOL = new DbConnectionPool();
    
    public static void nop() {
    }
    
    public static Connection getConnection() throws SQLException {
        return POOL.getConnection();
    }
    
    public static void close() {
        POOL.close();
    }

}

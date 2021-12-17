package multithread.example.multiserver.exam;

import java.sql.*;

public class DataProvider {

    private volatile static DataProvider uniqueInstance;
    
    private Connection con;
    private Statement stmt;

    public static DataProvider getInstance() {
        if (uniqueInstance == null) {
            synchronized (DataProvider.class) {
                if (uniqueInstance == null)
                    uniqueInstance = new DataProvider();
            }
        }
        return uniqueInstance;
    }
    
    private DataProvider() {
        try {
            SQLServerConnection myCon = new SQLServerConnection();
            con = myCon.getConnection();
            stmt = con.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Execute query (INSERT, UPDATE, DELETE) and return number of rows affected.
     * Return -1 if error found.
     * @param cmd
     * @return 
     */
    public int executeNonQuery(String cmd) {
        try {
            int i = stmt.executeUpdate(cmd);
            return i;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Execute query and return result set.
     * Return null if error found.
     * @param str
     * @return 
     */
    public ResultSet executeQuery(String str) {
        try {
            ResultSet rs = stmt.executeQuery(str);
            return rs;
        } catch (Exception e) {
            return null;
        }
    }
}

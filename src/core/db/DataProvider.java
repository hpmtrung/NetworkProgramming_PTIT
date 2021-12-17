package core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DataProvider {
private volatile static DataProvider instance;
    
    private Connection connection;
    private Statement stmt;

    public static DataProvider getInstance() {
        if (instance == null) {
            synchronized (DataProvider.class) {
                if (instance == null)
                    instance = new DataProvider();
            }
        }
        return instance;
    }
    
    private DataProvider() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String URL = "jdbc:sqlserver://localhost:1433;Database=KIEMTRALTM;user=KNLTM;password=123";
            connection = DriverManager.getConnection(URL);
            stmt = connection.createStatement();
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

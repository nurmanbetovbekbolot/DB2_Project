package dbproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=CRM_System";
//    private static final String Connection conn = DbConnection.connect(null,null);user = "sa";
//    private static final String password = "123";

    public static Connection connect(String user, String password) {
//        DatabaseConnection databaseConnection;
//        String username=(user==null)?"sa":user;
//        String pass=(password==null)?"123":password;
        Connection conn = null;
        try {
//            databaseConnection = new DatabaseConnection();
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Connected successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

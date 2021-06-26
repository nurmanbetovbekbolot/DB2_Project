package dbproject.db;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import dbproject.controller.LoginController;
import dbproject.controller.MainController;
import dbproject.dao.*;
import dbproject.exception.WrongCredentialsException;
import dbproject.model.UserModel;

import java.sql.*;

public abstract class DbConnection {
    private final String url;
    private static final String host = "127.0.0.1";
    private static final String port = "1433";
    private static final String database = "CRM_DB";
    public static UserDao userDao;
    public static OrderDao orderDao;
    public static PackageDao packageDao;
    public static ServiceDao serviceDao;
    public static TaskDao taskDao;
    public static UserModel loggedInUser;
//    public static UserModel loggedInUserRole;

    public DbConnection(String url) {
        this.url = url;
    }

    public Connection connect() throws SQLException {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new SQLServerDriver());
            conn = DriverManager.getConnection(this.url);

            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            System.out.println("rtgtedhnfbv");
            throw new WrongCredentialsException("Bitte geben Sie die richtigen Anmeldedaten ein");
        }
        return conn;
    }

    public static void initialize(String username, String password) throws SQLException {

        String connUrl = "jdbc:sqlserver://" + host + ":" + port +
                ";databaseName=" + database + ";user=" + username + ";password=" + password + ";";
        getCurUser(connUrl);
        String a = getCurUser(connUrl);
//        String b=getCurUserRole(connUrl,a);
        System.out.println("MyUser :" + a);
//        System.out.println("MyRole"+b);
        userDao = new UserDao(connUrl);
        orderDao = new OrderDao(connUrl);
        packageDao = new PackageDao(connUrl);
        serviceDao = new ServiceDao(connUrl);
        taskDao = new TaskDao(connUrl);
        loggedInUser = userDao.getCurUserRole(connUrl, a);
//        MainController.getUsers().removeAll();
//        MainController.getOrders().removeAll();
//        MainController.getPackages().removeAll();
//        MainController.getServices().removeAll();
//        MainController.getTasks().removeAll();
        System.out.println(loggedInUser);
    }

    public static String getCurUser(String url) throws SQLException {
        Connection conn = null;

        DriverManager.registerDriver(new SQLServerDriver());
        conn = DriverManager.getConnection(url);
        String SQL = "select SYSTEM_USER";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(SQL);
        if (rs.next()) {
            return rs.getString("");
        }
        return "";
    }

}

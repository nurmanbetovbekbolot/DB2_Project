package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {


    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String SQL = "select * from benutzer ";
        try (Connection conn = DbConnection.connect("kunde", "1");
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("benutzernr"));
                user.setFirstName(rs.getString("vorname"));
                user.setLastName(rs.getString("nachname"));
                user.setUserName(rs.getString("benutzer_name"));
                user.setPassword(rs.getString("password"));
                user.setCreatedDate(rs.getDate("erstellt_am"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

//        public User getUserById(int id) {
//        String SQL = "select * from benutzer where benutzernr = ? ";
//        try (Connection conn = DbConnection.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setInt(1, id);
//            try (ResultSet rs = statement.executeQuery()) {
//                if (rs.next()) {
//                    User user = new User();
//                    user.setUserId(rs.getInt("benutzernr"));
//                    user.setFirstName(rs.getString("vorname"));
//                    user.setLastName(rs.getString("nachname"));
//                    user.setUserName(rs.getString("benutzer_name"));
//                    user.setPassword(rs.getString("password"));
//                    user.setCreatedDate(rs.getDate("erstellt_am"));
//                    return user;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public User updateUser(User user) {
//        String SQL = "update benutzer set vorname=?, nachname=?,benutzer_name=?, password=? where benutzernr = ?";
//        try (Connection conn = DbConnection.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setString(1, user.getFirstName());
//            statement.setString(2, user.getLastName());
//            statement.setString(3, user.getUserName());
//            statement.setString(4, user.getPassword());
//            statement.setInt(5, user.getUserId());
//            statement.executeUpdate();
//            return user;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public User createUser(User user) {
//        String SQL = "insert into benutzer(vorname, nachname,benutzer_name, password, created_date) values (?, ?, ?, ?, now())";
//        try (Connection conn = DbConnection.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setString(1, user.getFirstName());
//            statement.setString(2, user.getLastName());
//            statement.setString(3, user.getUserName());
//            statement.setString(4, user.getPassword());
//            statement.executeUpdate();
//            return user;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public boolean deleteUserById(int id) {
//        String SQL = "delete from benutzer where benutzernr = ?";
//        try (Connection conn = DbConnection.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setInt(1, id);
//            statement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}

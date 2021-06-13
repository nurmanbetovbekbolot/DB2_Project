package dbproject.dao;

import dbproject.db.DBConnection;
import dbproject.dto.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {


    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String SQL = "select * from benutzer ";
        try (Connection conn = DBConnection.connect();
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

//    public User getUserById(int id) {
//        String SQL = "select * from users where id = ? ";
//        try (Connection conn = DB.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setInt(1, id);
//            try (ResultSet rs = statement.executeQuery()) {
//                if (rs.next()) {
//                    return User.builder()
//                            .id(rs.getInt("ID"))
//                            .name(rs.getString("NAME"))
//                            .password(rs.getString("PASSWORD"))
//                            .createdDate(rs.getTimestamp("CREATED_DATE"))
//                            .build();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public User updateUser(User user) {
//        String SQL = "update users set name=?, password=? where id = ?";
//        try (Connection conn = DB.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getPassword());
//            statement.setInt(3, user.getId());
//            statement.executeUpdate();
//            return user;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public User createUser(User user) {
//        String SQL = "insert into users(name, password, created_date) values (?, ?, now())";
//        try (Connection conn = DB.connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getPassword());
//            statement.executeUpdate();
//            return user;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public boolean deleteUserById(int id) {
//        String SQL = "delete from users where id = ?";
//        try (Connection conn = DB.connect();
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

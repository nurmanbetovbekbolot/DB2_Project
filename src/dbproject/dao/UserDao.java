package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.User;
import dbproject.model.Role;
import dbproject.model.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class UserDao extends DbConnection {

    public UserDao(String url) {
        super(url);
    }


    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String SQL = "select b.*,r2.name as usersrole from benutzer b join benutzer_role br on b.benutzernr = br.benutzernr join role r2 on r2.rolenr = br.rolenr and r2.name !='ADMIN'";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("benutzernr"));
                user.setFirstName(rs.getString("vorname"));
                user.setLastName(rs.getString("nachname"));
                user.setUserName(rs.getString("benutzer_name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("usersrole"));
                user.setCreatedDate(rs.getDate("erstellt_am"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ObservableList<User> getUsersByName(String uname) {
        ObservableList<User> users = FXCollections.observableArrayList();
        String SQL = "select b.*,r2.name as usersrole from benutzer b join benutzer_role br on b.benutzernr = br.benutzernr join role r2 on r2.rolenr = br.rolenr and r2.name !='ADMIN' where b.vorname LIKE '%uname%' or b.nachname LIKE '%uname%' ";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, uname);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("benutzernr"));
                    user.setFirstName(rs.getString("vorname"));
                    user.setLastName(rs.getString("nachname"));
                    user.setUserName(rs.getString("benutzer_name"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("usersrole"));
                    user.setCreatedDate(rs.getDate("erstellt_am"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


//    public String getCurrentUser() throws SQLException {
//        String SQL = "select SYSTEM_USER";
//        Connection conn = connect();
//        Statement statement = conn.createStatement();
//        ResultSet rs = statement.executeQuery(SQL);
//        if (rs.next()){
//            return rs.getString("");
//            }
//        return "";
//    }

//    public ObservableList<Role> getUsersRole() {
//        ObservableList<Role> roles = FXCollections.observableArrayList();
//        String SQL = "select b.*,r2.name from benutzer b join benutzer_role br on b.benutzernr = br.benutzernr join role r2 on r2.rolenr = br.rolenr";
//        try (Connection conn = connect();
//             Statement statement = conn.createStatement();
//             ResultSet rs = statement.executeQuery(SQL)) {
//            while (rs.next()) {
//                User user = new User();
//                user.setUserId(rs.getInt("benutzernr"));
//                user.setFirstName(rs.getString("vorname"));
//                user.setLastName(rs.getString("nachname"));
//                user.setUserName(rs.getString("benutzer_name"));
//                user.setPassword(rs.getString("password"));
//                user.setCreatedDate(rs.getDate("erstellt_am"));
//                user
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }


    public UserModel getCurUserRole(String url, String username) throws SQLException {

        String SQL = "select b.benutzernr, r.name from benutzer_role as br join benutzer as b on b.benutzernr=br.benutzernr join role as r on r.rolenr=br.rolenr where b.benutzer_name=?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    UserModel userModel = new UserModel();
                    userModel.setId(rs.getInt("benutzernr"));
                    userModel.setRole(rs.getString("name"));
                    return userModel;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public User getUserById(int id) {
        String SQL = "select b.*,r2.name as usersrole from benutzer b join benutzer_role br on b.benutzernr = br.benutzernr join role r2 on r2.rolenr = br.rolenr where b.benutzernr=?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("benutzernr"));
                    user.setFirstName(rs.getString("vorname"));
                    user.setLastName(rs.getString("nachname"));
                    user.setUserName(rs.getString("benutzer_name"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("usersrole"));
                    user.setCreatedDate(rs.getDate("erstellt_am"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByLogin(String login) {
        String SQL = "select b.*,r2.name as usersrole from benutzer b join benutzer_role br on b.benutzernr = br.benutzernr join role r2 on r2.rolenr = br.rolenr where benutzer_name=?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("benutzernr"));
                    user.setFirstName(rs.getString("vorname"));
                    user.setLastName(rs.getString("nachname"));
                    user.setUserName(rs.getString("benutzer_name"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("usersrole"));
                    user.setCreatedDate(rs.getDate("erstellt_am"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User updateUser(User user, Role role) {
        String SQL = "{call sp_update_user(?, ?, ?, ?,?,?)}";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getPassword());
            statement.setString(5, String.valueOf(role));
            statement.setInt(6, user.getUserId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public User createUser(User user) {
//        String SQL = "insert into benutzer(vorname, nachname,benutzer_name, password, erstellt_am) values (?, ?, ?, ?, getdate())";
//        try (Connection conn = connect();
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


    public User createUser(User user, Role role) {
        String SQL = "{call sp_create_user(?, ?, ?, ?,?)}";
        try (Connection conn = connect();
             CallableStatement statement = conn.prepareCall(SQL)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getPassword());
            statement.setString(5, String.valueOf(role));
            statement.execute();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUserById(int id) {
        String SQL = "{call sp_delete_user(?)}";
        try (Connection conn = connect();
             CallableStatement statement = conn.prepareCall(SQL)) {
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public Role getRole(int userId){
//        String SQL = "select r.name from benutzer as b join benutzer_role as br on b.benutzernr=br.benutzernr join role as r on r.rolenr=r.rolenr where b.benutzernr=?";
//        try (Connection conn = connect();
//             PreparedStatement statement = conn.prepareStatement(SQL)) {
//            statement.setInt(1, userId);
//            try (ResultSet rs = statement.executeQuery()) {
//                if (rs.next()) {
//                    Role.ADMIN
//                    User user = new User();
//                    rs.getString("")
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


}

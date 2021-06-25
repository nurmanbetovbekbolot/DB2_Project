package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Service;
import dbproject.dto.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TaskDao extends DbConnection {

    private DbConnection dbConnection;

    public TaskDao(String url) {
        super(url);
//        this.dbConnection = new DbConnection(url);
    }

//    public TaskDao() {
//    }

    public ObservableList<Task> getTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        String SQL = "select * from aufgabe ";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("aufgabenr"));
                t.setName(rs.getString("name"));
                t.setDescription(rs.getString("bezeichnung"));
                t.setService(rs.getInt("dienst"));
                t.setCreatedDate(rs.getDate("erstellt_am"));
                tasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public ObservableList<Task> getTasksByServiceId(int sId) {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        String SQL = "Select a.*  FROM aufgabe a inner join dienst d on a.dienst=d.dienstnr where d.dienstnr =?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, sId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Task t = new Task();
                    t.setTaskId(rs.getInt("aufgabenr"));
                    t.setName(rs.getString("name"));
                    t.setDescription(rs.getString("bezeichnung"));
                    t.setService(rs.getInt("dienst"));
                    t.setCreatedDate(rs.getDate("erstellt_am"));
                    tasks.add(t);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    public Task getTaskById(int id) {
        String SQL = "select * from aufgabe where aufgabenr = ? ";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Task t = new Task();
                    t.setTaskId(rs.getInt("aufgabenr"));
                    t.setName(rs.getString("name"));
                    t.setDescription(rs.getString("bezeichnung"));
                    t.setService(rs.getInt("dienst"));
                    t.setCreatedDate(rs.getDate("erstellt_am"));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task getTaskByName(String name) {
        String SQL = "select * from aufgabe where name = ? ";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Task t = new Task();
                    t.setTaskId(rs.getInt("aufgabenr"));
                    t.setName(rs.getString("name"));
                    t.setDescription(rs.getString("bezeichnung"));
                    t.setService(rs.getInt("dienst"));
                    t.setCreatedDate(rs.getDate("erstellt_am"));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task createTask(Task t) {
        String SQL = "insert into aufgabe(name, bezeichnung,dienst,erstellt_am) values (?,?,?,getdate())";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, t.getName());
            statement.setString(2, t.getDescription());
            statement.setInt(3, t.getService());
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task updateTask(Task t) {
        String SQL = "update aufgabe set name=?,bezeichnung=?,dienst=? where aufgabenr = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, t.getName());
            statement.setString(2, t.getDescription());
            statement.setInt(3, t.getService());
            statement.setInt(4, t.getTaskId());
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTaskById(int id) {
        String SQL = "delete from aufgabe where aufgabenr = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

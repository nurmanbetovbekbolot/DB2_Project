package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ServiceDao {

    public ObservableList<Service> getServices() {
        ObservableList<Service> services = FXCollections.observableArrayList();
        String SQL = "select * from dienst ";
        try (Connection conn = DbConnection.connect("sa", "123");
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("dienstnr"));
                service.setName(rs.getString("name"));
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public Service getServiceById(int id) {
        String SQL = "select * from dienst where dienstnr = ? ";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Service s = new Service();
                    s.setServiceId(rs.getInt("dienstnr"));
                    s.setName(rs.getString("name"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Service createService(Service s) {
        String SQL = "insert into dienst(name) values (?)";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, s.getName());

            statement.executeUpdate();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Service updateService(Service s) {
        String SQL = "update dienst set name=? where dienstnr = ?";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, s.getName());
            statement.setInt(2, s.getServiceId());

            statement.executeUpdate();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteServiceFromPackage(int serviceId){
        String SQL = "delete from paket_dienste where dienstnr = ?";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, serviceId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteServiceById(int id) {
        String SQL = "delete from dienst where dienstnr = ?";
        try (Connection conn = DbConnection.connect("sa", "123");
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

package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Package;
import dbproject.dto.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

public class ServiceDao extends DbConnection {

    public ServiceDao(String url) {
        super(url);
    }

    public ObservableList<Service> getServices() {
        ObservableList<Service> services = FXCollections.observableArrayList();
        String SQL = "select * from dienst ";
        try (Connection conn = connect();
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

    public ObservableList<Service> getServicesByPackageId(int pId) {
        ObservableList<Service> services = FXCollections.observableArrayList();
        String SQL = "Select d.*  FROM dienst d inner join paket_dienste pd on d.dienstnr=pd.dienstnr where pd.paketnr =?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, pId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();
                    service.setServiceId(rs.getInt("dienstnr"));
                    service.setName(rs.getString("name"));
                    services.add(service);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }


    public Service getServiceById(int id) {
        String SQL = "select * from dienst where dienstnr = ? ";
        try (Connection conn = connect();
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

    //XML
    public void getAllServicesXML() {
        String SQL = "SELECT (SELECT * FROM dienst\n" +
                "FOR XML PATH('DIENST'), TYPE, ELEMENTS , ROOT('DIENSTE')) as serviceXML";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                SQLXML xml= rs.getSQLXML("serviceXML");
                String values = xml.getString();
                PrintWriter out = new PrintWriter("D://DB_2/CRM-System/services.xml");
                out.println(values);
                out.flush();
            }
        } catch (SQLException| FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Service getServiceByName(String name) {
        String SQL = "select * from dienst where name = ? ";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, name);
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
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, s.getName());

            statement.executeUpdate();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addServiceToPackage1(String name) {
        String SQL = "insert into dienst(name) values (?)";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, name);

            statement.executeUpdate();
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }


    public void addServiceToPackage2(int pId,int sId) {
        String SQL = "insert into paket_dienste values (?,?)";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, pId);
            statement.setInt(2, sId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Service updateService(Service s) {
        String SQL = "update dienst set name=? where dienstnr = ?";
        try (Connection conn = connect();
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

    public boolean deleteServiceFromPackage(int serviceId) {
        String SQL = "delete from paket_dienste where dienstnr = ?";
        try (Connection conn = connect();
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

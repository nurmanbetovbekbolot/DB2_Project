package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {

    public List<Service> getServices() {
        List<Service> services = new ArrayList<>();
        String SQL = "select * from dienst ";
        try (Connection conn = DbConnection.connect(null,null);
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
}

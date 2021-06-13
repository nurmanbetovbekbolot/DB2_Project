package dbproject.dao;

import dbproject.db.DBConnection;
import dbproject.dto.Package;
import dbproject.dto.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PackageDao {

    public List<Package> getUsers() {
        List<Package> packages = new ArrayList<>();
        String SQL = "select * from paket ";
        try (Connection conn = DBConnection.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt("benutzernr"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("bezeichnung"));
                p.setPrice(rs.getDouble("preis"));
                p.setDiscountprice(rs.getDouble("discountpreis"));
                packages.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }
}

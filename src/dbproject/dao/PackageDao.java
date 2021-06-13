package dbproject.dao;

import dbproject.db.DBConnection;
import dbproject.dto.Package;
import dbproject.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDao {

    public List<Package> getPackages() {
        List<Package> packages = new ArrayList<>();
        String SQL = "select * from paket ";
        try (Connection conn = DBConnection.connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt("paketnr"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("bezeichnung"));
                p.setPrice(rs.getBigDecimal("preis"));
                p.setDiscountprice(rs.getBigDecimal("discountpreis"));
                packages.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

        public Package getPackageById(int id) {
        String SQL = "select * from paket where id = ? ";
        try (Connection conn = DBConnection.connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Package p = new Package();
                    p.setPackageId(rs.getInt("paketnr"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("bezeichnung"));
                    p.setPrice(rs.getBigDecimal("preis"));
                    p.setDiscountprice(rs.getBigDecimal("discountpreis"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
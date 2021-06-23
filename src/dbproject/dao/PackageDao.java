package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;

public class PackageDao extends DbConnection{
    private DbConnection dbConnection;

    public PackageDao(String url) {
        super(url);
//        this.dbConnection = new DbConnection(url);
    }

//    public PackageDao() {
//    }

    public ObservableList<Package> getPackages() {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        String SQL = "select * from paket ";
        try (Connection conn = connect() ;
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt("paketnr"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("bezeichnung"));
                p.setPrice(rs.getBigDecimal("preis"));
                p.setDiscountPrice(rs.getBigDecimal("discountpreis"));
                packages.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    public Package getPackageById(int id) {
        String SQL = "select * from paket where paketnr = ? ";
        try (Connection conn = connect() ;
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Package p = new Package();
                    p.setPackageId(rs.getInt("paketnr"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("bezeichnung"));
                    p.setPrice(rs.getBigDecimal("preis"));
                    p.setDiscountPrice(rs.getBigDecimal("discountpreis"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Package createPackage(Package p) {
        String SQL = "insert into paket(name, bezeichnung, preis,discountpreis) values (?, ?, ?, ?)";
        try (Connection conn = connect() ;
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, p.getName());
            statement.setString(2, p.getDescription());
            statement.setBigDecimal(3, BigDecimal.valueOf(p.getPrice()));
            statement.setBigDecimal(4, BigDecimal.valueOf(p.getDiscountPrice()));
            statement.executeUpdate();
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Package updatePackage(Package p) {
        String SQL = "update paket set name=?, bezeichnung=?, preis=?, discountpreis=? where paketnr = ?";
        try (Connection conn = connect() ;
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, p.getName());
            statement.setString(2, p.getDescription());
            statement.setBigDecimal(3, BigDecimal.valueOf(p.getPrice()));
            statement.setBigDecimal(4, BigDecimal.valueOf(p.getDiscountPrice()));
            statement.setInt(5, p.getPackageId());
            statement.executeUpdate();
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletePackageById(int id) {
        String SQL = "delete from paket where paketnr = ?";
        try (Connection conn = connect() ;
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
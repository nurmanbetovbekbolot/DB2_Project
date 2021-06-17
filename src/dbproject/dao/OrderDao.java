package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import dbproject.dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;

public class OrderDao {

    public ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String SQL = "select * from bestellung ";
        try (Connection conn = DbConnection.connect("kunde", "1");
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("bestellungnr"));
                o.setName(rs.getString("name"));
                o.setDescription(rs.getString("bezeichnung"));
                o.setClientId(rs.getInt("kunde"));
                o.setManagerId(rs.getInt("manager"));
                o.setPaketId(rs.getInt("paket"));
                o.setCreatedDate(rs.getDate("erstellt_am"));
                orders.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    public Order getOrderById(int id) {
        String SQL = "select * from bestellung where bestellungnr = ? ";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Package p = new Package();
                    Order o = new Order();
                    o.setOrderId(rs.getInt("bestellungnr"));
                    o.setName(rs.getString("name"));
                    o.setDescription(rs.getString("bezeichnung"));
                    o.setClientId(rs.getInt("kunde"));
                    o.setManagerId(rs.getInt("manager"));
                    o.setPaketId(rs.getInt("paket"));
                    o.setCreatedDate(rs.getDate("erstellt_am"));
                    return o;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Order createOrder(Order o) {
        String SQL = "insert into bezeichnung(name,bezeichnung, kunde, manager, paket, erstellt_am) values (?,?, ?, ?, ?, getdate())";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, o.getName());
            statement.setString(2, o.getDescription());
            statement.setInt(3, o.getClientId());
            statement.setInt(4, o.getManagerId());
            statement.setInt(5,o.getPaketId());
            statement.executeUpdate();
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order updateOrder(Order o) {
        String SQL = "update order set name=?, bezeichnung=?, kunde=?, manager=?, paket=? where bestellungnr = ?";
        try (Connection conn = DbConnection.connect("sa", "123");
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, o.getName());
            statement.setString(2, o.getDescription());
            statement.setInt(3, o.getClientId());
            statement.setInt(4, o.getManagerId());
            statement.setInt(5,o.getPaketId());
            statement.executeUpdate();
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteOrderById(int id) {
        //TODO delete all references
        String SQL = "delete from bestellung where bestellungnr = ?";
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


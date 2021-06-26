package dbproject.dao;

import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

public class OrderDao extends DbConnection {

    public OrderDao(String url) {
        super(url);
    }


    public ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String SQL = "select * from bestellung ";
        try (Connection conn = connect();
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

    //    String SQL = "b.bestellungnr,b.name,o.clientId,o.managerId,o.packageId,o.createdDate) FROM bestellung as b WHERE o.clientId.id=:id ORDER BY o.id DESC"
    public ObservableList<Order> getOrdersByClientId(int id) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String SQL = "SELECT b.bestellungnr,b.name,b.bezeichnung, b.kunde, b.manager, b.paket, b.erstellt_am FROM bestellung AS b\n" +
                "WHERE b.kunde = ? ORDER BY b.bestellungnr DESC";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    public ObservableList<Order> getOrdersByManagerId(int id) {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String SQL = "SELECT b.bestellungnr,b.name,b.bezeichnung, b.kunde, b.manager, b.paket, b.erstellt_am FROM bestellung AS b\n" +
                "WHERE b.manager = ? ORDER BY b.bestellungnr DESC";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    //XML
    public void getAllOrdersXML() {
        String SQL = "SELECT (SELECT * FROM bestellung\n" +
                "FOR XML PATH('BESTELLUNG'), TYPE, ELEMENTS , ROOT('BESTELLUNGEN')) as orderXML";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(SQL)) {
            while (rs.next()) {
                SQLXML xml= rs.getSQLXML("orderXML");
                String values = xml.getString();
                PrintWriter out = new PrintWriter("D://DB_2/CRM-System/orders.xml");
                out.println(values);
                out.flush();
            }
        } catch (SQLException| FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderById(int id) {
        String SQL = "select * from bestellung where bestellungnr = ? ";
        try (Connection conn = connect();
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

    public Order getOrderByName(String orderName) {
        String SQL = "select * from bestellung where name = ? ";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, orderName);
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
        String SQL = "insert into bestellung(name,bezeichnung, kunde, manager, paket, erstellt_am) values (?,?, ?, ?, ?, getdate())";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, o.getName());
            statement.setString(2, o.getDescription());
            statement.setInt(3, o.getClientId());
            statement.setInt(4, o.getManagerId());
            statement.setInt(5, o.getPaketId());
            statement.executeUpdate();
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order updateOrder(Order o) {
        String SQL = "update bestellung set name=?, bezeichnung=?, kunde=?, manager=?, paket=? where bestellungnr = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, o.getName());
            statement.setString(2, o.getDescription());
            statement.setInt(3, o.getClientId());
            statement.setInt(4, o.getManagerId());
            statement.setInt(5, o.getPaketId());
            statement.setInt(6, o.getOrderId());
            statement.executeUpdate();
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteOrderById(int id) {
        String SQL = "delete from bestellung where bestellungnr = ?";
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


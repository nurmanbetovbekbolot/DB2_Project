package dbproject.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=CRM_System";
    private static final String user = "sa";
    private static final String password = "123";

    public static Connection connect() {
//        DatabaseConnection databaseConnection;
        Connection conn = null;
        try {
//            databaseConnection = new DatabaseConnection();
            conn = DriverManager.getConnection(url,user,password);

            System.out.println("Connected successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }



//    public List<Mitarbeiter> getAllMitarbeiters() {
//        List<Mitarbeiter> mitarbeiters = new ArrayList<>();
//        String SQL = "SELECT * FROM mitarbeiter ";
//        try (
//                Connection conn = connect();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(SQL)) {
//            while (rs.next()) {
//                Mitarbeiter mitarbeiter = new Mitarbeiter();
//                mitarbeiter.setMitNr(rs.getInt("MitNr"));
//                mitarbeiter.setFaName(rs.getString("FaName"));
//                mitarbeiter.setVoName(rs.getString("VoName"));
//                mitarbeiter.setGehalt(rs.getBigDecimal("Gehalt"));
//                mitarbeiter.setAbtNr(rs.getInt("AbtNr"));
//                mitarbeiters.add(mitarbeiter);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        return mitarbeiters;
//    }
//
//
//    public List<Projekt> getAllProjekts() {
//        List<Projekt> projekts = new ArrayList<>();
//        String SQL = "SELECT * FROM projekt";
//        try (
//                Connection conn = connect();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(SQL)) {
//            while (rs.next()) {
//                Projekt projekt = new Projekt();
//                projekt.setProNr(rs.getInt("ProNr"));
//                projekt.setProName(rs.getString("ProName"));
//                projekt.setGeplStuUmfang(rs.getInt("GeplStuUmfang"));
//                projekt.setKuNr(rs.getInt("KuNr"));
//                projekts.add(projekt);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        return projekts;
//    }
//
//    public List<Taetigkeit> getAllTaetigkeits() {
//        List<Taetigkeit> taetigkeits = new ArrayList<>();
//        String SQL = "SELECT * FROM tätigkeit";
//        try (
//                Connection conn = connect();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(SQL)) {
//            while (rs.next()) {
//                Taetigkeit taetigkeit = new Taetigkeit();
//                taetigkeit.setTaetNr(rs.getInt("TätNr"));
//                taetigkeit.setTaetName(rs.getString("TätName"));
//                taetigkeit.setTaetStuSatz(rs.getBigDecimal("TätStuSatz"));
//                taetigkeits.add(taetigkeit);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        return taetigkeits;
//    }
//
//    public List<Abteilung> getAllAbteilungs() {
//        List<Abteilung> abteilungs = new ArrayList<>();
//        String SQL = "SELECT * FROM abteilung";
//        try (
//                Connection conn = connect();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(SQL)) {
//            while (rs.next()) {
//                Abteilung abteilung = new Abteilung();
//                abteilung.setAbtNr(rs.getInt("AbtNr"));
//                abteilung.setAbtName(rs.getString("AbtName"));
//                abteilungs.add(abteilung);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        return abteilungs;
//    }
//
//    public List<Geleistet> getAllGeleistets() {
//        List<Geleistet> geleistets = new ArrayList<>();
//        String SQL = "SELECT * FROM geleistet";
//        try (
//                Connection conn = connect();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(SQL)) {
//            while (rs.next()) {
//                Geleistet geleistet = new Geleistet();
//                geleistet.setGelNr(rs.getInt("GeNr"));
//                geleistet.setBegin(rs.getString("Begin"));
//                geleistet.setEnde(rs.getString("Ende"));
//                geleistet.setMitNr(rs.getInt("MitNr"));
//                geleistet.setTaetNr(rs.getInt("TätNr"));
//                geleistet.setProNr(rs.getInt("ProNr"));
//
//                geleistets.add(geleistet);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        return geleistets;
//    }
//
//    public List<Kunde> getAllKundes() {
//        List<Kunde> kundes = new ArrayList<>();
//        String SQL = "SELECT * FROM kunde ";
//        try (
//                Connection conn = connect();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(SQL)) {
//            while (rs.next()) {
//                Kunde kunde = new Kunde();
//                kunde.setKuNr(rs.getInt("KuNr"));
//                kunde.setKuName(rs.getString("KuName"));
//                kunde.setPLZ(rs.getString("PLZ"));
//                kunde.setOrt(rs.getString("ORT"));
//                kundes.add(kunde);
//
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        return kundes;
//    }
}

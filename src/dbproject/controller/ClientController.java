package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import dbproject.dto.Service;
import dbproject.dto.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class ClientController {
    private Main main;
    private Stage stage;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> orderNameColumn;

    @FXML
    private TableColumn<Order, String> orderDescriptionColumn;

    @FXML
    private TableColumn<Order, Integer> orderClientColumn;

    @FXML
    private TableColumn<Order, Integer> orderManagerColumn;

    @FXML
    private TableColumn<Order, Integer> orderPackageColumn;

    @FXML
    private TableColumn<Order, Date> orderCreatedAtColumn;

    @FXML
    private Label oId;

    @FXML
    private Label oName;

    @FXML
    private Label oDesc;

    @FXML
    private Label oClient;

    @FXML
    private Label oManager;

    @FXML
    private Label oPackage;

    @FXML
    private Label oDate;

    @FXML
    private TableView<Package> packageTable;

    @FXML
    private TableColumn<Package, Integer> packageIdColumn;


    @FXML
    private TableColumn<Package, String> packageNameColumn;

    @FXML
    private TableColumn<Package, String> descriptionColumn;

    @FXML
    private TableColumn<Package, Double> preisColumn;

    @FXML
    private TableColumn<Package, Double> discountPreisColumn;

    @FXML
    private Label pId;

    @FXML
    private Label pName;

    @FXML
    private Label pDesc;

    @FXML
    private Label pPrice;

    @FXML
    private Label pDiscPrice;

    @FXML
    private Button logout;

    private static final ObservableList<Order> myOrders = FXCollections.observableArrayList();
    private static final ObservableList<Package> myPackages = FXCollections.observableArrayList();

    @FXML
    protected void initialize (){
        orderNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        orderClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        orderManagerColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        orderPackageColumn.setCellValueFactory(new PropertyValueFactory<>("paketId"));
        orderCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        packageIdColumn.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        preisColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountPreisColumn.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));

        myOrders.addAll(DbConnection.orderDao.getOrdersByClientId(DbConnection.loggedInUser.getId()));
        myPackages.addAll(DbConnection.packageDao.getPackagesInOrder(myOrders.get(0).getOrderId()));

        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showOrdersDetailedInfo(newValue);
        });


        packageTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showPackagesDetailedInfo(newValue);
        });


    }

    private void showOrdersDetailedInfo(Order o){
        oId.setText(""+o.getOrderId());
        oName.setText(o.getName());
        oDesc.setText(o.getDescription());
        oClient.setText(DbConnection.userDao.getUserById(o.getClientId()).toString());
        oManager.setText(DbConnection.userDao.getUserById(o.getManagerId()).toString());
        oPackage.setText(DbConnection.packageDao.getPackageById(o.getPaketId()).toString());
        oDate.setText(o.getCreatedDate().toString());

    }

    private void showPackagesDetailedInfo(Package p){
        pId.setText(""+p.getPackageId());
        pName.setText(p.getName());
        pDesc.setText(p.getDescription());
        pPrice.setText(String.valueOf(p.getPrice()));
        pDiscPrice.setText(String.valueOf(p.getDiscountPrice()));

    }

    public static ObservableList<Order> getMyOrders() {
        return myOrders;
    }

    public void setMain(Main main) {
        this.main = main;
        orderTable.setItems(myOrders);
        packageTable.setItems(myPackages);

    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/Login.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    private void alert(String text) {
        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                text,
                ButtonType.CLOSE
        );
        alert.initOwner(main.getPrimaryStage());
        alert.showAndWait();
    }
}

package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import dbproject.dto.Service;
import dbproject.dto.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

    private static final ObservableList<Order> myOrders = FXCollections.observableArrayList();

    @FXML
    protected void initialize (){
        orderNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        orderClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        orderManagerColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        orderPackageColumn.setCellValueFactory(new PropertyValueFactory<>("paketId"));
        orderCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        myOrders.addAll(DbConnection.orderDao.getOrdersByClientId(DbConnection.loggedInUser.getId()));

    }

//    @FXML
//    public void handleSearchVehicleButton() {
//        try {
//            OperatingEnvironment operatingEnvironment = (OperatingEnvironment) operatingEnvironmentToggleGroup.getSelectedToggle().getUserData();
//            int requiredDistance = Integer.parseInt(minDistanceRequiredField.getText());
//            ObservableList<VehicleBindingAdapter> matchedVehiclesBindingAdapters = FXCollections.observableArrayList();
//            vehicleManagement.findMatchingVehicles(requiredDistance, operatingEnvironment).forEach(vehicle ->
//                    matchedVehiclesBindingAdapters.add(vehicle.getVehicleBindingAdapter()));
//            vehicleTable.setItems(matchedVehiclesBindingAdapters);
//        } catch (NumberFormatException e) {
//            alert(Alert.AlertType.WARNING, "Not valid distance", "Please select a valid distance.");
//
//        } catch (NullPointerException e) {
//            alert(Alert.AlertType.WARNING, "No Vehicle Selected", "Please select a vehicle in the table.");
//        }
//
//    }


    public static ObservableList<Order> getMyOrders() {
        return myOrders;
    }

    public void setMain(Main main) {
        this.main = main;
        orderTable.setItems(myOrders);
    }
}

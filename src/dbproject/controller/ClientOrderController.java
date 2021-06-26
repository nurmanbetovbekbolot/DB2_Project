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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Date;

public class ClientOrderController {


    private Main main;
    private Stage stage;
    private Order selectedOrder;

//    @FXML
//    private TableView<Order> orderTable;
//
//    @FXML
//    private TableColumn<Order, String> orderNameColumn;
//
//    @FXML
//    private TableColumn<Order, String> orderDescriptionColumn;
//
//    @FXML
//    private TableColumn<Order, Integer> orderClientColumn;
//
//    @FXML
//    private TableColumn<Order, Integer> orderManagerColumn;
//
//    @FXML
//    private TableColumn<Order, Integer> orderPackageColumn;
//
//    @FXML
//    private TableColumn<Order, Date> orderCreatedAtColumn;

    @FXML
    private Label forPinfo;

    @FXML
    private Label forInfo;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> serviceIdColumn;


    @FXML
    private TableColumn<Service, String> serviceNameColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Integer> taskIdColumn;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    private TableColumn<Task, Integer> taskServiceColumn;

    @FXML
    private TableColumn<Task, Date> taskCreatedAtColumn;

    @FXML
    private TextField serviceTextField;

    @FXML
    private TextField taskNameTextField;

    @FXML
    private TextField taskDescTextField;

    @FXML
    private Button logout;

    private static final ObservableList<Order> myOrders = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        forInfo.setText("");


        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
//        taskServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        serviceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue!=null)
//            forInfo.setText("Nr: "+(newValue==null?"":newValue.getServiceId())+" | Name: "+(newValue==null?"":newValue.getName()));
            showServicesTasks(newValue);
        });
    }

    private void showServicesTasks(Service service){
        if (service != null){
            taskTable.setItems(DbConnection.taskDao.getTasksByServiceId(service.getServiceId()));
        }
        else {
            serviceTable.setItems(null);
        }
    }

    public void setSelectedOrder(Order o) {
        this.selectedOrder = o;
        Package p = DbConnection.packageDao.getPackageById(o.getPaketId());
        forPinfo.setText("PaketNr: "+p.getPackageId()+" | Name: "+p.getName()+" | Bezeichnung: "+p.getDescription()+" | Preis: "+p.getPrice()+" | Discount: "+p.getDiscountPrice());

    }

    public void setMain(Main main) {
        this.main = main;
        serviceTable.setItems(DbConnection.serviceDao.getServicesByPackageId(selectedOrder.getPaketId()));

    }

    public void setStage(Stage stage) {
        this.stage = stage;
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

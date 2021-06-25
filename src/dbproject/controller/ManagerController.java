package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.*;
import dbproject.dto.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class ManagerController {


    //User
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> vorNameColumn;

    @FXML
    private TableColumn<User, String> nachNameColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Date> createdAtColumn;

    @FXML
    private Label uId;

    @FXML
    private Label uFname;

    @FXML
    private Label uLname;

    @FXML
    private Label uLogin;

    @FXML
    private Label uRole;

    @FXML
    private Label uPass;

    @FXML
    private Label uDate;

    //Order
    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;


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

    //Package

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


    //Service

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> serviceIdColumn;


    @FXML
    private TableColumn<Service, String> serviceNameColumn;


    //Task

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
    private Label tId;

    @FXML
    private Label tName;

    @FXML
    private Label tDesc;

    @FXML
    private Label tService;

    @FXML
    private Label tDate;


    private Main main;

    private static final ObservableList<User> users = FXCollections.observableArrayList();
    private static final ObservableList<Order> orders = FXCollections.observableArrayList();
    private static final ObservableList<Package> packages = FXCollections.observableArrayList();
    private static final ObservableList<Service> services = FXCollections.observableArrayList();
    private static final ObservableList<Task> tasks = FXCollections.observableArrayList();


    public ManagerController() {
    }

    @FXML
    protected void initialize() {
//        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
//        vorNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        nachNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
//        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
//        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
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

        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        users.addAll(DbConnection.userDao.getUsers());
        orders.addAll(DbConnection.orderDao.getOrdersByManagerId(DbConnection.loggedInUser.getId()));
        packages.addAll(DbConnection.packageDao.getPackages());
        services.addAll(DbConnection.serviceDao.getServices());
        tasks.addAll(DbConnection.taskDao.getTasks());


        oId.setText("Nr");
        oName.setText("Name");
        oDesc.setText("Bezeichnung");
        oClient.setText("Kunde");
        oManager.setText("Manager");
        oPackage.setText("Paket");
        oDate.setText("Erstellt_am");

        pId.setText("Nr");
        pName.setText("Name");
        pDesc.setText("Bezeichnung");
        pPrice.setText("Preis");
        pDiscPrice.setText("Discount Preis");


        tId.setText("Nr");
        tName.setText("Name");
        tDesc.setText("Bezeichnung");
        tService.setText("Dienst");
        tDate.setText("Erstellt_am");


        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showOrdersDetailedInfo(newValue);
        });

        packageTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showPackagesDetailedInfo(newValue);
        });

        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showTasksDetailedInfo(newValue);
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

    private void showTasksDetailedInfo(Task t){
        tId.setText(""+t.getTaskId());
        tName.setText(t.getName());
        tDesc.setText(t.getDescription());
        tService.setText(DbConnection.serviceDao.getServiceById(t.getService()).toString());
        tDate.setText(t.getCreatedDate().toString());

    }


    private void showClientsOrders(User user){
        if (user != null){
            //setText.setText(user.getId().toString())
            orderTable.setItems(DbConnection.orderDao.getOrdersByClientId(user.getUserId()));

        }
        else {
            orderTable.setItems(null);
        }
    }

    @FXML
    private void handleCreateUser() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/CreateUserDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Benutzer erstellen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));
            UserController controller = loader.getController();
            controller.setMain(this.main);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/EditUserDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Benutzer bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));
            UserController controller = loader.getController();
            controller.setMain(this.main, selectedUser);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteUser() {
        int selectedUserIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedUserIndex >= 0) {
            User userInUserTv = userTable.getItems().get(selectedUserIndex);
            DbConnection.userDao.deleteUserById(userInUserTv.getUserId());
            users.remove(userInUserTv);
            userTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Benutzer aus.");
        }
    }

    @FXML
    private void handleCreateOrder() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/CreateOrderDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bestellung erstellen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));
            OrderController controller = loader.getController();
            controller.setMain(this.main);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/EditOrderDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bestellung bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));

            OrderController controller = loader.getController();
            controller.setMain(this.main, selectedOrder);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }

    }


    @FXML
    private void handleDeleteOrder() {
        int selectedOrderIndex = orderTable.getSelectionModel().getSelectedIndex();
        if (selectedOrderIndex >= 0) {
            Order orderInOrderTv = orderTable.getItems().get(selectedOrderIndex);
            DbConnection.orderDao.deleteOrderById(orderInOrderTv.getOrderId());
            orders.remove(orderInOrderTv);
            orderTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle eine Bestellung aus.");
        }
    }



    @FXML
    private void handleCreatePaket() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/CreatePackageDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Paket erstellen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));
            PackageController controller = loader.getController();
            controller.setMain(this.main);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditPaket() {
        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/EditPackageDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Paket bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));

            PackageController controller = loader.getController();
            controller.setMain(this.main, selectedPackage);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }

    }


    @FXML
    private void handleDeletePaket() {
        int selectedPackageIndex = packageTable.getSelectionModel().getSelectedIndex();
        if (selectedPackageIndex >= 0) {
            Package packageInPackageTv = packageTable.getItems().get(selectedPackageIndex);
            DbConnection.packageDao.deletePackageById(packageInPackageTv.getPackageId());
            packages.remove(packageInPackageTv);
            packageTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle ein Paket aus.");
        }
    }


    @FXML
    private void handleCreateService() {
//        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
//        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        try {
//            if (selectedService == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/CreateServiceDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Dienst erstellen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));
            ServiceController controller = loader.getController();
            controller.setMain(this.main);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
//            }

        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditService() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
//        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/EditServiceDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Dienst bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));

            ServiceController controller = loader.getController();
            controller.setMain(this.main, selectedService);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }

    }


    @FXML
    private void handleDeleteService() {
        int selectedServiceIndex = serviceTable.getSelectionModel().getSelectedIndex();
//        int selectedPackageIndex = packageTable.getSelectionModel().getSelectedIndex();
        if (selectedServiceIndex >= 0) {
            Service serviceInPackageTv = serviceTable.getItems().get(selectedServiceIndex);
            DbConnection.serviceDao.deleteServiceById(serviceInPackageTv.getServiceId());
            services.remove(serviceInPackageTv);
            serviceTable.getSelectionModel().clearSelection();
//            Package packageInPackageTv = packageTable.getItems().get(selectedPackageIndex);
//            packageDao.deletePackageById(packageInPackageTv.getPackageId());
//            packages.remove(packageInPackageTv);
//            packageTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Dienst aus.");
        }
    }


    @FXML
    private void handleCreateTask() {
//        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        try {
//            if (selectedTask == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/CreateTaskDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Aufgabe erstellen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));
            TaskController controller = loader.getController();
            controller.setMain(this.main);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
//            }

        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/EditTaskDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Aufgabe bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));

            TaskController controller = loader.getController();
            controller.setMain(this.main, selectedTask);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }

    }


    @FXML
    private void handleDeleteTask() {
        int selectedTaskIndex = taskTable.getSelectionModel().getSelectedIndex();
        if (selectedTaskIndex >= 0) {
            Task taskInTaskTv = taskTable.getItems().get(selectedTaskIndex);
            DbConnection.taskDao.deleteTaskById(taskInTaskTv.getTaskId());
            tasks.remove(taskInTaskTv);
            taskTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle eine Aufgabe aus.");
        }
    }

    @FXML
    public void handleSearchUser() {
        try {

//            OperatingEnvironment operatingEnvironment = (OperatingEnvironment) operatingEnvironmentToggleGroup.getSelectedToggle().getUserData();
//            int requiredDistance = Integer.parseInt(minDistanceRequiredField.getText());
//            String uname = searchusername.getText();
//            userTable.setItems(DbConnection.userDao.getUsersByName(uname));
//            ObservableList<VehicleBindingAdapter> matchedVehiclesBindingAdapters = FXCollections.observableArrayList();
//            vehicleManagement.findMatchingVehicles(requiredDistance, operatingEnvironment).forEach(vehicle ->
//                    matchedVehiclesBindingAdapters.add(vehicle.getVehicleBindingAdapter()));
//            vehicleTable.setItems(matchedVehiclesBindingAdapters);
        } catch (NullPointerException e) {
            alert("Nicht gefunden");
            e.printStackTrace();
        }

    }


    public static ObservableList<Order> getOrders() {
        return orders;
    }

    public static ObservableList<User> getUsers() {
        return users;
    }

    public static ObservableList<Package> getPackages() {
        return packages;
    }

    public static ObservableList<Service> getServices() {
        return services;
    }

    public static ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setMain(Main main) {
        this.main = main;

        userTable.setItems(users);
        orderTable.setItems(orders);
        packageTable.setItems(packages);
        serviceTable.setItems(services);
        taskTable.setItems(tasks);

//        userTable.setItems(DbConnection.userDao.getUsers());
//        orderTable.setItems(DbConnection.orderDao.getOrders());
//        packageTable.setItems(DbConnection.packageDao.getPackages());
//        serviceTable.setItems(DbConnection.serviceDao.getServices());
//        taskTable.setItems(DbConnection.taskDao.getTasks());
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

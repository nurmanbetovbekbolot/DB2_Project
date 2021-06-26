package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.Package;
import dbproject.dto.*;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class MainController {

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

    private Stage primaryStage;

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

    @FXML
    private Button logoutButton;

    private Main main;

    private static final ObservableList<User> users = FXCollections.observableArrayList();
    private static final ObservableList<Order> orders = FXCollections.observableArrayList();
    private static final ObservableList<Package> packages = FXCollections.observableArrayList();
    private static final ObservableList<Service> services = FXCollections.observableArrayList();
    private static final ObservableList<Task> tasks = FXCollections.observableArrayList();


    public MainController() {
    }

    @FXML
    protected void initialize() {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        vorNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        nachNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

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
        orders.addAll(DbConnection.orderDao.getOrders());
        packages.addAll(DbConnection.packageDao.getPackages());
        services.addAll(DbConnection.serviceDao.getServices());
        tasks.addAll(DbConnection.taskDao.getTasks());
//        addButtonToTable();


        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                showUsersDetailedInfo(newValue);
        });
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                showOrdersDetailedInfo(newValue);
        });

        packageTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                showPackagesDetailedInfo(newValue);
        });

        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                showTasksDetailedInfo(newValue);
        });
    }

    private void showUsersDetailedInfo(User u) {
        uId.setText("Nr: " + u.getUserId());
        uFname.setText("Vorname: " + u.getFirstName());
        uLname.setText("Name: " + u.getLastName());
        uLogin.setText("Login: " + u.getUserName());
        uRole.setText("Role: " + u.getRole());
        uPass.setText("Pass: " + u.getPassword());
        uDate.setText("Erstellt_am: " + u.getCreatedDate().toString());

    }

    private void showOrdersDetailedInfo(Order o) {
        oId.setText("Nr: " + o.getOrderId());
        oName.setText("Name: " + o.getName());
        oDesc.setText("Bezeichnung: " + o.getDescription());
        oClient.setText("Kunde: " + DbConnection.userDao.getUserById(o.getClientId()).toString());
        oManager.setText("Manager " + DbConnection.userDao.getUserById(o.getManagerId()).toString());
        oPackage.setText("Paket: " + DbConnection.packageDao.getPackageById(o.getPaketId()).getName());
        oDate.setText("Erstellt_am: " + o.getCreatedDate().toString());

    }

    private void showPackagesDetailedInfo(Package p) {
        pId.setText("Nr: " + p.getPackageId());
        pName.setText("Name: " + p.getName());
        pDesc.setText("Bezeichnung: " + p.getDescription());
        pPrice.setText("Preis: " + String.valueOf(p.getPrice()));
        pDiscPrice.setText("Rabatt: " + String.valueOf(p.getDiscountPrice()));

    }

    private void showTasksDetailedInfo(Task t) {
        tId.setText("Nr: " + t.getTaskId());
        tName.setText("Name: " + t.getName());
        tDesc.setText("Bezeichnung: " + t.getDescription());
        tService.setText("Dienst: " + DbConnection.serviceDao.getServiceById(t.getService()).toString());
        tDate.setText("Erstellt_am: " + t.getCreatedDate().toString());

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
    private void handleToPackage() {
        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/Package.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle(selectedPackage.getName());
                dialogStage.initOwner(this.main.getPrimaryStage());
                dialogStage.setScene(new Scene(page));
                dialogStage.setWidth(800);
                PackageOverviewController controller = loader.getController();
                controller.setSelectedPackage(selectedPackage);
                controller.setMain(this.main);
                controller.setStage(dialogStage);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert("Es ist keine Bestellung ausgewählt! Bitte wählen Sie zuerst Bestellung aus.");
        }
    }

    @FXML
    private void handleCreateService() {
        try {
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

        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditService() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
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
        if (selectedServiceIndex >= 0) {
            Service serviceInPackageTv = serviceTable.getItems().get(selectedServiceIndex);
            DbConnection.taskDao.deleteTaskByServiceId(serviceInPackageTv.getServiceId());
            DbConnection.serviceDao.deleteServiceFromPackage(serviceInPackageTv.getServiceId());
            DbConnection.serviceDao.deleteServiceById(serviceInPackageTv.getServiceId());
            int delId = serviceInPackageTv.getServiceId();
            services.remove(serviceInPackageTv);
            tasks.removeIf(task -> task.getService() == delId);
            serviceTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Dienst aus.");
        }
    }


    @FXML
    private void handleCreateTask() {
        try {
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
            int deletedU = userInUserTv.getUserId();
            users.remove(userInUserTv);
            orders.removeIf(order ->
                    order.getClientId() == deletedU
            );
            orders.removeIf(order ->
                    order.getManagerId() == deletedU
            );
            userTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Benutzer aus.");
        }
    }

    private void addButtonToTable() {
        TableColumn<Service, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Service, Void>, TableCell<Service, Void>> cellFactory = new Callback<TableColumn<Service, Void>, TableCell<Service, Void>>() {
            @Override
            public TableCell<Service, Void> call(final TableColumn<Service, Void> param) {
                final TableCell<Service, Void> cell = new TableCell<Service, Void>() {

                    private final Button btn = new Button("Hinzufügen");

                    {
                        btn.setOnAction((ActionEvent event) -> {
//                            DbConnection.serviceDao.createService(new Service(serviceNameTextField.getText())
//                            DbConnection.serviceDao.
//                            Service service = getTableView().getItems().get(getIndex());
//                            System.out.println("selectedData: " + service);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        serviceTable.getColumns().add(colBtn);

    }

    public static ObservableList<User> getUsers() {
        return users;
    }

    public static ObservableList<Order> getOrders() {
        return orders;
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


    @FXML
    private void handleToOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../view/Order.fxml"));
                AnchorPane page = loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle(selectedOrder.getName());
                dialogStage.initOwner(this.main.getPrimaryStage());
                dialogStage.setScene(new Scene(page));
                OrderController controller = loader.getController();
                controller.setSelectedOrder(selectedOrder);
                controller.setMain(this.main);
                controller.setStage(dialogStage);
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            alert("Es ist keine Bestellung ausgewählt! Bitte wählen Sie zuerst Bestellung aus.");
        }
    }

    @FXML
    private void handleExportUser() {
        DbConnection.userDao.getAllUsersXML();
    }

    @FXML
    private void handleExportOrder() {
        DbConnection.orderDao.getAllOrdersXML();
    }

    @FXML
    private void handleExportPackage() {
        DbConnection.packageDao.getAllPackagesXML();
    }

    @FXML
    private void handleExportService() {
        DbConnection.serviceDao.getAllServicesXML();
    }

    @FXML
    private void handleExportTask() {
        DbConnection.taskDao.getAllTasksXML();
    }


    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
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
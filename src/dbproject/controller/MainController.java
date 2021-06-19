package dbproject.controller;

import dbproject.Main;
import dbproject.dao.*;
import dbproject.dto.*;
import dbproject.dto.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class MainController {
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> vorNameColumn;

    @FXML
    private TableColumn<User, String> nachNameColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, Date> createdAtColumn;

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
    private TableView<Package> packageTable;

    @FXML
    private TableColumn<Package, String> packageNameColumn;

    @FXML
    private TableColumn<Package, String> descriptionColumn;

    @FXML
    private TableColumn<Package, Double> preisColumn;

    @FXML
    private TableColumn<Package, Double> discountPreisColumn;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, String> serviceNameColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    private TableColumn<Task, Integer> taskServiceColumn;

    @FXML
    private TableColumn<Task, Date> taskCreatedAtColumn;


    private Main main;
    private UserDao userDao;
    private PackageDao packageDao;
    private ServiceDao serviceDao;
    private TaskDao taskDao;
    private OrderDao orderDao;


    private static final ObservableList<User> users = FXCollections.observableArrayList();
    private static final ObservableList<Order> orders = FXCollections.observableArrayList();
    private static final ObservableList<Package> packages = FXCollections.observableArrayList();
    private static final ObservableList<Service> services = FXCollections.observableArrayList();
    private static final ObservableList<Task> tasks = FXCollections.observableArrayList();


    public MainController() {
        this.userDao = new UserDao();
        this.packageDao = new PackageDao();
        this.serviceDao = new ServiceDao();
        this.taskDao = new TaskDao();
        this.orderDao = new OrderDao();
    }

    @FXML
    protected void initialize() {
        vorNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        nachNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        orderNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        orderClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        orderManagerColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        orderPackageColumn.setCellValueFactory(new PropertyValueFactory<>("paketId"));
        orderCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        preisColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountPreisColumn.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));

        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        users.addAll(userDao.getUsers());
        orders.addAll(orderDao.getOrders());
        packages.addAll(packageDao.getPackages());
        services.addAll(serviceDao.getServices());
        tasks.addAll(taskDao.getTasks());
        packageTable.setRowFactory(packageTableView -> {

            final TableRow<Package> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && index < packageTableView.getItems().size() && packageTableView.getSelectionModel().isSelected(index)) {
                    packageTable.getSelectionModel().clearSelection();
                    event.consume();
                }
            });
            return row;
        });

    }


    //    private void showClientsOrders(User user){
//        if (user != null){
//            smsHandyTableView.setItems(main.getSmsHandyData().filtered(smsHandy -> smsHandy.getProvider().getName().equals(provider.getName())));
//        }
//        else {
//            smsHandyTableView.setItems(null);
//        }
//    }


    @FXML
    private void handleCreateOrder() {
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        try {
            if (selectedOrder == null) {
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
            }

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
            orderDao.deleteOrderById(orderInOrderTv.getOrderId());
            orders.remove(orderInOrderTv);
            orderTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle eine Bestellung aus.");
        }
    }



    @FXML
    private void handleCreatePaket() {
        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        try {
            if (selectedPackage == null) {
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
            }

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
            packageDao.deletePackageById(packageInPackageTv.getPackageId());
            packages.remove(packageInPackageTv);
            packageTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle ein Paket aus.");
        }
    }


    @FXML
    private void handleCreateService() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
//        Package selectedPackage = packageTable.getSelectionModel().getSelectedItem();
        try {
            if (selectedService == null) {
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
            }

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
            serviceDao.deleteServiceById(serviceInPackageTv.getServiceId());
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
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        try {
            if (selectedTask == null) {
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
            }

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
            taskDao.deleteTaskById(taskInTaskTv.getTaskId());
            tasks.remove(taskInTaskTv);
            taskTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle eine Aufgabe aus.");
        }
    }


    @FXML
    private void handleCreateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        try {
            if (selectedUser == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../view/CreateUserDialog.fxml"));
                AnchorPane page = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("User erstellen");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(this.main.getPrimaryStage());
                dialogStage.setScene(new Scene(page));
                UserController controller = loader.getController();
                controller.setMain(this.main);
                controller.setStage(dialogStage);
                dialogStage.showAndWait();
            }

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
            dialogStage.setTitle("User bearbeiten");
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
            userDao.deleteUserById(userInUserTv.getUserId());
            users.remove(userInUserTv);
            userTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Benutzer aus.");
        }
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
    }

//    public static void infoBox(String msg, String text, String title) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText(msg);
//        alert.setTitle(title);
//        alert.setHeaderText(text);
//        alert.showAndWait();
//    }

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
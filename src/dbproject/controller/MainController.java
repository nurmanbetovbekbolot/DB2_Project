package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.dao.UserDao;
import dbproject.dto.Package;
import dbproject.dto.User;
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
    private Button createPaketButton;

    @FXML
    private Button editPaketButton;



    private Main main;
    private UserDao userDao;
    private PackageDao packageDao;


    private static final ObservableList<User> users = FXCollections.observableArrayList();
    private static final ObservableList<Package> packages = FXCollections.observableArrayList();
    public MainController() {
        this.userDao = new UserDao();
        this.packageDao = new PackageDao();
    }

    @FXML
    protected void initialize() {
        vorNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        nachNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        preisColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountPreisColumn.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));

        users.addAll(userDao.getUsers());
        packages.addAll(packageDao.getPackages());


        packageTable.setRowFactory(packageTableView -> {

            final TableRow<Package> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && index < packageTableView.getItems().size() && packageTableView.getSelectionModel().isSelected(index)  ) {
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
    private void handleDeletePaket(){
        int selectedPackageIndex = packageTable.getSelectionModel().getSelectedIndex();
        if (selectedPackageIndex>=0){
            Package packageInPackageTv = packageTable.getItems().get(selectedPackageIndex);
           packageDao.deletePackageById(packageInPackageTv.getPackageId());
           packages.remove(packageInPackageTv);
            packageTable.getSelectionModel().clearSelection();
        }
        else{
            alert("Bitte wählen Sie in der Tabelle ein Paket aus.");
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
    private void handleDeleteUser(){
        int selectedUserIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedUserIndex>=0){
            User userInUserTv = userTable.getItems().get(selectedUserIndex);
            userDao.deleteUserById(userInUserTv.getUserId());
            users.remove(userInUserTv);
            userTable.getSelectionModel().clearSelection();
        }
        else{
            alert("Bitte wählen Sie in der Tabelle einen User aus.");
        }
    }

    public static ObservableList<User> getUsers() {
        return users;
    }

    public static ObservableList<Package> getPackages() {
        return packages;
    }

    public void setMain(Main main) {
        this.main = main;
        userTable.setItems(users);
        packageTable.setItems(packages);
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
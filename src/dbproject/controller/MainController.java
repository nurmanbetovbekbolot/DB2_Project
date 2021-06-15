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

//    private final ObservableList<User> users = FXCollections.observableArrayList();
//    private final ObservableList<Package> packages = FXCollections.observableArrayList();

    private Main main;
    private UserDao userDao;
    private PackageDao packageDao;

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

    }


//    private void showClientsOrders(User user){
//        if (user != null){
//            smsHandyTableView.setItems(main.getSmsHandyData().filtered(smsHandy -> smsHandy.getProvider().getName().equals(provider.getName())));
//        }
//        else {
//            smsHandyTableView.setItems(null);
//        }
//    }


    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new Package.
     */
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
                CreatePackageDialogController controller = loader.getController();
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

            EditPackageDialogController controller = loader.getController();
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

            packageTable.getSelectionModel().clearSelection();
        }
        else{
            alert("Bitte wählen Sie in der Tabelle ein Paket aus.");
        }
    }


    public void setMain(Main main) {
        this.main = main;
        userTable.setItems(userDao.getUsers());
        packageTable.setItems(packageDao.getPackages());
    }

    public static void infoBox(String msg, String text, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
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
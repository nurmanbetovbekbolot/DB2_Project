package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import dbproject.dto.Service;
import dbproject.dto.Task;
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

public class PackageOverviewController {
    private Main main;
    private Stage stage;
    //    private PackageDao packageDao;
    private Package selectedPackage;
    private Package editedPackage;


    @FXML
    private TextField packageNameTextField;

    @FXML
    private TextArea packageDescriptionTextField;

    @FXML
    private TextField packagePriceTextField;

    @FXML
    private TextField packageDiscountPriceTextField;

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
    private Label forPinfo;

    @FXML
    private Label forInfo;

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
    private TextField taskTextField;

//    @FXML
//    private Label tId;
//
//    @FXML
//    private Label tName;
//
//    @FXML
//    private Label tDesc;
//
//    @FXML
//    private Label tService;
//
//    @FXML
//    private Label tDate;


    @FXML
    private void initialize() {
        forInfo.setText("Info");
//
//        pId.setText("Nr");
//        pName.setText("Name");
//        pDesc.setText("Bezeichnung");
//        pPrice.setText("Preis");
//        pDiscPrice.setText("Discount Preis");

        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
//        taskServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        serviceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            forInfo.setText("Nr: "+newValue.getServiceId()+" | Name: "+newValue.getName());
            showServicesTasks(newValue);
        });
    }


    @FXML
    private void handleCreatePackageButton() {
//        String message = checkPackageData();
//        if (!message.equals("")) {
//            alert(message);
//            return;
//        }
//        Package p = DbConnection.packageDao.createPackage(new Package(packageNameTextField.getText(), packageDescriptionTextField.getText(), Double.parseDouble(packagePriceTextField.getText()), Double.parseDouble(packageDiscountPriceTextField.getText())));
//
//        MainController.getPackages().add(DbConnection.packageDao.getPackageByName(packageNameTextField.getText()));
//
//        alert("Paket erfolgreich angelegt!");
//        stage.close();
    }


    @FXML
    private void handleEditPackageButton() {
//        String message = checkPackageData();
//        if (message.equals("")) {
//            Package p = new Package(editedPackage.getPackageId(),packageNameTextField.getText(),packageDescriptionTextField.getText(),Double.parseDouble(packagePriceTextField.getText()),Double.parseDouble(packageDiscountPriceTextField.getText()));
//            DbConnection.packageDao.updatePackage(p);
//            MainController.getPackages().remove(editedPackage);
//            MainController.getPackages().add(p);
//            alert("Paket erfolgreich bearbeitet!");
//            stage.close();
//        } else {
//            alert(message);
//        }
    }

    @FXML
    private void handleCreateService() {
        String message = checkServiceData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        String  addingToService= DbConnection.serviceDao.addServiceToPackage1(serviceTextField.getText());
        Service fromDb = DbConnection.serviceDao.getServiceByName(serviceTextField.getText());

        DbConnection.serviceDao.addServiceToPackage2(selectedPackage.getPackageId(),fromDb.getServiceId());
        System.out.println(taskTextField.getText());
        alert("Dienst erfolgreich angelegt!");
        serviceTable.getItems().add(fromDb);
//        DbConnection.serviceDao.getServicesByPackageId(selectedPackage.getPackageId());

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
            DbConnection.serviceDao.deleteServiceById(serviceInPackageTv.getServiceId());
            MainController.getServices().remove(serviceInPackageTv);
            serviceTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Dienst aus.");
        }
    }

    @FXML
    private void handleCancelButton() {
        stage.close();
    }

    private void alert(String text) {
        Alert alert = new Alert(
                Alert.AlertType.NONE,
                text,
                ButtonType.CLOSE
        );
        alert.showAndWait();
    }

    private String checkServiceData() {
        String message = "";
        if (serviceTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        for (Service s : DbConnection.serviceDao.getServices()) {
            if (s.getName().equals(serviceTextField.getText())) return "Dieser datei ist besetzt";
        }
        return message;
    }

    public void setSelectedPackage(Package p) {
        this.selectedPackage = p;
        forPinfo.setText("PaketNr: "+p.getPackageId()+" | Name: "+p.getName()+" | Bezeichnung: "+p.getDescription()+" | Preis: "+p.getPrice()+" | Discount: "+p.getDiscountPrice());

    }

    public void setMain(Main main) {
        this.main = main;
        serviceTable.setItems(DbConnection.serviceDao.getServicesByPackageId(selectedPackage.getPackageId()));
    }



    public void setMain(Main main, Package p) {
        this.main = main;
        this.editedPackage = p;
        packageNameTextField.setText(p.getName());
        packageDescriptionTextField.setText(p.getDescription());
        packagePriceTextField.setText("" + p.getPrice());
        packageDiscountPriceTextField.setText("" + p.getDiscountPrice());
    }

//    private void updateItems(Service s){
//        serviceTable.getItems().add(s);
//    }
//
    private void showServicesTasks(Service service){
        if (service != null){
            taskTable.setItems(DbConnection.taskDao.getTasksByServiceId(service.getServiceId()));
//            forInfo.setText("");

        }
        else {
            serviceTable.setItems(null);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}


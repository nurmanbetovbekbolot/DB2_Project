package dbproject.controller;

import dbproject.Main;
import dbproject.dao.ServiceDao;
import dbproject.db.DbConnection;
import dbproject.dto.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServiceController {
    private Main main;
    private Stage stage;
//    private ServiceDao serviceDao;
    private Service editedService;
//    private Package editedPackage;


    @FXML
    private TextField serviceNameTextField;


    @FXML
    private void initialize() {
    }

//    public ServiceController() {
//        this.serviceDao = new ServiceDao();
//    }

//    public ServiceController(String url) {
//        this.serviceDao = new ServiceDao(url);
//    }
    @FXML
    private void handleCreateServiceButton() {
        String message = checkServiceData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
//        Package p = packageDao.createPackage(new Package(packageNameTextField.getText(), packageDescriptionTextField.getText(), Double.parseDouble(packagePriceTextField.getText()), Double.parseDouble(packageDiscountPriceTextField.getText())));
        Service s = DbConnection.serviceDao.createService(new Service(serviceNameTextField.getText()));
        MainController.getServices().add(s);

        alert("Dienst erfolgreich angelegt!");
        stage.close();
    }


    @FXML
    private void handleEditServiceButton() {
        String message = checkServiceData();
        if (message.equals("")) {
//            Package p = new Package(editedPackage.getPackageId(),packageNameTextField.getText(),packageDescriptionTextField.getText(),Double.parseDouble(packagePriceTextField.getText()),Double.parseDouble(packageDiscountPriceTextField.getText()));
//            packageDao.updatePackage(p);
            Service s = new Service(editedService.getServiceId(),serviceNameTextField.getText());
            DbConnection.serviceDao.updateService(s);
            MainController.getServices().remove(editedService);
            MainController.getServices().add(s);
            alert("Dienst erfolgreich bearbeitet!");
            stage.close();
        } else {
            alert(message);
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
        if (serviceNameTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        for (Service s : DbConnection.serviceDao.getServices()) {
            if (s.getName().equals(serviceNameTextField.getText())) return "Dieser datei ist besetzt";
        }
        return message;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setMain(Main main, Service s) {
        this.main = main;
        this.editedService = s;
        serviceNameTextField.setText(s.getName());

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}


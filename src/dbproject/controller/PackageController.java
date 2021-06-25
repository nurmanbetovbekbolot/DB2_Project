package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.Package;
import dbproject.dto.Service;
import dbproject.dto.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Date;

public class PackageController {
    private Main main;
    private Stage stage;
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
    private void handleCreatePackageButton() {
        String message = checkPackageData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        Package p = DbConnection.packageDao.createPackage(new Package(packageNameTextField.getText(), packageDescriptionTextField.getText(), Double.parseDouble(packagePriceTextField.getText()), Double.parseDouble(packageDiscountPriceTextField.getText())));

        MainController.getPackages().add(DbConnection.packageDao.getPackageByName(packageNameTextField.getText()));

        alert("Paket erfolgreich angelegt!");
        stage.close();
    }


    @FXML
    private void handleEditPackageButton() {
        String message = checkPackageData();
        if (message.equals("")) {
            Package p = new Package(editedPackage.getPackageId(),packageNameTextField.getText(),packageDescriptionTextField.getText(),Double.parseDouble(packagePriceTextField.getText()),Double.parseDouble(packageDiscountPriceTextField.getText()));
            DbConnection.packageDao.updatePackage(p);
            MainController.getPackages().remove(editedPackage);
            MainController.getPackages().add(p);
            alert("Paket erfolgreich bearbeitet!");
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

    private String checkPackageData() {
        String message = "";
        if (packageNameTextField.getText().isBlank() || packageDescriptionTextField.getText().isBlank() || packagePriceTextField.getText().isBlank() || packageDiscountPriceTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        if (!packagePriceTextField.getText().isBlank() && !packageDiscountPriceTextField.getText().isBlank()) {
            try {
                Double.parseDouble(packagePriceTextField.getText());
                Double.parseDouble(packageDiscountPriceTextField.getText());
            } catch (NumberFormatException e) {
                return "Zahl sollte vom Typ Nummer sein!";
            }
        }
        return message;
    }

    public void setSelectedPackage(Package p) {
        this.selectedPackage = p;
    }

    public void setMain(Main main) {
        this.main = main;
    }



    public void setMain(Main main, Package p) {
        this.main = main;
        this.editedPackage = p;
        packageNameTextField.setText(p.getName());
        packageDescriptionTextField.setText(p.getDescription());
        packagePriceTextField.setText("" + p.getPrice());
        packageDiscountPriceTextField.setText("" + p.getDiscountPrice());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}


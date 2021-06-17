package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.dto.Package;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PackageController {
    private Main main;
    private Stage stage;
    private PackageDao packageDao;
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
    private void initialize() {
    }

    public PackageController() {
        this.packageDao = new PackageDao();
    }

    @FXML
    private void handleCreatePackageButton() {
        String message = checkPackageData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        Package p = packageDao.createPackage(new Package(packageNameTextField.getText(), packageDescriptionTextField.getText(), Double.parseDouble(packagePriceTextField.getText()), Double.parseDouble(packageDiscountPriceTextField.getText())));

        MainController.getPackages().add(p);

        alert("Paket erfolgreich angelegt!");
        stage.close();
    }


    @FXML
    private void handleEditPackageButton() {
        String message = checkPackageData();
        if (message.equals("")) {
            Package p = new Package(editedPackage.getPackageId(),packageNameTextField.getText(),packageDescriptionTextField.getText(),Double.parseDouble(packagePriceTextField.getText()),Double.parseDouble(packageDiscountPriceTextField.getText()));
            packageDao.updatePackage(p);
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
        for (Package pe : packageDao.getPackages()) {
            if (pe.getName().equals(packageNameTextField.getText())) return "Dieser datei ist besetzt";
        }
        return message;
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


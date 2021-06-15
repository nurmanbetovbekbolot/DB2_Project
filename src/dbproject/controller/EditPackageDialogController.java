package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.dto.Package;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EditPackageDialogController {
    private Main main;
    private Stage stage;
    private Package editedPackage;
    private PackageDao packageDao;

    @FXML
    private TextField packageNameTextField;

    @FXML
    private TextField packageDescriptionTextField;

    @FXML
    private TextField packagePriceTextField;

    @FXML
    private TextField packageDiscountPriceTextField;


    public EditPackageDialogController() {
        this.packageDao = new PackageDao();
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleEditPackageButton() {
        String message = checkPackageData();
        if (message.equals("")) {
            packageDao.updatePackage(editedPackage);
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

    /**
     * zeigt Fehler- oder Erfolgsfenster an
     *
     * @param text- oder Erfolmeldung
     */
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
        if (!packagePriceTextField.getText().isBlank() && !packageDiscountPriceTextField.getText().isBlank())
        {
            try {
                Double.parseDouble(packagePriceTextField.getText());
                Double.parseDouble(packageDiscountPriceTextField.getText());
            } catch (NumberFormatException e) {
                return  "Zahl sollte vom Typ Double sein!";
            }
        }
        for (Package pe : packageDao.getPackages()) {
            if (pe.getName().equals(packageNameTextField.getText())) return "Dieser datei ist besetzt";
        }
        return message;
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

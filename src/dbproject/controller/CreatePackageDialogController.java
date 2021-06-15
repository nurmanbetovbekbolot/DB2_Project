package dbproject.controller;

import com.sun.jdi.InvocationException;
import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.dto.Package;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Random;

public class CreatePackageDialogController {
    private Main main;
    private Stage stage;
    private PackageDao packageDao;

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

    public CreatePackageDialogController() {
        this.packageDao = new PackageDao();
    }

    @FXML
    private void handleCreatePackageButton() {
        String message = checkPackageData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        packageDao.createPackage(new Package(packageNameTextField.getText(), packageDescriptionTextField.getText(), Double.parseDouble(packagePriceTextField.getText()), Double.parseDouble(packageDiscountPriceTextField.getText())));
        alert("Paket erfolgreich angelegt!");
        stage.close();
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
                return "Zahl sollte vom Typ Double sein!";
            }
        }
        for (Package pe : packageDao.getPackages()) {
            if (pe.getName().equals(packageNameTextField.getText())) return "Dieser datei ist besetzt";
        }
        return message;
    }

    public void setMain(Main main) {
        this.main = main;
        packageDao.getPackages();
//        smsHandyNumberTextField.setText(generateSmsHandyNumber());
//
//        smsHandyTypeChoiceBox.getItems().add(SmsHandyArt.PREPAID);
//        smsHandyTypeChoiceBox.getItems().add(SmsHandyArt.TARIFF_PLAN);
//        smsHandyTypeChoiceBox.setValue(smsHandyTypeChoiceBox.getItems().get(0));
//
//        main.getProvidersData().forEach(provider -> smsHandyProviderChoiceBox.getItems().add(provider));
//        smsHandyProviderChoiceBox.setValue(smsHandyProviderChoiceBox.getItems().get(0));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private enum SmsHandyArt {
        PREPAID, TARIFF_PLAN
    }
}

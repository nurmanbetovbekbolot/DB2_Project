package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import dbproject.dto.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class OrderController {
    private Main main;
    private Stage stage;
    private Order editedOrder;
    private Order selectedOrder;


    @FXML
    private TextField orderNameTextField;

    @FXML
    private TextArea orderDescriptionTextField;

    @FXML
    private ChoiceBox<User> orderClientChoiceBox;

    @FXML
    private ChoiceBox<User> orderManagerChoiceBox;

    @FXML
    private ChoiceBox<Package> orderPackageChoiceBox;

    @FXML
    private void initialize() {
        orderClientChoiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getFirstName();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        orderManagerChoiceBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getFirstName();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        orderPackageChoiceBox.setConverter(new StringConverter<Package>() {
            @Override
            public String toString(Package object) {
                return object.getName();
            }

            @Override
            public Package fromString(String string) {
                return null;
            }
        });
    }


    @FXML
    private void handleCreateOrderButton() {
        String message = checkOrderData();
        User client = orderClientChoiceBox.getValue();
        Package p = orderPackageChoiceBox.getValue();
        User manager = orderManagerChoiceBox.getValue();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        try {
            Order o = DbConnection.orderDao.createOrder(new Order(orderNameTextField.getText(), orderDescriptionTextField.getText(), orderClientChoiceBox.getValue().getUserId(), orderManagerChoiceBox.getValue().getUserId(), orderPackageChoiceBox.getValue().getPackageId()));
            MainController.getOrders().add(DbConnection.orderDao.getOrderByName(orderNameTextField.getText()));
            alert("Bestellung erfolgreich angelegt!");
            stage.close();


        } catch (Exception e) {
            alert(e.getMessage());
        }

    }


    @FXML
    private void handleEditOrderButton() {
        String message = checkOrderData();
        User client = orderClientChoiceBox.getValue();
        User manager = orderManagerChoiceBox.getValue();
        Package p = orderPackageChoiceBox.getValue();
        if (message.equals("")) {
            Order o = new Order(editedOrder.getOrderId(), orderNameTextField.getText(), orderDescriptionTextField.getText(), client.getUserId(), manager.getUserId(), p.getPackageId());
            DbConnection.orderDao.updateOrder(o);
            MainController.getOrders().remove(editedOrder);
            MainController.getOrders().add(DbConnection.orderDao.getOrderById(editedOrder.getOrderId()));
            alert("Bestellung erfolgreich bearbeitet!");
            stage.close();
        } else {
            alert(message);
        }
    }

    @FXML
    private void handleMoreInfoButton() {
        String message = checkOrderData();
        User client = orderClientChoiceBox.getValue();
        User manager = orderManagerChoiceBox.getValue();
        Package p = orderPackageChoiceBox.getValue();
        if (message.equals("")) {
            Order o = new Order(editedOrder.getOrderId(), orderNameTextField.getText(), orderDescriptionTextField.getText(), client.getUserId(), manager.getUserId(), p.getPackageId());
            DbConnection.orderDao.updateOrder(o);
            MainController.getOrders().remove(editedOrder);
            MainController.getOrders().add(DbConnection.orderDao.getOrderById(editedOrder.getOrderId()));
            alert("Bestellung erfolgreich bearbeitet!");
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

    private String checkOrderData() {
        String message = "";
        if (orderNameTextField.getText().isBlank() || orderDescriptionTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        return message;
    }

    public void setMain(Main main) {
        this.main = main;

        MainController.getUsers().forEach(user -> {
            if (user.getRole().equals("CLIENT")) {
                orderClientChoiceBox.getItems().add(user);
            }
            else if(user.getRole().equals("MANAGER"))
            {
                orderManagerChoiceBox.getItems().add(user);
            }});
        orderClientChoiceBox.setValue(orderClientChoiceBox.getItems().get(0));
        orderManagerChoiceBox.setValue(orderManagerChoiceBox.getItems().get(0));

        MainController.getPackages().forEach(p -> orderPackageChoiceBox.getItems().add(p));
        orderPackageChoiceBox.setValue(orderPackageChoiceBox.getItems().get(0));

    }

    public void setMain(Main main, Order o) {
        this.main = main;
        this.editedOrder = o;
        orderNameTextField.setText(o.getName());
        orderDescriptionTextField.setText(o.getDescription());
        MainController.getUsers().forEach(user -> orderClientChoiceBox.getItems().add(user));
        orderClientChoiceBox.setValue(orderClientChoiceBox.getItems().get(0));

        MainController.getUsers().forEach(manager -> orderManagerChoiceBox.getItems().add(manager));
        orderManagerChoiceBox.setValue(orderManagerChoiceBox.getItems().get(0));

        MainController.getPackages().forEach(p -> orderPackageChoiceBox.getItems().add(p));
        orderPackageChoiceBox.setValue(orderPackageChoiceBox.getItems().get(0));

    }

    public void setSelectedOrder(Order o) {
        this.selectedOrder = o;
//        numberLabel.setText(selectedSmsHandy.getNumber()+" | "+selectedSmsHandy.getClass().getSimpleName()+" | "+selectedSmsHandy.getProvider().getName());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}

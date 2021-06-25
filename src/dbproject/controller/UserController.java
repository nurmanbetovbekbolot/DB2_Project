package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import dbproject.dto.User;
import dbproject.model.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class UserController {

    private Main main;
    private Stage stage;
    private User editedUser;
//    private final UserDao userDao;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ChoiceBox<Role> userRoleChoiceBox;


    @FXML
    private void initialize() {
        userRoleChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Role object) {
                return object == Role.MANAGER ? "MANAGER" : "CLIENT";
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    private void handleCreateUserButton() {
        String message = checkUserData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        User u = DbConnection.userDao.createUser(new User(firstNameTextField.getText(), lastNameTextField.getText(), loginTextField.getText(), passwordTextField.getText()), userRoleChoiceBox.getValue());
        u.setCreatedDate(new Date());
      MainController.getUsers().add(DbConnection.userDao.getUserByLogin(loginTextField.getText()));
        alert("Benutzer erfolgreich angelegt!");
        stage.close();
    }

    @FXML
    private void handleEditUserButton() throws ParseException {
        String message = checkUserData();
        User u;
        if (message.equals("")) {
                u = new User(editedUser.getUserId(), firstNameTextField.getText(), lastNameTextField.getText(), loginTextField.getText(), passwordTextField.getText());
            DbConnection.userDao.updateUser(u, userRoleChoiceBox.getValue());
            u.setCreatedDate(new Date());
            MainController.getUsers().remove(editedUser);
            MainController.getUsers().add(DbConnection.userDao.getUserById(editedUser.getUserId()));
            alert("Benutzer erfolgreich bearbeitet!");
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

    private String checkUserData() {
        String message = "";
        if (firstNameTextField.getText().isBlank() || lastNameTextField.getText().isBlank() || loginTextField.getText().isBlank() || passwordTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
//        for (User user : userDao.getUsers()) {
//            if (user.getUserName().equals(loginTextField.getText())) return "Dieser datei ist besetzt";
//        }
        return message;
    }


    public void setMain(Main main) {
        this.main = main;
        userRoleChoiceBox.getItems().add(Role.CLIENT);
        userRoleChoiceBox.getItems().add(Role.MANAGER);
        userRoleChoiceBox.setValue(userRoleChoiceBox.getItems().get(0));

    }

    public void setMain(Main main, User u) {
        this.main = main;
        this.editedUser = u;
        firstNameTextField.setText(u.getFirstName());
        lastNameTextField.setText(u.getLastName());
        loginTextField.setText(u.getUserName());
        passwordTextField.setText(u.getPassword());
        userRoleChoiceBox.getItems().add(Role.CLIENT);
        userRoleChoiceBox.getItems().add(Role.MANAGER);
        userRoleChoiceBox.setValue(userRoleChoiceBox.getItems().get(0));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}

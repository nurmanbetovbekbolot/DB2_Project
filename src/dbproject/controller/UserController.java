package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.dao.UserDao;
import dbproject.dto.Package;
import dbproject.dto.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Date;

public class UserController {

    private Main main;
    private Stage stage;
    private User editedUser;
    private final UserDao userDao;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;


    public UserController() {
        userDao = new UserDao();
    }

    @FXML
    private void handleCreateUserButton() {
        String message = checkUserData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        User u = userDao.createUser(new User(firstNameTextField.getText(),lastNameTextField.getText(),loginTextField.getText(),passwordTextField.getText()));
        MainController.getUsers().add(u);
        alert("User erfolgreich angelegt!");
        stage.close();
    }

    @FXML
    private void handleEditUserButton() {
        String message = checkUserData();
        if (message.equals("")) {
            User u = new User(editedUser.getUserId(),firstNameTextField.getText(),lastNameTextField.getText(),loginTextField.getText(),passwordTextField.getText());
            userDao.updateUser(u);
            MainController.getUsers().remove(editedUser);
            MainController.getUsers().add(u);
            alert("User erfolgreich bearbeitet!");
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
    }

    public void setMain(Main main, User u) {
        this.main = main;
        this.editedUser = u;
        firstNameTextField.setText(u.getFirstName());
        lastNameTextField.setText(u.getLastName());
        loginTextField.setText(u.getUserName());
        passwordTextField.setText(u.getPassword());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

package dbproject.controller;

import dbproject.Main;
import dbproject.db.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class LoginController {

    private Main main;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;


    private Connection conn;

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success")) {
                String pathToView;
                try {

                        switch (DbConnection.loggedInUser.getRole()) {
                            case "ADMIN":
                                pathToView = "../view/Main.fxml";

                                break;
                            case "MANAGER":
                                pathToView = "../view/Manager.fxml";
                                break;
                            case "CLIENT":
                                pathToView = "../view/Client.fxml";
                                break;
                            default:
                                pathToView = "../view/Login.fxml";
                        }

                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource(pathToView));
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                    if (DbConnection.loggedInUser.getRole().equals("CLIENT")) {
                        ClientController moc = loader.getController();
                        moc.setMain(main);

                    }
                    else if (DbConnection.loggedInUser.getRole().equals("MANAGER")){
                        ManagerController moc = loader.getController();
                        moc.setMain(main);
                    }
                    else if (DbConnection.loggedInUser.getRole().equals("ADMIN")){
                        MainController moc = loader.getController();
                        moc.setMain(main);
                    }


                } catch (IOException ex) {
                    System.out.println("getu");
                    System.err.println(ex.getMessage());
                }

            }
        }
    }


//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        if (conn == null) {
//            lblErrors.setTextFill(Color.TOMATO);
//            lblErrors.setText("Server Error : Check");
//        } else {
//            lblErrors.setTextFill(Color.GREEN);
//            lblErrors.setText("Server is up : Good to go");
//        }
//    }
//
//    public LoginController() throws SQLException {
//        conn = dbConnection.connect();
//    }

    private String logIn() {
        String status = "Success";
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        if (login.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            try {
                DbConnection.initialize(login, password);
                MainController.getUsers().clear();
                MainController.getOrders().clear();
                MainController.getPackages().clear();
                MainController.getServices().clear();
                MainController.getTasks().clear();
            } catch (SQLException e) {
                setLblError(Color.TOMATO, "Enter correct credentials");
                status = "Error";

            }
        }
        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    public void setMain(Main main) {
        this.main = main;
    }

}

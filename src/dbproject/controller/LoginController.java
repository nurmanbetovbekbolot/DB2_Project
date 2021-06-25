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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController{

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
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

//    public String login = txtUsername.getText();


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
                                pathToView = "../view/Main.fxml";
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
//                        .setTitle("MAKK | " + authUserRole + " | " + emailTextField.getText());

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

    //we gonna use string to check for status
    private String logIn(){
        String status = "Success";
        String login = txtUsername.getText();
        String password = txtPassword.getText();
        if(login.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
//            String sql = "SELECT * FROM benutzer WHERE benutzer_name = ? and password = ?";
            try {
            DbConnection.initialize(login, password);

            }
            catch (SQLException e){
             setLblError(Color.TOMATO,"Enter correct credentials");
                status = "Error";

            }

//            if (conn == null)
//                setLblError(Color.TOMATO, "Enter Correct Email/Password");
//            try (Connection conn = dbConnection.connect() ;
//                 PreparedStatement statement = conn.prepareStatement(sql)) {
//
//            preparedStatement.setString(1, login);
//            preparedStatement.setString(2, password);
//            resultSet = preparedStatement.executeQuery();
//            if (!resultSet.next()) {
//                if () {
//                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
//                    status = "Error";
//                } else {
//                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
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

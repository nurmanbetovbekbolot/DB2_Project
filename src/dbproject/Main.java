package dbproject;

import dbproject.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("CRM Anwendung");

        showMainOverview();
    }

    public void showMainOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/Login.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
            LoginController moc = loader.getController();
            moc.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

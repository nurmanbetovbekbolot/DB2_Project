package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.dao.UserDao;
import dbproject.dto.Package;
import dbproject.dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

/**
 * FXML Controller class
 *
 * @author oXCToo
 */
public class MainController {
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> vorNameColumn;

    @FXML
    private TableColumn<User, String> nachNameColumn;

    @FXML
    private TableColumn<User,String> loginColumn;

    @FXML
    private TableColumn<User,Date> createdAtColumn;

    @FXML
    private TableView<Package> packageTable;

    @FXML
    private TableColumn<Package,String> packageNameColumn;

    @FXML
    private TableColumn<Package,String> descriptionColumn;

    @FXML
    private TableColumn<Package,Double> preisColumn;

    @FXML
    private TableColumn<Package,Double> discountPreisColumn;

    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Package> packages = FXCollections.observableArrayList();

    private Main main;
    private UserDao userDao = new UserDao();
    private PackageDao packageDao = new PackageDao();
    @FXML
    protected void initialize() {
        vorNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        nachNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        users.addAll(userDao.getUsers());
        userTable.setItems(users);

        packageNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        preisColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        discountPreisColumn.setCellValueFactory(new PropertyValueFactory<>("discountprice"));
        packages.addAll(packageDao.getPackages());
        packageTable.setItems(packages);
    }


//    private void showClientsOrders(User user){
//        if (user != null){
//            smsHandyTableView.setItems(main.getSmsHandyData().filtered(smsHandy -> smsHandy.getProvider().getName().equals(provider.getName())));
//        }
//        else {
//            smsHandyTableView.setItems(null);
//        }
//    }


//        login_button.setOnAction(new EventHandler<ActionEvent>() {
//            Boolean loggedIn;
//            public void handle(ActionEvent event) {
//        Parent root;
//                try {
//                    // get ssn from user input
//                    ssn = userInput.getText();
//                    D company = new CompanyDatabase();
//
//                    // check ssn validation
//                    valid = company.isManager(ssn);
//                    if (valid) loggedIn = true;
//                    else loggedIn = false;
//
//                    // showing error message
//                    if(!loggedIn) {
//                        infoBox("Please enter correct SSN", null, "Failed");
//                    }
//                    else {
//        try {
//            root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Stage stage = new Stage();
//        stage.setTitle("Manager View");
//                        stage.setScene(new Scene(root, 800, 600));
//        stage.show();
//    }
//                }
//                catch(Exception e) {
//                    e.printStackTrace();
//                }

//});
//    }

    public void setMain(Main main) {
        this.main = main;
//        users.addAll(userDao.getUsers());
//        userTable.setItems(users);
//        userTable.getItems().addAll(users);
    }

    public static void infoBox(String msg, String text, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}
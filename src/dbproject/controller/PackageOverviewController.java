package dbproject.controller;

import dbproject.Main;
import dbproject.dao.PackageDao;
import dbproject.db.DbConnection;
import dbproject.dto.Order;
import dbproject.dto.Package;
import dbproject.dto.Service;
import dbproject.dto.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class PackageOverviewController {
    private Main main;
    private Stage stage;
    private Package selectedPackage;
    private Package editedPackage;

    @FXML
    private Label forPinfo;

    @FXML
    private Label forInfo;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> serviceIdColumn;


    @FXML
    private TableColumn<Service, String> serviceNameColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Integer> taskIdColumn;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    private TableColumn<Task, Integer> taskServiceColumn;

    @FXML
    private TableColumn<Task, Date> taskCreatedAtColumn;

    @FXML
    private TextField serviceTextField;

    @FXML
    private TextField taskNameTextField;

    @FXML
    private TextField taskDescTextField;

    @FXML
    private void initialize() {
        forInfo.setText("");


        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("taskId"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
//        taskServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        serviceTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            forInfo.setText("Nr: "+(newValue==null?"":newValue.getServiceId())+" | Name: "+(newValue==null?"":newValue.getName()));
            showServicesTasks(newValue);
        });
    }

    @FXML
    private void handleCreateService() {
        String message = checkServiceData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        String  addingToService= DbConnection.serviceDao.addServiceToPackage1(serviceTextField.getText());
        Service fromDb = DbConnection.serviceDao.getServiceByName(serviceTextField.getText());

        DbConnection.serviceDao.addServiceToPackage2(selectedPackage.getPackageId(),fromDb.getServiceId());
        alert("Dienst erfolgreich angelegt!");
        serviceTable.getItems().add(fromDb);

    }

    @FXML
    private void handleEditService() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/EditServiceDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Dienst bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.main.getPrimaryStage());
            dialogStage.setScene(new Scene(page));

            ServiceController controller = loader.getController();
            controller.setMain(this.main, selectedService);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alert("Etwas ist schief gelaufen.");
            e.printStackTrace();
        }

    }

    @FXML
    private void handleDeleteService() {
        int selectedServiceIndex = serviceTable.getSelectionModel().getSelectedIndex();
        if (selectedServiceIndex >= 0) {
            Service serviceInPackageTv = serviceTable.getItems().get(selectedServiceIndex);
            DbConnection.serviceDao.deleteServiceFromPackage(serviceInPackageTv.getServiceId());
            int delId=serviceInPackageTv.getServiceId();
            serviceTable.getItems().remove(serviceInPackageTv);
            taskTable.getItems().removeIf(task -> task.getService()==delId);
//            serviceTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle einen Dienst aus.");
        }
    }

    @FXML
    private void handleCreateTaskButton() {
        String message = checkTaskData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();

        if (selectedService!=null) {
            Task t = DbConnection.taskDao.createTask(new Task(taskNameTextField.getText(), taskDescTextField.getText(), selectedService.getServiceId()));
            taskTable.getItems().add(DbConnection.taskDao.getTaskByName(taskNameTextField.getText()));
            alert("Aufgabe erfolgreich angelegt!");
        }
        else
            alert("Bitte wählen Sie in der Tabelle einen Dienst aus.");
    }


    @FXML
    private void handleDeleteTask() {
        int selectedTaskIndex = taskTable.getSelectionModel().getSelectedIndex();
        if (selectedTaskIndex >= 0) {
            Task taskInTaskTv = taskTable.getItems().get(selectedTaskIndex);
            DbConnection.taskDao.deleteTaskById(taskInTaskTv.getTaskId());
            taskTable.getItems().remove(taskInTaskTv);
            taskTable.getSelectionModel().clearSelection();
        } else {
            alert("Bitte wählen Sie in der Tabelle eine Aufgabe aus.");
        }
    }

    private String checkTaskData() {
        String message = "";
        if (taskNameTextField.getText().isBlank() || taskDescTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        return message;
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

    private String checkServiceData() {
        String message = "";
        if (serviceTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        for (Service s : DbConnection.serviceDao.getServices()) {
            if (s.getName().equals(serviceTextField.getText())) return "Dieser datei ist besetzt";
        }
        return message;
    }

    public void setSelectedPackage(Package p) {
        this.selectedPackage = p;
        forPinfo.setText("PaketNr: "+p.getPackageId()+" | Name: "+p.getName()+" | Bezeichnung: "+p.getDescription()+" | Preis: "+p.getPrice()+" | Discount: "+p.getDiscountPrice());

    }

    public void setMain(Main main) {
        this.main = main;
        serviceTable.setItems(DbConnection.serviceDao.getServicesByPackageId(selectedPackage.getPackageId()));
    }



    public void setMain(Main main, Package p) {
        this.main = main;
        this.editedPackage = p;
    }

//    private void updateItems(Service s){
//        serviceTable.getItems().add(s);
//    }
//
    private void showServicesTasks(Service service){
        if (service != null){
            taskTable.setItems(DbConnection.taskDao.getTasksByServiceId(service.getServiceId()));
        }
        else {
            serviceTable.setItems(null);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}


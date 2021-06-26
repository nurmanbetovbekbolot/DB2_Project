package dbproject.controller;

import dbproject.Main;
import dbproject.dao.TaskDao;
import dbproject.db.DbConnection;
import dbproject.dto.Package;
import dbproject.dto.Service;
import dbproject.dto.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class TaskController {
    private Main main;
    private Stage stage;
//    private TaskDao taskDao;
    private Task editedTask;


    @FXML
    private TextField taskNameTextField;

    @FXML
    private TextArea taskDescriptionTextField;

    @FXML
    private ChoiceBox<Service> taskServiceChoiceBox;

    @FXML
    private void initialize() {
        taskServiceChoiceBox.setConverter(new StringConverter<Service>() {
            @Override
            public String toString(Service object) {
                return object.getName();
            }

            @Override
            public Service fromString(String string) {
                return null;
            }
        });
    }

//    public TaskController() {
//        this.taskDao = new TaskDao();
//    }

//    public TaskController(String url) {
//        this.taskDao = new TaskDao(url);
//    }

    @FXML
    private void handleCreateTaskButton() {
        String message = checkTaskData();
        if (!message.equals("")) {
            alert(message);
            return;
        }
        Task t = DbConnection.taskDao.createTask(new Task(taskNameTextField.getText(), taskDescriptionTextField.getText(), taskServiceChoiceBox.getValue().getServiceId()));
        MainController.getTasks().add(DbConnection.taskDao.getTaskByName(taskNameTextField.getText()));
        alert("Aufgabe erfolgreich angelegt!");
        stage.close();
    }


    @FXML
    private void handleEditTaskButton() {
        String message = checkTaskData();
        if (message.equals("")) {
            Task t = new Task(editedTask.getTaskId(), taskNameTextField.getText(), taskDescriptionTextField.getText(), taskServiceChoiceBox.getValue().getServiceId());
            DbConnection.taskDao.updateTask(t);
            MainController.getTasks().remove(editedTask);
            MainController.getTasks().add(t);
            alert("Aufgabe erfolgreich bearbeitet!");
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

    private String checkTaskData() {
        String message = "";
        if (taskNameTextField.getText().isBlank() || taskDescriptionTextField.getText().isBlank())
            return "Feld kann nicht leer sein!";
        return message;
    }

    public void setMain(Main main) {
        this.main = main;
        MainController.getServices().forEach(tsk -> taskServiceChoiceBox.getItems().add(tsk));
        taskServiceChoiceBox.setValue(taskServiceChoiceBox.getItems().get(0));
    }

    public void setMain(Main main, Task t) {
        this.main = main;
        this.editedTask = t;
        taskNameTextField.setText(t.getName());
        taskDescriptionTextField.setText(t.getDescription());

        MainController.getServices().forEach(tsk -> taskServiceChoiceBox.getItems().add(tsk));
        taskServiceChoiceBox.setValue(taskServiceChoiceBox.getItems().get(0));
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
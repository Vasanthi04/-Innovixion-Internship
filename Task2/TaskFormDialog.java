package com.vasanthi.taskscheduler;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class TaskFormDialog extends Dialog<Pair<String, String>> {

    private TextField taskNameField;

    public TaskFormDialog() {
        setTitle("Add Task");
        setHeaderText("Enter Task Name:");

        // Set up the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        taskNameField = new TextField();
        grid.add(new Label("Task Name:"), 0, 0);
        grid.add(taskNameField, 1, 0);

        getDialogPane().setContent(grid);

        // Set the button types (OK and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Convert the result to a pair when OK button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(taskNameField.getText(), ""); // Modify as per your task details
            }
            return null;
        });
    }

    public String getTaskName() {
        return taskNameField.getText();
    }
}

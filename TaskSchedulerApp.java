package com.vasanthi.taskscheduler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class TaskSchedulerApp extends Application {

    private TaskManager taskManager;
    private NotificationService notificationService;
    private TaskListView taskListView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Task Scheduler");

        // Initialize TaskManager and NotificationService
        taskManager = new TaskManager();
        notificationService = new NotificationService();

        // Load tasks from the database
        taskManager.loadTasksFromDatabase();

        // Layout setup
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // Top: Title Label
        Label titleLabel = new Label("Task Scheduler");
        root.setTop(titleLabel);

        // Left: Add Task Button
        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> {
            TaskFormDialog dialog = new TaskFormDialog();
            dialog.showAndWait().ifPresent(result -> {
                String taskName = result.getKey();
                String taskDescription = result.getValue();
                // Create a new task with the provided values
                Task newTask = new Task(taskName, taskDescription, LocalDateTime.now().plusDays(1), 2); // Default priority to Medium
                taskManager.addTask(newTask);
                taskListView.addTask(newTask); // Update the TaskListView
                System.out.println("Task added: " + newTask.getTitle());
            });
        });
        root.setLeft(addButton);

        // Center: Display Tasks
        List<Task> tasks = taskManager.getTasks();
        taskListView = new TaskListView(tasks);
        root.setCenter(taskListView);

        // Right: Notifications Button
        Button notifyButton = new Button("Notify Due Tasks");
        notifyButton.setOnAction(e -> {
            notificationService.sendNotifications(); // Send notifications for due tasks
            System.out.println("Notifications sent for due tasks.");
        });
        root.setRight(notifyButton);

        // Set Scene and Show Stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

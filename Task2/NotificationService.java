package com.vasanthi.taskscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TaskScheduler";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "VasaNthi24&";

    // Retrieve tasks from the database that are due
    public List<Task> getDueTasks() {
        List<Task> dueTasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE due_date <= ? AND is_completed = false";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, LocalDateTime.now());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Task task = new Task(rs.getString("title"), rs.getString("description"),
                            rs.getObject("due_date", LocalDateTime.class), rs.getInt("priority"));
                    task.setId(rs.getInt("id"));
                    task.setCompleted(rs.getBoolean("is_completed"));
                    dueTasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dueTasks;
    }

    // Send notification for due tasks
    public void sendNotifications() {
        List<Task> dueTasks = getDueTasks();
        for (Task task : dueTasks) {
            System.out.println("Task '" + task.getTitle() + "' is due!");
        }
    }
}

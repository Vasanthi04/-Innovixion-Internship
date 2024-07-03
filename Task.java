package com.vasanthi.taskscheduler;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private int priority; // 1 for High, 2 for Medium, 3 for Low
    private boolean isCompleted;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/TaskScheduler";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "VasaNthi24&";

    // Constructor
    public Task(String title, String description, LocalDateTime dueDate, int priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }

    // Getters and setters for id and isCompleted
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    // Other getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // Save task to the database
    public void save() {
        String query = "INSERT INTO tasks (title, description, due_date, priority, is_completed) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setObject(3, dueDate);
            stmt.setInt(4, priority);
            stmt.setBoolean(5, isCompleted);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update task in the database
    public void update() {
        String query = "UPDATE tasks SET title = ?, description = ?, due_date = ?, priority = ?, is_completed = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setObject(3, dueDate);
            stmt.setInt(4, priority);
            stmt.setBoolean(5, isCompleted);
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete task from the database
    public void delete() {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve tasks from the database
    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Task task = new Task(rs.getString("title"), rs.getString("description"), rs.getObject("due_date", LocalDateTime.class), rs.getInt("priority"));
                task.setId(rs.getInt("id"));
                task.setCompleted(rs.getBoolean("is_completed"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public String toString() {
        return "Task [title=" + title + ", description=" + description + ", dueDate=" + dueDate + ", priority="
                + priority + ", isCompleted=" + isCompleted + "]";
    }

	
	
}

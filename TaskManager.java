package com.vasanthi.taskscheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.save(); // Save the task to the database
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> getDueTasks() {
        List<Task> dueTasks = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Task task : tasks) {
            if (task.getDueDate().isBefore(now) || task.getDueDate().isEqual(now))
            {
                dueTasks.add(task);
            }
        }
        return dueTasks;
    }

    // Load tasks from the database
    public void loadTasksFromDatabase() {
        tasks = Task.getAllTasks();
    }
}

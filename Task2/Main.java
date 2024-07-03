package com.vasanthi.taskscheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        NotificationService notificationService = new NotificationService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                System.out.println("Enter title:");
                String title = scanner.nextLine();
                System.out.println("Enter description:");
                String description = scanner.nextLine();
                System.out.println("Enter due date (YYYY-MM-DDTHH:MM):");
                String dueDateStr = scanner.nextLine();
                LocalDateTime dueDate = LocalDateTime.parse(dueDateStr);
                System.out.println("Enter priority (1-High, 2-Medium, 3-Low):");
                int priority = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                Task task = new Task(title, description, dueDate, priority);
                taskManager.addTask(task);
            } else if (choice == 2) {
                for (Task task : taskManager.getTasks()) {
                    System.out.println(task);
                }
                notificationService.sendNotifications();
            } else if (choice == 3) {
                break;
            }
        }

        scanner.close();
    }
}

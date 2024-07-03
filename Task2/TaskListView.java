package com.vasanthi.taskscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaskListView extends VBox {
    private ListView<Task> listView;
    private ObservableList<Task> taskObservableList;

    public TaskListView(List<Task> tasks) {
        taskObservableList = FXCollections.observableArrayList(tasks);
        listView = new ListView<>(taskObservableList);
        this.getChildren().add(listView);
    }

    public void addTask(Task task) {
        taskObservableList.add(task);
    }

    public void removeTask(Task task) {
        taskObservableList.remove(task);
    }

    public ListView<Task> getListView() {
        return listView;
    }
}

package com.example.mytaskmanager.repository;

import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface IRepository extends Serializable {

    List<Task> getTasks();
    Task getSingleTask(UUID taskId);
    void insertTask(Task task);
    void updateTask(Task task);
    void removeSingleTask(UUID taskId);
    void removeTasks();
    List<Task> getTasksList(State state);
    void addTaskToDo(Task task);
    void addTaskDone(Task task);
    void addTaskDoing(Task task);
}

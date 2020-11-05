package com.example.mytaskmanager.repository;

import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends Serializable {

    List<Task> getTasks();
    Task getSingleTask(UUID taskId);
    List<Task> getUserTasks(String userName);
    void insertTask(Task task);
    void updateTask(Task task);
    void removeSingleTask(Task task);
    void removeTasks();
    void removeTasksUser(String userName);
    List<Task> getTasksList(State state, String userName);
    void addTaskToDo(Task task);
    void addTaskDone(Task task);
    void addTaskDoing(Task task);
    File getPhotoFile(Task task);
    Integer getCount(String userName);


}

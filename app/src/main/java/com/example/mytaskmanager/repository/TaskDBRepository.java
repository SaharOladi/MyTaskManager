package com.example.mytaskmanager.repository;

import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository {


    private static TaskDBRepository sInstance;

    public static TaskDBRepository getInstance() {
        if (sInstance == null)
            sInstance = new TaskDBRepository();

        return sInstance;
    }


    @Override
    public List<Task> getTasks() {
        return null;
    }

    @Override
    public Task getSingleTask(UUID taskId) {
        return null;
    }

    @Override
    public void insertTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void removeSingleTask(UUID taskId) {

    }

    @Override
    public List<Task> getTasksList(State state) {
        return null;
    }

    @Override
    public void removeTasks() {

    }

    @Override
    public void addTaskToDo(Task task) {

    }

    @Override
    public void addTaskDone(Task task) {

    }

    @Override
    public void addTaskDoing(Task task) {

    }
}

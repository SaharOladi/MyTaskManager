package com.example.mytaskmanager.repository;

import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements IRepository {

    private static TaskRepository sInstance;

    public static TaskRepository getInstance() {
        if (sInstance == null)
            sInstance = new TaskRepository();

        return sInstance;
    }

    private List<Task> mTaskToDo;
    private List<Task> mTaskDone;
    private List<Task> mTaskDoing;
    private List<Task> mTasks;

    private TaskRepository() {
        mTasks = new ArrayList<>();
        mTaskToDo = new ArrayList<>();
        mTaskDone = new ArrayList<>();
        mTaskDoing = new ArrayList<>();

    }

    public void insertTask(Task task) {
        if (task.getTaskState().equals(State.DOING))
            mTaskDoing.add(task);
        if (task.getTaskState().equals(State.TODO))
            mTaskToDo.add(task);
        if (task.getTaskState().equals(State.DONE))
            mTaskDone.add(task);
        mTasks.add(task);
    }

    public void addTaskToDo(Task task) {
        if (!mTaskToDo.contains(task)) {
            task.setTaskState(State.TODO);
            mTaskToDo.add(task);
        }
        mTasks.add(task);
    }

    public void addTaskDone(Task task) {
        if (!mTaskDone.contains(task)) {
            task.setTaskState(State.DONE);
            mTaskDone.add(task);
        }
        mTasks.add(task);
    }

    public void addTaskDoing(Task task) {
        if (!mTaskDoing.contains(task)) {
            task.setTaskState(State.DOING);
            mTaskDoing.add(task);
        }
        mTasks.add(task);
    }



    public void removeSingleTask(UUID taskId) {
        for (Task task:mTaskToDo) {
            if (task.getTaskID().equals(taskId)) {
                mTaskToDo.remove(task);
                return;
            }
        }
        for (Task task:mTaskDone) {
            if (task.getTaskID().equals(taskId)) {
                mTaskDone.remove(task);
                return;
            }
        }
        for (Task task:mTaskDoing) {
            if (task.getTaskID().equals(taskId)) {
                mTaskDoing.remove(task);
                return;
            }
        }
        for (Task task:mTasks) {
            if (task.getTaskID().equals(taskId)) {
                mTasks.remove(task);
                return;
            }
        }

    }

    public void removeTasks(){
        mTaskDoing.clear();
        mTaskDone.clear();
        mTaskToDo.clear();
        mTasks.clear();
    }

    public Task getSingleTask(UUID taskId) {
        for (Task task:mTaskDone) {
            if (task.getTaskID().equals(taskId))
                return task;
        }
        for (Task task:mTaskDoing) {
            if (task.getTaskID().equals(taskId))
                return task;
        }
        for (Task task:mTaskToDo) {
            if (task.getTaskID().equals(taskId))
                return task;
        }
        for (Task task:mTasks) {
            if (task.getTaskID().equals(taskId))
                return task;
        }
        return null;
    }

    public List<Task> getTasksList(State state) {
        if (state.equals(State.TODO))
            return mTaskToDo;
        else if (state.equals(State.DONE))
            return mTaskDone;
        else
            return mTaskDoing;
    }

    public void updateTask(Task task) {
        Task findTask = getSingleTask(task.getTaskID());
        if (findTask == null)
            return;
        findTask.setTaskTitle(task.getTaskTitle());
        findTask.setTaskDescription(task.getTaskDescription());
        findTask.setTaskDate(task.getTaskDate());
        findTask.setTaskState(task.getTaskState());

        insertTask(findTask);
        removeSingleTask(task.getTaskID());
    }

    public List<Task> getTasks(){
        return mTasks;
    }
}

package com.example.mytaskmanager.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.mytaskmanager.database.TaskDAO;
import com.example.mytaskmanager.database.TaskDataBase;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements ITaskRepository {


    private static TaskDBRepository sInstance;
    private TaskDAO mTaskDao;
    private Context mContext;

    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context);

        return sInstance;
    }

    public TaskDBRepository(Context context) {

        mContext = context.getApplicationContext();
        TaskDataBase taskDataBase = Room.databaseBuilder(mContext,
                TaskDataBase.class,
                "task.db")
                .allowMainThreadQueries()
                .build();
        mTaskDao = taskDataBase.getTaskDataBaseDAO();

    }


    @Override
    public List<Task> getTasks() {
        return mTaskDao.getTasks();
    }

    @Override
    public Task getSingleTask(UUID taskId) {
        return mTaskDao.getTask(taskId);
    }


    @Override
    public void insertTask(Task task) {
        mTaskDao.insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        mTaskDao.updateTask(task);
    }

    @Override
    public void removeSingleTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    @Override
    public List<Task> getTasksList(State state) {
        return mTaskDao.getTaskState(state);
    }

    @Override
    public void removeTasks() {
      mTaskDao.deleteAllTask();
    }


    @Override
    public void addTaskToDo(Task task) {}

    @Override
    public void addTaskDone(Task task) {}

    @Override
    public void addTaskDoing(Task task) {}

}

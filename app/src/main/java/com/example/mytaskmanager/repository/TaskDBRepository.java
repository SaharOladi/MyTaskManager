package com.example.mytaskmanager.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.mytaskmanager.database.TaskDAO;
import com.example.mytaskmanager.database.TaskDataBase;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.io.File;
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
    public List<Task> getUserTasks(String userName) {
        return mTaskDao.getUserTasks(userName);
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
    public List<Task> getTasksList(State state, String userName) {
        return mTaskDao.getTaskState(state,userName);
    }

    @Override
    public void removeTasks() {
        mTaskDao.deleteAllTask();
    }

    @Override
    public void removeTasksUser(String userName) {
        mTaskDao.removeAllTask(userName);
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

    @Override
    public File getPhotoFile(Task task) {
        // /data/data/com.example.mytaskmanager/files/
        File photoFile = null;
        File filesDir = mContext.getFilesDir();
        // /data/data/com.example.mytaskmanager/files/IMG_ktui4u544nmkfuy48485.jpg
        if (task != null && task.getPhotoFileName() != null)
            photoFile = new File(filesDir, task.getPhotoFileName());
        return photoFile;
    }

    @Override
    public Integer getCount(String userName) {
        return mTaskDao.getTaskCount(userName);
    }

}

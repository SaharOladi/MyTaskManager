package com.example.mytaskmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDAO {
    @Update
    void updateTask(Task task);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM taskTable")
    void deleteAllTask();

    @Insert
    void insertTasks(Task... task);

    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE task_state=:state")
    List<Task> getTaskState(State state);

    @Query("SELECT * FROM taskTable WHERE uuid=:inputId")
    Task getTask(UUID inputId);


}

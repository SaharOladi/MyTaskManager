package com.example.mytaskmanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mytaskmanager.model.Task;

@Database(entities = Task.class, version = 1)
@TypeConverters({Converters.class})
public abstract class TaskDataBase extends RoomDatabase {
    public abstract TaskDAO getTaskDataBaseDAO();
}

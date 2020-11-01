package com.example.mytaskmanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mytaskmanager.model.User;

@Database(entities = User.class, version = 1)
@TypeConverters({Converters.class})
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDAO getUserDataBaseDAO();
}

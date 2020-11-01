package com.example.mytaskmanager.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytaskmanager.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDAO {
    @Update
    void updateUser(User user);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Insert
    void insertUsers(User... user);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE uuid=:inputId")
    User getUser(UUID inputId);

    @Query("SELECT * FROM userTable WHERE user_name=:userName")
    User getUserName(String userName);
}

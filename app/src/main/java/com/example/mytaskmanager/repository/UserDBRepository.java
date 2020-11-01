package com.example.mytaskmanager.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.mytaskmanager.database.UserDAO;
import com.example.mytaskmanager.database.UserDataBase;
import com.example.mytaskmanager.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements Serializable {

    private static UserDBRepository sInstance;

    private UserDAO mUserDAO;
    private Context mContext;


    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);

        return sInstance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        UserDataBase userDataBase = Room.databaseBuilder(mContext,
                UserDataBase.class,
                "user.db")
                .allowMainThreadQueries()
                .build();
        mUserDAO = userDataBase.getUserDataBaseDAO();
    }

    public List<User> getUsers() {
        return mUserDAO.getUsers();
    }

    public User getUser(UUID userId) {
        return mUserDAO.getUser(userId);
    }

    public User getUser(String userName) {
        return mUserDAO.getUserName(userName);
    }


    public void insertUser(User user) {
        mUserDAO.insertUser(user);
    }


}

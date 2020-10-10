package com.example.mytaskmanager.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private UUID mId;
    private String mUserName;
    private String mPassword;

    public User(UUID id,String userName, String password) {
        mId = id;
        mUserName = userName;
        mPassword = password;
    }

    public User(String userName, String password) {
        mId = UUID.randomUUID();
        mUserName = userName;
        mPassword = password;
    }

    public User(UUID id) {
        mId = id;
    }

    public User() {
        this(UUID.randomUUID());
    }

    //Getter & Setters
    public UUID getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}

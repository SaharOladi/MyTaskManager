package com.example.mytaskmanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable {

    private UUID   mUserID;
    private String mUserName;
    private String mUserPassword;
    private Date   mUserLoginDate;

    public User() {
        mUserID = UUID.randomUUID();
        mUserLoginDate = new Date();
    }

    public User(String userName, String userPassword) {
        mUserName = userName;
        mUserPassword = userPassword;
    }

    public UUID getUserID() {
        return mUserID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String userPassword) {
        mUserPassword = userPassword;
    }

    public Date getUserLoginDate() {
        return mUserLoginDate;
    }

    public void setUserLoginDate(Date userLoginDate) {
        mUserLoginDate = userLoginDate;
    }
}

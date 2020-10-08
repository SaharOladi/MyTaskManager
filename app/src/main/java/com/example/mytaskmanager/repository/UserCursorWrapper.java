package com.example.mytaskmanager.repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.mytaskmanager.database.TaskDBSchema;
import com.example.mytaskmanager.model.User;

import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){

        UUID uuidString = UUID.fromString(getString(getColumnIndex(TaskDBSchema.UserTable.Cols.UUID)));
        String userName = getString(getColumnIndex(TaskDBSchema.UserTable.Cols.USERNAME));
        String userPassword = getString(getColumnIndex(TaskDBSchema.UserTable.Cols.PASSWORD));
        Date loginDate = new Date(getLong(getColumnIndex(TaskDBSchema.UserTable.Cols.DATE)));

        return new User(uuidString, userName, userPassword, loginDate);
    }
}
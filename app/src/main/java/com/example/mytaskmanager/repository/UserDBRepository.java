package com.example.mytaskmanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mytaskmanager.database.TaskDBHelper;
import com.example.mytaskmanager.database.TaskDBSchema;
import com.example.mytaskmanager.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements Serializable {

    private static UserDBRepository sInstance;

    private SQLiteDatabase mDatabase;
    private Context mContext;


    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);

        return sInstance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskDBHelper userDBHelper = new TaskDBHelper(mContext);

        //all 4 checks happens on getDataBase
        mDatabase = userDBHelper.getWritableDatabase();
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        UserCursorWrapper userCursorWrapper = queryUserCursor(null, null);

        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return users;

        try {
            userCursorWrapper.moveToFirst();

            while (!userCursorWrapper.isAfterLast()) {
                User user = userCursorWrapper.getUser();
                users.add(user);

                userCursorWrapper.moveToNext();
            }
        } finally {
            userCursorWrapper.close();
        }

        return users;
    }

    public User getUser(UUID userId) {
        String where = TaskDBSchema.UserTable.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{userId.toString()};

        UserCursorWrapper userCursorWrapper = queryUserCursor(where, whereArgs);

        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return null;

        try {
            userCursorWrapper.moveToFirst();
            User user = userCursorWrapper.getUser();

            return user;
        } finally {
            userCursorWrapper.close();
        }
    }

    public User getUser(String  userName) {
        String where = TaskDBSchema.UserTable.Cols.USERNAME + " = ?";
        String[] whereArgs = new String[]{userName.toString()};

        UserCursorWrapper userCursorWrapper = queryUserCursor(where, whereArgs);

        if (userCursorWrapper == null || userCursorWrapper.getCount() == 0)
            return null;

        try {
            userCursorWrapper.moveToFirst();
            User user = userCursorWrapper.getUser();

            return user;
        } finally {
            userCursorWrapper.close();
        }
    }

    public Boolean searchUser(User user) {
        List<User> users = getUsers();
        String username = user.getUserName();
        String password = user.getPassword();
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getUserName().equals(username) &&
                    users.get(i).getPassword().equals(password)){
                return true;
            }
        }

        return false;
    }

    private UserCursorWrapper queryUserCursor(String where, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskDBSchema.UserTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
        return userCursorWrapper;
    }

    public void insertUser(User user) {
        ContentValues values = getContentValues(user);
        mDatabase.insert(TaskDBSchema.UserTable.NAME, null, values);
    }


    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(TaskDBSchema.UserTable.Cols.UUID, user.getId().toString());
        values.put(TaskDBSchema.UserTable.Cols.USERNAME, user.getUserName());
        values.put(TaskDBSchema.UserTable.Cols.PASSWORD, user.getPassword());
        return values;
    }

}

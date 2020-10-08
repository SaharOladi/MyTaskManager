package com.example.mytaskmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(@Nullable Context context) {
        super(context, TaskDBSchema.NAME, null, TaskDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sbQueryTask = new StringBuilder();
        sbQueryTask.append("CREATE TABLE " + TaskDBSchema.TaskTable.NAME + " (");
        sbQueryTask.append(TaskDBSchema.TaskTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQueryTask.append(TaskDBSchema.TaskTable.Cols.UUID + " TEXT NOT NULL,");
        sbQueryTask.append(TaskDBSchema.TaskTable.Cols.TITLE + " TEXT,");
        sbQueryTask.append(TaskDBSchema.TaskTable.Cols.DESCRIPTION + " TEXT,");
        sbQueryTask.append(TaskDBSchema.TaskTable.Cols.STATE + " TEXT,");
        sbQueryTask.append(TaskDBSchema.TaskTable.Cols.DATE + " TEXT");
        sbQueryTask.append(");");
        db.execSQL(sbQueryTask.toString());

        StringBuilder sbQueryUser = new StringBuilder();
        sbQueryUser.append("CREATE TABLE " + TaskDBSchema.UserTable.NAME + " (");
        sbQueryUser.append(TaskDBSchema.UserTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQueryUser.append(TaskDBSchema.UserTable.Cols.UUID + " TEXT NOT NULL,");
        sbQueryUser.append(TaskDBSchema.UserTable.Cols.USERNAME + " TEXT,");
        sbQueryUser.append(TaskDBSchema.UserTable.Cols.PASSWORD + " TEXT,");
        sbQueryUser.append(TaskDBSchema.UserTable.Cols.DATE + " TEXT");
        sbQueryUser.append(");");
        db.execSQL(sbQueryUser.toString());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}

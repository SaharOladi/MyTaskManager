package com.example.mytaskmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mytaskmanager.database.TaskDBSchema.TaskTable.Cols;

import androidx.annotation.Nullable;

public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(@Nullable Context context) {
        super(context, TaskDBSchema.NAME, null, TaskDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("CREATE TABLE " + TaskDBSchema.TaskTable.NAME + " (");
        sbQuery.append(Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery.append(Cols.UUID + " TEXT NOT NULL,");
        sbQuery.append(Cols.TITLE + " TEXT,");
        sbQuery.append(Cols.DESCRIPTION + " TEXT,");
        sbQuery.append(Cols.STATE + " TEXT,");
        sbQuery.append(Cols.DATE + " TEXT");
        sbQuery.append(");");
        db.execSQL(sbQuery.toString());



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}

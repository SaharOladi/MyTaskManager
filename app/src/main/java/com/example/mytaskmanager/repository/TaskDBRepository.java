package com.example.mytaskmanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mytaskmanager.database.TaskDBHelper;
import com.example.mytaskmanager.database.TaskDBSchema;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;
import com.example.mytaskmanager.database.TaskDBSchema.TaskTable.Cols;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository {


    private static TaskDBRepository sInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context);

        return sInstance;
    }

    public TaskDBRepository(Context context) {

        mContext = context.getApplicationContext();
        TaskDBHelper taskDBHelper = new TaskDBHelper(mContext);

        mDatabase = taskDBHelper.getWritableDatabase();

    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                TaskDBSchema.TaskTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null

        );
        if (cursor == null || cursor.getCount() == 0)
            return tasks;

        try {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Task task = extractTaskFromCursor(cursor);
                tasks.add(task);

                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }

        return tasks;
    }

    private Task extractTaskFromCursor(Cursor cursor) {
        UUID uuidString = UUID.fromString(cursor.getString(cursor.getColumnIndex(Cols.UUID)));
        String title = cursor.getString(cursor.getColumnIndex(Cols.TITLE));
        String description = cursor.getString(cursor.getColumnIndex(Cols.DESCRIPTION));
        State state = State.valueOf(cursor.getString(cursor.getColumnIndex(Cols.STATE)));
        Date timeStampDate = new Date(cursor.getLong(cursor.getColumnIndex(Cols.DATE)));

        return new Task(uuidString, title, description, state, timeStampDate);
    }

    @Override
    public Task getSingleTask(UUID taskId) {
        String where = Cols.UUID + " = ?";
        String[] whereArgs = new String[]{taskId.toString()};

        Cursor cursor = mDatabase.query(
                TaskDBSchema.TaskTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        try {
            cursor.moveToFirst();
            Task task = extractTaskFromCursor(cursor);
            return task;
        } finally {
            cursor.close();
        }
    }

    @Override
    public void insertTask(Task task) {
        ContentValues values = getContentValues(task);
        mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID, task.getTaskID().toString());
        values.put(Cols.TITLE, task.getTaskTitle());
        values.put(Cols.DESCRIPTION, task.getTaskDescription());
        values.put(Cols.STATE, task.getTaskState().toString());
        values.put(Cols.DATE, task.getTaskDate().getTime());
        return values;
    }

    @Override
    public void updateTask(Task task) {
        ContentValues values = getContentValues(task);
//        String whereClause = Cols.UUID + " = " + task.getTaskID().toString();
        String whereClause = Cols.UUID + " = ?";
        String[] whereArgs = new String[]{task.getTaskID().toString()};
        mDatabase.update(TaskDBSchema.TaskTable.NAME, values, whereClause, whereArgs);
    }

    @Override
    public void removeSingleTask(UUID taskId) {
        String whereClause = Cols.UUID + " = ?";
        String[] whereArgs = new String[]{taskId.toString()};
        mDatabase.delete(TaskDBSchema.TaskTable.NAME, whereClause, whereArgs);
    }

    @Override
    public List<Task> getTasksList(State state) {
        List<Task> tasks = new ArrayList<>();
        String where = Cols.STATE + " = ?";
        String[] whereArgs = new String[]{state.toString()};

        Cursor cursor = mDatabase.query(
                TaskDBSchema.TaskTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return null;

        try {
            cursor.moveToFirst();
            Task task = extractTaskFromCursor(cursor);
            tasks.add(task);
            return tasks;

        } finally {
            cursor.close();
        }
    }

    @Override
    public void removeTasks() {
        mDatabase.delete(TaskDBSchema.TaskTable.NAME, null, null);
    }

    @Override
    public void addTaskToDo(Task task) {
        if (task.getTaskState() == State.TODO) {
            ContentValues values = getContentValues(task);
            mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
        }
    }

    @Override
    public void addTaskDone(Task task) {
        if (task.getTaskState() == State.DONE) {
            ContentValues values = getContentValues(task);
            mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
        }
    }

    @Override
    public void addTaskDoing(Task task) {
        if (task.getTaskState() == State.DOING) {
            ContentValues values = getContentValues(task);
            mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
        }
    }
}

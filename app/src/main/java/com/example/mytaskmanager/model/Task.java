package com.example.mytaskmanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "taskTable")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @ColumnInfo(name = "uuid")
    private UUID mTaskID;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    @ColumnInfo(name = "task title")
    private String mTaskTitle;

    @ColumnInfo(name = "task description")
    private String mTaskDescription;

    @ColumnInfo(name = "task_state")
    private State mTaskState;

    @ColumnInfo(name = "task_date")
    private Date mTaskDate;


    public void setTaskID(UUID taskID) {
        mTaskID = taskID;
    }

    public Task() {
        mTaskID = UUID.randomUUID();
        mTaskDate = new Date();
    }

    public Task(UUID id, String title, String description, State state, Date date) {
        mTaskID = id;
        mTaskTitle = title;
        mTaskDescription = description;
        mTaskState = state;
        mTaskDate = date;
    }

    public UUID getTaskID() {
        return mTaskID;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        mTaskDescription = taskDescription;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public void setTaskState(State taskState) {
        mTaskState = taskState;
    }

    public Date getTaskDate() {
        return mTaskDate;
    }

    public void setTaskDate(Date taskDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate);

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, monthOfYear, dayOfMonth, mTaskDate.getHours(), mTaskDate.getMinutes(), mTaskDate.getSeconds());
        mTaskDate = calendar.getTime();
    }

    public void setTaskTime(Date taskTime) {
        mTaskDate.setHours(taskTime.getHours());
        mTaskDate.setMinutes(taskTime.getMinutes());
        mTaskDate.setSeconds(taskTime.getSeconds());
    }

    public String getJustDate() {
        return new SimpleDateFormat("dd MMM yyyy").format(mTaskDate);
    }

    public String getJustTime() {
        return new SimpleDateFormat("hh:mm a").format(mTaskDate);
    }

    public String getPhotoFileName() {
        return "IMG_" + getTaskID().toString() + ".jpg";
    }
}

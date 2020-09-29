package com.example.mytaskmanager.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {

    private UUID   mTaskID;
    private String mTaskTitle;
    private String mTaskDescription;
    private State  mTaskState;
    private Date   mTaskDate;
    private SimpleDateFormat mFormat;

    public Task() {
        mTaskID = UUID.randomUUID();
        mTaskDate = new Date();
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
        mTaskDate = taskDate;
    }

    public SimpleDateFormat getFormat() {
        return mFormat;
    }

    public void setFormat(SimpleDateFormat format) {
        mFormat = format;
    }

    public String getJustDate(){
        mFormat = new SimpleDateFormat("dd MMM yyyy");
        return mFormat.format(mTaskDate);
    }

    public String getJustTime(){
        mFormat = new SimpleDateFormat("hh:mm a");
        return mFormat.format(mTaskDate);
    }
}

package com.example.mytaskmanager.database;

import androidx.room.TypeConverter;

import com.example.mytaskmanager.model.State;

import java.util.Date;
import java.util.UUID;

public class Converters {

    @TypeConverter
    public static UUID stringToUUID(String value) {
        return UUID.fromString(value);
    }

    @TypeConverter
    public static String UUIDToString(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static Date LongToDate(Long timeStamp) {
        return new Date(timeStamp);
    }

    @TypeConverter
    public static long DateToLong(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static State StringToState(String stateStr) {

        State temp = State.TODO;

        if (stateStr.equals("TODO"))
            temp = State.TODO;

        if (stateStr.equals("DOING"))
            temp = State.DOING;

        if (stateStr.equals("DONE"))
            temp = State.DONE;

        return temp;

    }

    @TypeConverter
    public static String StateToString(State tempState) {
        String result = "";

        if (tempState == State.DOING)
            result = "DOING";

        if (tempState == State.DONE)
            result = "DONE";

        if (tempState == State.TODO)
            result = "TODO";

        return result;

    }

}

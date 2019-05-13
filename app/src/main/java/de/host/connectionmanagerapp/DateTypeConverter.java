package de.host.connectionmanagerapp;

import androidx.room.TypeConverter;

import java.util.Date;


//https://developer.android.com/training/data-storage/room/referencing-data#java
public class DateTypeConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

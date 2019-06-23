//from https://stackoverflow.com/questions/50313525/room-using-date-field Comment from O95
package de.host.connectionmanagerapp.database;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalenderTypeConverter {
    @TypeConverter
    public static Calendar toCalendar(Long l) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        return c == null ? null : c;
    }

    @TypeConverter
    public static Long fromCalendar(Calendar c){
        return c == null ? null : c.getTime().getTime();
    }
}

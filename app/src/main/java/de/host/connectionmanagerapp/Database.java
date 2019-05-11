package de.host.connectionmanagerapp;

import android.app.Notification;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

//import com.commonsware.cwac.saferoom.SafeHelperFactory;


@androidx.room.Database(entities = {Connection.class,Identity.class,Snippet.class,Job.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    private static Database INSTANCE;
   // static SafeHelperFactory factory= SafeHelperFactory;

    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class,
                                    "database")
                                    //.openHelperFactory(factory)
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

package de.host.connectionmanagerapp;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Connection.class,Identity.class,Snippet.class}, version = 1)
public abstract class Database extends RoomDatabase {


    private static Database INSTANCE;

    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class,
                                    "database").build();
                }
            }
        }
        return INSTANCE;
    }
}

package de.host.connectionmanagerapp;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Connection.class,Identity.class,Snippet.class}, version = 1)
public abstract class Database extends RoomDatabase {


}

package de.host.connectionmanagerapp;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

//import com.commonsware.cwac.saferoom.SafeHelperFactory;


@androidx.room.Database(entities = {Connection.class,Identity.class,Snippet.class,Job.class, Connection_Job.class, Snippet_Job.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class Database extends RoomDatabase {


    private static Database INSTANCE;
   // static SafeHelperFactory factory= SafeHelperFactory;
   public abstract ConnectionDao connectionDao();
   public abstract IdentityDao identityDao();
   public abstract JobDao jobDao();
   public abstract SnippetDao snippetDao();

    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class,
                                    "database")
                                    //.openHelperFactory(factory)
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

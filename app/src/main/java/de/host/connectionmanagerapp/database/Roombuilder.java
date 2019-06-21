//Autor Mattis Uphoff

package de.host.connectionmanagerapp.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

import de.host.connectionmanagerapp.daos.ConnectionDao;
import de.host.connectionmanagerapp.daos.Connection_JobDao;
import de.host.connectionmanagerapp.daos.IdentityDao;
import de.host.connectionmanagerapp.daos.JobDao;
import de.host.connectionmanagerapp.daos.SnippetDao;
import de.host.connectionmanagerapp.daos.Snippet_JobDao;


@androidx.room.Database(entities = {Connection.class, Identity.class, Snippet.class, Job.class, Connection_Job.class, Snippet_Job.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class Roombuilder extends RoomDatabase {


    private static Roombuilder INSTANCE;
    //static String pass = "test";
    //private static SafeHelperFactory factory= new SafeHelperFactory(pass.toCharArray());
   public abstract ConnectionDao connectionDao();
   public abstract IdentityDao identityDao();
   public abstract JobDao jobDao();
   public abstract SnippetDao snippetDao();
   public abstract Connection_JobDao cjDao();
   public abstract Snippet_JobDao sjDao();

    static Roombuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Roombuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    Roombuilder.class,
                                    "database")
                                    //.openHelperFactory(new SafeHelperFactory("passphrase".toCharArray()))
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

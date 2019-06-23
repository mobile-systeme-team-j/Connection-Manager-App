//Autor Mattis Uphoff
// Verbindungen der Datenbank nach https://android.jlelse.eu/android-architecture-components-room-relationships-bf473510c14a
//Aufbau  der Klassen Repostiory Viewadapter Daos Viewmodels nach https://codelabs.developers.google.com/codelabs/android-room-with-a-view
package de.host.connectionmanagerapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

import de.host.connectionmanagerapp.daos.ConnectionDao;
import de.host.connectionmanagerapp.daos.IdentityDao;
import de.host.connectionmanagerapp.daos.JobDao;
import de.host.connectionmanagerapp.daos.SnippetDao;


@androidx.room.Database(entities = {Connection.class, Identity.class, Snippet.class, Job.class}, version = 16, exportSchema = false)
@TypeConverters({DateTypeConverter.class, CalenderTypeConverter.class})
public abstract class Roombuilder extends RoomDatabase {


    private static Roombuilder INSTANCE;
    static String pass = "#j8U!nb4+M2$";
    private static SafeHelperFactory factory= new SafeHelperFactory(pass.toCharArray());
   public abstract ConnectionDao connectionDao();
   public abstract IdentityDao identityDao();
   public abstract JobDao jobDao();
   public abstract SnippetDao snippetDao();

    static Roombuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Roombuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    Roombuilder.class,
                                    "database")
                                    .openHelperFactory(factory)
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

//Autor Mattis Uphoff
package de.host.connectionmanagerapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.host.connectionmanagerapp.database.Identity;
import io.reactivex.Flowable;

@Dao
public interface IdentityDao {
    @Insert
    void insert(Identity identity);

    @Update
    void update(Identity... identities);

    @Query("SELECT * from identity where identity_id=:id")
    Flowable<Identity>identityformid(long id);

    @Query("Select*from identity")
    LiveData<List<Identity>> getAllIdentities();

    @Delete
    void delete (Identity... identities);
}

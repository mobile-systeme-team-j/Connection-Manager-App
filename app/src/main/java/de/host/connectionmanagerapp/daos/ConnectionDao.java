//Autor Mattis Uphoff
package de.host.connectionmanagerapp.daos;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.host.connectionmanagerapp.database.Connection;
import io.reactivex.Flowable;

@Dao
public interface ConnectionDao {

    @Insert
    void insert(Connection connection);
    @Insert
    long insertid(Connection connection);

    @Update
    void update(Connection... connections);

    @Query("Select*from connection Where connection_Id = :id")
    Flowable<Connection> connectionfromid(long id);

    @Query("Select*from connection Where titel = :titel")
    Flowable<Connection> connectionfromtitel(String titel);

    @Query("Select*from connection Order by timestamp DESC Limit 5")
    LiveData<List<Connection>> getRecentConnection();

    @Query("Select*from connection")
    LiveData<List<Connection>> getAllConnection();

    @Delete
    void delete (Connection... connections);



}

//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ConnectionDao {

    @Insert
    void insert(Connection connection);

    @Update
    void update(Connection... connections);

    @Query("Select*from connection Where connection_Id = :id")
    public abstract Connection connectionfromid(long id);

    @Query("Select*from connection Order by timestamp DESC Limit 5")
    LiveData<List<Connection>> getRecentConnection();

    @Query("Select*from connection")
    LiveData<List<Connection>> getAllConnection();

    @Delete
    void delete (Connection... connections);



}

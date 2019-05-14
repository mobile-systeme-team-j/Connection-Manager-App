package de.host.connectionmanagerapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ConnectionDao {

    @Insert
    void insert(Connection connection);

    @Update
    void update(Connection... connections);

    @Query("Select*from connection Where connection_Id = :id")
    public abstract Connection connectionfromid(long id);

    @Delete
    void delete (Connection... connections);



}

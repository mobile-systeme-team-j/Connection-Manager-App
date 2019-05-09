package de.host.connectionmanagerapp;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ConnectionDao {

    @Insert
    void insert(Connection connection);

    @Update
    void update(Connection... connections);

    @Delete
    void delete (Connection... connections);



}

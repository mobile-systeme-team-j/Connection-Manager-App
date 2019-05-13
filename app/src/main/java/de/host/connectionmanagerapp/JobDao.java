package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface JobDao {

    @Insert
    void insert (Job job);

    @Update
    void update (Job... jobs);

    @Delete
    void delete (Job... jobs);

}

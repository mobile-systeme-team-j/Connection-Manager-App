package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface Connection_JobDao {

    @Insert
    void insert(Connection_Job cj);

}

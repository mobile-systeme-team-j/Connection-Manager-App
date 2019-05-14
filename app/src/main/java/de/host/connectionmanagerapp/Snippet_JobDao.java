package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface Snippet_JobDao {
    @Insert
    void insert(Snippet_Job sj);
}

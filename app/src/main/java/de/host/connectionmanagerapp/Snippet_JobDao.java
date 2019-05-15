package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Snippet_JobDao {
    @Insert
    void insert(Snippet_Job sj);

    @Query("Select*from snippet_job_join Where job_Id = :id")
    List<Snippet_Job> sjsfromjob(long id);

    @Delete
    void delete(Snippet_Job... sjs);
}

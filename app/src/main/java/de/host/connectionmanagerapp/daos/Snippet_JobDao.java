//Autor MAttis Uphoff
package de.host.connectionmanagerapp.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import de.host.connectionmanagerapp.database.Snippet_Job;

@Dao
public interface Snippet_JobDao {
    @Insert
    void insert(Snippet_Job sj);

    @Query("Select*from snippet_job_join Where job_Id = :id")
    List<Snippet_Job> sjsfromjob(long id);

    @Query("Select*from snippet_job_join Where snippet_Id = :id")
    List<Snippet_Job> sjsfromsnippet(long id);

    @Delete
    void delete(Snippet_Job... sjs);
}

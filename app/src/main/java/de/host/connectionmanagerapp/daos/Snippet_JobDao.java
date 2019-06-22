//Autor MAttis Uphoff
package de.host.connectionmanagerapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.database.Snippet_Job;
import io.reactivex.Flowable;

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Dao
public interface Snippet_JobDao {
    @Insert
    void insert(Snippet_Job sj);

    @Query("SELECT * FROM snippet INNER JOIN snippet_job_join ON snippet.snippet_Id= snippet_job_join.snippet_Id WHERE snippet_job_join.job_Id=:job_Id")
    LiveData<List<Snippet>> getSnippetForJob(long job_Id);


    @Query("SELECT * FROM job INNER JOIN snippet_job_join ON job.job_Id= snippet_job_join.job_Id WHERE snippet_job_join.snippet_Id=:snippet_Id")
    LiveData<List<Job>> getJobForSnippet(long snippet_Id);
}

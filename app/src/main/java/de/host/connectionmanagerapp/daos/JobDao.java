//Autor Mattis
package de.host.connectionmanagerapp.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.host.connectionmanagerapp.database.Job;
import io.reactivex.Flowable;

@Dao
public interface JobDao {

    @Insert
    long insert (Job job);

    @Update
    void update (Job... jobs);

    @Query("SELECT * from job where job_Id=:id")
    Flowable<Job> jobfromId(int id);

    @Query("SELECT * from job where snippet_titel=:titel")
    Job jobfromsnippetTitel(String titel);

    @Query("SELECT * from job where connection_titel=:titel")
    Job jobfromcoonectionTitel(String titel);

    @Query("Select * from job")
    LiveData<List<Job>> getAllJobs();

    @Delete
    void delete (Job... jobs);

}

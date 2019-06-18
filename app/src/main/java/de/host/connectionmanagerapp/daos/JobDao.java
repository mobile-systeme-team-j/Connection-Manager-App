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

@Dao
public interface JobDao {

    @Insert
    long insert (Job job);

    @Update
    void update (Job... jobs);

    @Query("SELECT * from job where job_Id=:id")
    LiveData<Job> jobfromId(long id);

    @Query("Select * from job")
    LiveData<List<Job>> getAllJobs();

    @Delete
    void delete (Job... jobs);

}
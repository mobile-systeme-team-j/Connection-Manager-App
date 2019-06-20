//Autor Mattis Uphoff
package de.host.connectionmanagerapp.daos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Connection_Job;

@Dao
public interface Connection_JobDao {

    @Insert
    void insert(Connection_Job cj);

    //@Query("SELECT * FROM connection INNER JOIN connection_job_join ON connection.connection_Id= connection_job_join.connection_Id WHERE connection_job_join.job_Id=:job_Id")
    //LiveData<List<Connection>> getConnectionForJob(final int job_Id);


   // @Query("SELECT * FROM job INNER JOIN connection_job_join ON job.job_Id = connection_job_join.job_Id WHERE connection_job_join.connection_Id=:connection_Id")
   // LiveData<List<Connection>> getJobForConnection(final int connection_Id);


}

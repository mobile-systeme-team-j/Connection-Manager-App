//Autor Mattis Uphoff
package de.host.connectionmanagerapp.daos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import java.util.List;

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Connection_Job;
import de.host.connectionmanagerapp.database.Job;
import io.reactivex.Flowable;

@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Dao
public interface Connection_JobDao {

    @Insert
    void insert(Connection_Job cj);

    @Query("SELECT * FROM connection INNER JOIN connection_job_join ON connection.connection_Id= connection_job_join.connection_Id WHERE connection_job_join.job_Id=:job_Id")
    Flowable<Connection> getConnectionForJob(final int job_Id);


   @Query("SELECT * FROM job INNER JOIN connection_job_join ON job.job_Id = connection_job_join.job_Id WHERE connection_job_join.connection_Id=:connection_Id")
   Flowable<Job> getJobForConnection(final int connection_Id);


}

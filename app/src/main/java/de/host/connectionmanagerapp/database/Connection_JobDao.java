//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Connection_JobDao {

    @Insert
    void insert(Connection_Job cj);
    @Query("Select*from connection_job_join Where job_Id = :id")
    List<Connection_Job> cjsjobs(long id);

    @Query("Select*from connection_job_join Where connection_Id = :id")
    List<Connection_Job> cjsconnection(long id);

    @Delete
    void delete(Connection_Job... cj);

}

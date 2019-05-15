package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface JobDao {

    @Insert
    long insert (Job job);

    @Update
    void update (Job... jobs);

    @Query("SELECT * from job where job_Id=:id")
    public abstract Job jobfromId(long id);

    @Delete
    void delete (Job... jobs);

}

package de.host.connectionmanagerapp;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "connection_job_join",
        primaryKeys = { "connection_Id", "job_Id" },
        foreignKeys = {
                @ForeignKey(entity = Connection.class,
                        parentColumns = "connection_Id",
                        childColumns = "connection_Id"),
                @ForeignKey(entity = Job.class,
                        parentColumns = "job_Id",
                        childColumns = "job_Id")
        })
public class Connection_Job {

    private  final int job_Id;
    private  final int connection_Id;


    public Connection_Job(int job_Id, int connection_Id) {
        this.job_Id = job_Id;
        this.connection_Id = connection_Id;
    }

    public int getJob_Id() {
        return job_Id;
    }

    public int getConnection_Id() {
        return connection_Id;
    }
}

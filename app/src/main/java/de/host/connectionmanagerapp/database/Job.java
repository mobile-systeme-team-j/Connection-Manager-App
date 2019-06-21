//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "job")
public class Job {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "job_Id")
    private long job_id;

    @NonNull
    @ColumnInfo(name="titel")
    private String titel;


    @ColumnInfo(name = "job_time")
    private Date jobtime;

    @ColumnInfo(name = "job_date")
    private Date job_date;

    public long getJob_id() {
        return job_id;
    }

    public void setJob_id(long job_id) {
        this.job_id = job_id;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    public Date getJob_date() {
        return job_date;
    }


    public Date getJobtime() {
       return jobtime;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    public void setJobtime(Date jobtime) {
        this.jobtime = jobtime;
    }

    public void setJob_date(Date job_date) {
        this.job_date = job_date;
    }
}

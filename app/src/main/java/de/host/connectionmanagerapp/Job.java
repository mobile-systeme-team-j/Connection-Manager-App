package de.host.connectionmanagerapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "job")
public class Job {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "jobId")
    private int jobid;

    @NonNull
    @ColumnInfo(name="titel")
    private String titel;


    @NonNull
    @ColumnInfo(name = "job_time")
    private Time jobtime;


    @ColumnInfo(name = "job_date")
    private Date job_date;

    public int getJobid() {
        return jobid;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    public Date getJob_date() {
        return job_date;
    }

    @NonNull
    public Time getJobtime() {
        return jobtime;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    public void setJobtime(@NonNull Time jobtime) {
        this.jobtime = jobtime;
    }

    public void setJob_date(Date job_date) {
        this.job_date = job_date;
    }
}

//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;


@Entity(tableName = "job"  ,
        indices = {@Index(value={"connection_titel"}),
                    @Index(value={"snippet_titel"})},
        foreignKeys = {@ForeignKey(entity = Connection.class,
        parentColumns = "titel",
        childColumns = "connection_titel",
        onDelete = CASCADE),
        @ForeignKey(entity = Snippet.class,
        parentColumns = "titel",
        childColumns = "snippet_titel",
        onDelete = SET_DEFAULT)})
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


    @ColumnInfo(name ="connection_titel")
    private String connection_titel;

    @ColumnInfo(name ="snippet_titel")
    private String snippet_titel;

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

    public String getConnection_titel() {
        return connection_titel;
    }

    public void setConnection_titel(String connection_titel) {
        this.connection_titel = connection_titel;
    }

    public String getSnippet_titel() {
        return snippet_titel;
    }

    public void setSnippet_titel(String snippet_titel) {
        this.snippet_titel = snippet_titel;
    }
}

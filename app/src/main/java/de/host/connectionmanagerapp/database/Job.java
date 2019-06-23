//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_DEFAULT;


@Entity(tableName = "job"  ,
        indices = {@Index(value={"connection_titel"}),
                    @Index(value={"snippet_titel"})},
        foreignKeys = {@ForeignKey(entity = Connection.class,
        parentColumns = "titel",
        childColumns = "connection_titel",
        onUpdate = CASCADE,
        onDelete = CASCADE),
        @ForeignKey(entity = Snippet.class,
        parentColumns = "titel",
        childColumns = "snippet_titel",
                onUpdate = CASCADE,
        onDelete = SET_DEFAULT)})
public class Job {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "job_Id")
    private int job_id;

    @NonNull
    @ColumnInfo(name="titel")
    private String titel;




    @ColumnInfo(name = "job_calender")
    private Calendar c;


    @ColumnInfo(name ="connection_titel")
    private String connection_titel;

    @ColumnInfo(name ="snippet_titel")
    private String snippet_titel;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
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

    public Calendar getC() {
        return c;
    }

    public void setC(Calendar c) {
        this.c = c;
    }
}

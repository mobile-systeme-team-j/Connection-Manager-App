package de.host.connectionmanagerapp;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "snippet_job_join",
        primaryKeys = { "snippet_Id", "job_Id" },
        foreignKeys = {
                @ForeignKey(entity = Snippet.class,
                        parentColumns = "snippet_Id",
                        childColumns = "snippet_Id"),
                @ForeignKey(entity = Job.class,
                        parentColumns = "job_Id",
                        childColumns = "job_Id")
        })
public class Snippet_Job {

    private final int snippet_Id;
    private final int job_Id;


    public Snippet_Job(int snippet_Id, int job_Id) {
        this.snippet_Id = snippet_Id;
        this.job_Id = job_Id;
}

    public int getSnippet_Id() {
        return snippet_Id;
    }

    public int getJob_Id() {
        return job_Id;
    }
}

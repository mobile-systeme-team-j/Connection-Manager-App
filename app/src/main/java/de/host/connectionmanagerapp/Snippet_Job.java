package de.host.connectionmanagerapp;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "snippet_job_join",

        primaryKeys = { "snippet_Id", "job_Id" },
        indices = {@Index(value={"job_Id"})},
        foreignKeys = {
                @ForeignKey(entity = Snippet.class,
                        parentColumns = "snippet_Id",
                        childColumns = "snippet_Id"),
                @ForeignKey(entity = Job.class,
                        parentColumns = "job_Id",
                        childColumns = "job_Id")
        })
public class Snippet_Job {

    private final long snippet_Id;
    private final long job_Id;


    public Snippet_Job(long snippet_Id, long job_Id) {
        this.snippet_Id = snippet_Id;
        this.job_Id = job_Id;
}

    public long getSnippet_Id() {
        return snippet_Id;
    }

    public long getJob_Id() {
        return job_Id;
    }
}

package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity(tableName = "snippet")
public class Snippet {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="snippet_Id")
    private long snippet_id;

    @NonNull
    @ColumnInfo(name = "titel")
    private String titel;

    @NonNull
    @ColumnInfo(name="text")
    private String Text;



    public long getSnippet_id() {
        return snippet_id;
    }

    public void setSnippet_id(long snippet_id) {
        this.snippet_id = snippet_id;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    @NonNull
    public String getText() {
        return Text;
    }

    public void setText(@NonNull String text) {
        Text = text;
    }
}

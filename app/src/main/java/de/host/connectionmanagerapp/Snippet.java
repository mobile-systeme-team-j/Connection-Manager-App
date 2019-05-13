package de.host.connectionmanagerapp;

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
    private int snippet_id;

    @NonNull
    @ColumnInfo(name = "titel")
    private String titel;

    @NonNull
    @ColumnInfo(name="text")
    private String Text;



    public int getSnippet_id() {
        return snippet_id;
    }

    public void setSnippet_id(int snippet_id) {
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

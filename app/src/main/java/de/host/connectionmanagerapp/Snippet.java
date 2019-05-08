package de.host.connectionmanagerapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    @ColumnInfo(name = "folder")
    private File folder;



    public int getSnippet_id() {
        return snippet_id;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    @NonNull
    public File getFolder() {
        return folder;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    public void setFolder(@NonNull File folder) {
        this.folder = folder;
    }
}

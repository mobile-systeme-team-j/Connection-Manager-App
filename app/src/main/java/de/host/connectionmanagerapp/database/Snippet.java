//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;



@Entity(tableName = "snippet",
        indices = {@Index(value = {"titel"}, unique = true)})
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


    public Snippet(String titel) {
        this.titel = titel;
    }

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

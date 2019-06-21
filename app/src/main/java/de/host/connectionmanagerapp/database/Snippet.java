//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



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


    @Ignore
    private boolean checked = false;

    public Snippet(String titel) {
        titel = this.titel;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        checked = checked;
    }
}

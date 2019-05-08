package de.host.connectionmanagerapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "connection")
public class Connection {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="coonection_id")
    private int connection_id;

    @NonNull
    @ColumnInfo(name = "titel")
    private String titel;



    @NonNull
    public String getTitel() {
        return titel;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }


}

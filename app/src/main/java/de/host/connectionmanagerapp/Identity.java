package de.host.connectionmanagerapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "identity_table")
public class Identity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identity_id")
    private int identiy_id;



    @NonNull
    @ColumnInfo(name = "titel")
    private String titel;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name="password")
    private String password;






    public int getIdentiy_id() {
        return identiy_id;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }


    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}

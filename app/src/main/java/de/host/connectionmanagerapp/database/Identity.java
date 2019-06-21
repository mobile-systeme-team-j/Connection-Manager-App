//Autor Mattis Uphoff

package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "identity",
indices = {@Index(value = {"titel"}, unique = true)})
public class Identity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "identity_id")
    private long identiy_id;



    @NonNull
    @ColumnInfo(name = "titel")
    private String titel;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="keypath")
    private String keypath;

    @ColumnInfo(name="keypassword")
    private String keypassword;



    public long getIdentiy_id() {
        return identiy_id;
    }

    public void setIdentiy_id(long identiy_id) {
        this.identiy_id = identiy_id;
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

    public String getKeypath() {
        return keypath;
    }

    public void setKeypath(String keypath) {
        this.keypath = keypath;
    }

    public String getKeypassword() {
        return keypassword;
    }

    public void setKeypassword(String keypassword) {
        this.keypassword = keypassword;
    }
}

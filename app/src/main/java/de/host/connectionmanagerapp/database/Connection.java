//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "connection"
        ,indices = {@Index(value={"identity_Id"})},
        foreignKeys = @ForeignKey(entity = Identity.class,
                parentColumns = "identity_id",
                childColumns = "identity_Id"))
public class Connection {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="connection_Id")
    private long connection_id;

    @NonNull
    @ColumnInfo(name = "titel")
    private String titel;

    @NonNull
    @ColumnInfo(name = "hostip")
    private String hostip;

    @NonNull
    @ColumnInfo(name = "port")
    private int port;

    @ColumnInfo(name="identity_Id")
    private long identity_Id;

    @ColumnInfo(name="timestamp")
    private Date timestamp;

    @Ignore
    private boolean checked = false;

    public long getConnection_id() {
        return connection_id;
    }

    public void setConnection_id(long connection_id) {
        this.connection_id = connection_id;
    }

    @NonNull
    public String getTitel() {
        return titel;
    }

    public void setTitel(@NonNull String titel) {
        this.titel = titel;
    }

    @NonNull
    public String getHostip() {
        return hostip;
    }

    public void setHostip(@NonNull String hostip) {
        this.hostip = hostip;
    }

    public int getPort() {
        return port;
    }

    public void
    setPort(int port) {
        this.port = port;
    }

    public long getIdentity_Id() {
        return identity_Id;
    }

    public void setIdentity_Id(long identity_Id) {
        this.identity_Id = identity_Id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        checked = checked;
    }
}

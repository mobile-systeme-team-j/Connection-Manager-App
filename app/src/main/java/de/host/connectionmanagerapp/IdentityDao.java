package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface IdentityDao {
    @Insert
    void insert(Identity identity);

    @Update
    void update(Identity... identities);

    @Delete
    void delete (Identity... identities);
}

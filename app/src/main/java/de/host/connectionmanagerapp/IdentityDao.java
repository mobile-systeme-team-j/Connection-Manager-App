package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface IdentityDao {
    @Insert
    void insert(Identity identity);
}

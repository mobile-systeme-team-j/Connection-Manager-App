package de.host.connectionmanagerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SnippetDao {

    @Insert
    void insert(Snippet snippet);

    @Update
    void update(Snippet... snippets);

    @Query("SELECT * from snippet where snippet_id=:id")
    public abstract Snippet snippetfromid(long id);

    @Delete
    void delete(Snippet... snippets);

}

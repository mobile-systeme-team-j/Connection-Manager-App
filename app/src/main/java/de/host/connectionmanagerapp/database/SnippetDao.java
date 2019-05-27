//Autor Mattis Uphoff
package de.host.connectionmanagerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SnippetDao {

    @Insert
    void insert(Snippet snippet);

    @Update
    void update(Snippet... snippets);

    @Query("SELECT * from snippet where snippet_id=:id")
    Snippet snippetfromid(long id);

    @Query("Select * from snippet")
    LiveData<List<Snippet>> getAllSnippets();

    @Delete
    void delete(Snippet... snippets);

}

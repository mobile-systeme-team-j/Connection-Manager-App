package de.host.connectionmanagerapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.host.connectionmanagerapp.database.Repository;
import de.host.connectionmanagerapp.database.Snippet;

public class SnippetViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Snippet>> allSnippets;

    public SnippetViewModel(Application application){
        super(application);
        repo = new Repository(application);
        allSnippets = repo.getSnippetsLive();
    }

    LiveData<List<Snippet>> getAllSnippets() { return allSnippets; }
}

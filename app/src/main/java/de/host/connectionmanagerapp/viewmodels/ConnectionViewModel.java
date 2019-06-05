package de.host.connectionmanagerapp.viewmodels;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.database.Repository;
import de.host.connectionmanagerapp.database.Snippet;

public class ConnectionViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Connection>> allConnections;
    private LiveData<List<Connection>> recentConnections;
    private LiveData<List<Identity>> allIdenties;
    private LiveData<List<Job>> allJobs;
    private LiveData<List<Snippet>> allSnippets;

    public ConnectionViewModel(Application application){
        super(application);
        repo = new Repository(application);
        allConnections = repo.getConnectionsLive();
        recentConnections= repo.getRecentConnections();
        allIdenties = repo.getIdentitiesLive();
        allJobs = repo.getJobsLive();
        allSnippets = repo.getSnippetsLive();

    }
    LiveData<List<Connection>> getAllConnections() { return allConnections; }

    LiveData<List<Identity>> getAllIdenties() { return allIdenties; }

    LiveData<List<Job>> getAllJobs(){return allJobs;}

    LiveData<List<Snippet>> getAllSnippets(){return allSnippets;}

    LiveData<List<Connection>> getRecentConnections() { return recentConnections; }
}

package de.host.connectionmanagerapp.viewmodels;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Repository;

public class ConnectionViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Connection>> allConnections;
    private LiveData<List<Connection>> recentConnections;

    public ConnectionViewModel(Application application){
        super(application);
        repo = new Repository(application);
        allConnections = repo.getConnectionsLive();
        recentConnections= repo.getRecentConnections();

    }
    LiveData<List<Connection>> getAllConnections() { return allConnections; }

    LiveData<List<Connection>> getRecentConnections() { return recentConnections; }
}

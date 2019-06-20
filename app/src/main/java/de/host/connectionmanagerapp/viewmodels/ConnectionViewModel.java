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
    //Insert
    public void insertIdentity(Identity identity){
        repo.identity_insert(identity);
    }
    public long insertIdentityid(Identity identity){return repo.identity_insertid(identity);}
    public  void insertConnection(Connection connection){
        repo.connection_insert(connection);
    }
    public void insertSnippet(Snippet snippet){
        repo.snippet_insert(snippet);
    }

    //getOnId
    public Connection getConnection(long id){
        return(repo.getConnection(id));
    }

    public Identity getIdentity(long id){
        return(repo.getIdentity(id));
    }
    public Job getJobs(long id){
        return(repo.getJob(id));
    }
    public Snippet getSnippets(long id){
        return(repo.getSnippet(id));
    }
    //update
    public void updateIdentity(Identity identity){
        repo.identity_update(identity);
    }
    public void updateConnection(Connection connection){
        repo.connection_update(connection);
    }
    public void updateJob(Job job){
        repo.job_update(job);
    }
    public void updateSnippets(Snippet snippet){
        repo.snippet_update(snippet);
    }

    //delete
    public void deleteIdentity(Identity identity){
        repo.identity_delete(identity);
    }
    public void deleteConnection(long id){
        repo.connection_delete(id);
    }
    public void deleteSnippets(long id){repo.snippet_delete(id);}


    public LiveData<List<Connection>> getAllConnections() { return allConnections; }

    public LiveData<List<Identity>> getAllIdenties() { return allIdenties; }

    public LiveData<List<Job>> getAllJobs(){return allJobs;}

    public LiveData<List<Snippet>> getAllSnippets(){return allSnippets;}

    public LiveData<List<Connection>> getRecentConnections() { return recentConnections; }


    ///////////////////////////////
    public List<Identity> getListIdentities(){return (List<Identity>) allIdenties;}
    public List<Job> getListJobs(){return (List<Job>) allJobs;}
    public List<Connection> getListConnections() { return (List<Connection>) allConnections; }
    public List<Snippet> getListSnippets(){return (List<Snippet>) allSnippets;}

}

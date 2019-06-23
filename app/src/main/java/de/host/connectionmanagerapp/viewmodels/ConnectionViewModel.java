//Author Mattis Uphoff
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
    public void insertIdentity(Identity identity) throws Exception{
        repo.identity_insert(identity);
    }
    public long insertIdentityid(Identity identity){return repo.identity_insertid(identity);}
    public  void insertConnection(Connection connection)throws Exception{
        repo.connection_insert(connection);
    }
    public long insertConnectionId(Connection connection){ return repo.connection_insertId(connection); }
    public void insertSnippet(Snippet snippet)throws Exception{
        repo.snippet_insert(snippet);
    }
    public void insertJob(Job job){repo.job_insert(job);}

    //getFromId/Fromtitel
    public Connection getConnection(long id){
        return(repo.getConnection(id));
    }
    public Connection getConnectionFromTitel(String titel){ return(repo.getConnectionFromTitel(titel)); }
    public Identity getIdentity(long id){
        return(repo.getIdentity(id));
    }
    public  Identity getIdentityFromTitel(String titel){return(repo.getIdentityFromTitel(titel));}
    public Job getJobs(int id){
        return(repo.getJob(id));
    }
    public Snippet getSnippets(long id){ return(repo.getSnippet(id)); }
    public Snippet getSnippetsFromTitel(String titel){
        return(repo.getSnippetfromTitel(titel));
    }


    //update
    public void updateIdentity(Identity identity) throws Exception{
        repo.identity_update(identity);
    }
    public void updateConnection(Connection connection)throws Exception{
        repo.connection_update(connection);
    }
    public void updateJob(Job job) throws Exception{
        repo.job_update(job);
    }
    public void updateSnippets(Snippet snippet)throws Exception{
        repo.snippet_update(snippet);
    }

    //delete
    public void deleteIdentity(long id){
        repo.identity_delete(id);
    }
    public void deleteConnection(long id){
        repo.connection_delete(id);
    }
    public void deleteSnippets(long id){repo.snippet_delete(id);}
    public void deleteJob(int id){repo.job_delete(id);}


    public LiveData<List<Connection>> getAllConnections() { return allConnections; }

    public LiveData<List<Identity>> getAllIdenties() { return allIdenties; }

    public LiveData<List<Job>> getAllJobs(){return allJobs;}

    public LiveData<List<Snippet>> getAllSnippets(){return allSnippets;}

    public LiveData<List<Connection>> getRecentConnections() { return recentConnections; }



}

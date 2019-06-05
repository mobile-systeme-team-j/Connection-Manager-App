//Autor Mattis Uphoff

package de.host.connectionmanagerapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    private IdentityDao identityDao;
    private ConnectionDao connectionDao;
    private JobDao jobDao;
    private SnippetDao snippetDao;
    private Connection_JobDao cjDao;
    private Snippet_JobDao sjDao;
    private long jobId;

    public Repository(Application application){
        Roombuilder db = Roombuilder.getDatabase(application);
        identityDao = db.identityDao();
        connectionDao = db.connectionDao();
        jobDao = db.jobDao();
        snippetDao= db.snippetDao();
        cjDao = db.cjDao();
        sjDao = db.sjDao();

    }

    //Insert Methoden
    //Fertige identity muss übergeben werden , es erfolgt keine überpfüfung mehr
    public void identity_insert(Identity identity){identityDao.insert(identity);}

    //Fertige connection übergeben es erfolgt eine überprüfung mehr, auch die identity id muss gesetzt werden
    public void connection_insert(Connection connection){connectionDao.insert(connection);}

    public void job_insert(Job job, long snippetId, long connectionId ){
        jobId = jobDao.insert(job);
        Connection_Job cj = new Connection_Job(jobId, connectionId);
        cjDao.insert(cj);

        Snippet_Job sj = new Snippet_Job(snippetId, jobId);
        sjDao.insert(sj);
    }
    //Fertiges snippet übergeben es erfolgt eine überprüfung mehr, auch die identity id muss gesetzt werden
    public void snippet_insert(Snippet snippet){snippetDao.insert(snippet);}


    //getEntity
    public Identity getIdentity(long id){
        return identityDao.identityformid(id);
    }
    public Connection getConnection(long id){
        return connectionDao.connectionfromid(id);
    }
    public Snippet getSnippet(long id){
        return snippetDao.snippetfromid(id);
    }
    public Job getJob(long id){
        return jobDao.jobfromId(id);
    }
    //LiveData
    public LiveData<List<Connection>> getConnectionsLive() {
        return connectionDao.getAllConnection();
    }

    public LiveData<List<Connection>> getRecentConnections(){
        return connectionDao.getRecentConnection();
    }

    public LiveData<List<Identity>> getIdentitiesLive(){
        return  identityDao.getAllIdentities();
    }

    public LiveData<List<Job>> getJobsLive(){
        return jobDao.getAllJobs();
    }

    public LiveData<List<Snippet>> getSnippetsLive(){
        return snippetDao.getAllSnippets();
    }

    // Updates
    public void identity_update(Identity identity){
        identityDao.update(identity);
    }
    public void connection_update(Connection connection){
        connectionDao.update(connection);
    }
    public void job_update(Job job){
        jobDao.update(job);
    }
    public void snippet_update(Snippet snippet){
        snippetDao.update(snippet);
    }

    //DeleteMethoden
    public void identity_delete(long identityid){
        Identity identity = identityDao.identityformid(identityid);
        identityDao.delete(identity);
    }
    public void snippet_delete(long snippetId){
        Snippet snippet = snippetDao.snippetfromid(snippetId);
        List<Snippet_Job> sjs = sjDao.sjsfromsnippet(snippetId);
        for(Snippet_Job sj: sjs) {
            sjDao.delete(sj);
        }
        snippetDao.delete(snippet);
    }
    public void connection_delete(long connectionId){
        Connection conection = connectionDao.connectionfromid(connectionId);
        List<Connection_Job>cjs= cjDao.cjsconnection(connectionId);
        for(Connection_Job cj : cjs){
            cjDao.delete(cj);
        }

        connectionDao.delete(conection);
    }
    public void job_delete(long jobId){
        Job job = jobDao.jobfromId(jobId);
        List<Snippet_Job> sjs = sjDao.sjsfromjob(jobId);
        List<Connection_Job>cjs= cjDao.cjsjobs(jobId);

        for(Snippet_Job sj: sjs) {
            sjDao.delete(sj);
        }
        for(Connection_Job cj : cjs){
            cjDao.delete(cj);
        }

        jobDao.delete(job);
    }



}
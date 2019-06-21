//Autor Mattis Uphoff

package de.host.connectionmanagerapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.host.connectionmanagerapp.daos.ConnectionDao;
import de.host.connectionmanagerapp.daos.Connection_JobDao;
import de.host.connectionmanagerapp.daos.IdentityDao;
import de.host.connectionmanagerapp.daos.JobDao;
import de.host.connectionmanagerapp.daos.SnippetDao;
import de.host.connectionmanagerapp.daos.Snippet_JobDao;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

public class Repository {

    private IdentityDao identityDao;
    private ConnectionDao connectionDao;
    private JobDao jobDao;
    private SnippetDao snippetDao;
    private Connection_JobDao cjDao;
    private Snippet_JobDao sjDao;

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
    public void identity_insert(Identity identity){
      Executor exe = Executors.newSingleThreadExecutor();
      exe.execute(() -> {
          identityDao.insert(identity);
      });
    }
    public long connection_insertId(Connection connection){
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future<Long> future = exe.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long id = connectionDao.insertid(connection);
                return id;
            }
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public long identity_insertid(Identity identity) {
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future<Long> future = exe.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long id =identityDao.insertid(identity);
                return id;
            }
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }


    //Fertige connection übergeben es erfolgt eine überprüfung mehr, auch die identity id muss gesetzt werden
    public void connection_insert(Connection connection) {
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
            connectionDao.insert(connection);
        });
    }

    public void job_insert(Job job, long snippetId, long connectionId ){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
         long id = jobDao.insert(job);
        Connection_Job cj = new Connection_Job(id, connectionId);
        cjDao.insert(cj);

        Snippet_Job sj = new Snippet_Job(snippetId, id);
        sjDao.insert(sj);
        });
    }
    //Fertiges snippet übergeben es erfolgt eine überprüfung mehr, auch die identity id muss gesetzt werden
    public void snippet_insert(Snippet snippet) {
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
            snippetDao.insert(snippet);
        });
    }


    //getEntity
    public Identity getIdentity(long id){
        Flowable<Identity> identity = identityDao.identityformid(id);
        Identity identity1 = identity.blockingFirst();
        return  identity1;

    }
    public Identity getIdentityFromTitel(String titel){
        return identityDao.identityformtitel(titel).blockingFirst();
    }
    public Connection getConnection(long id){

        Flowable<Connection>connectionFlowable = connectionDao.connectionfromid(id);
        return connectionFlowable.blockingFirst();
    }
    public Snippet getSnippet(long id){

        Flowable<Snippet> snippetFlowable = snippetDao.snippetfromid(id);
        return snippetFlowable.blockingFirst();
    }
    public Job getJob(long id){
        Flowable<Job> jobFlowable = jobDao.jobfromId(id);
        return jobFlowable.blockingFirst();
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
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
            identityDao.update(identity);
        });
    }
    public void connection_update(Connection connection){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
        connectionDao.update(connection);
        });
    }
    public void job_update(Job job){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
        jobDao.update(job);
        });
    }
    public void snippet_update(Snippet snippet){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
        snippetDao.update(snippet);});
    }

    //DeleteMethoden
    public void identity_delete(long id){

        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
            Identity identity=identityDao.identityformid(id).blockingFirst();
            identityDao.delete(identity);
        });
    }
    public void snippet_delete(long snippetId){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
            Snippet snippet = snippetDao.snippetfromid(snippetId).blockingFirst();
            snippetDao.delete(snippet);
        });
    }
    public void connection_delete(long connectionId){
        Connection conection = connectionDao.connectionfromid(connectionId).blockingFirst();
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
        connectionDao.delete(conection);
        });
    }
    public void job_delete(Job job){
        long jobId = job.getJob_id();

        jobDao.delete(job);
    }



}
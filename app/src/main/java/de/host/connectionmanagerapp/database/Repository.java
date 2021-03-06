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
import de.host.connectionmanagerapp.daos.IdentityDao;
import de.host.connectionmanagerapp.daos.JobDao;
import de.host.connectionmanagerapp.daos.SnippetDao;
import io.reactivex.Flowable;

public class Repository {

    private IdentityDao identityDao;
    private ConnectionDao connectionDao;
    private JobDao jobDao;
    private SnippetDao snippetDao;
    private Application application;

    public Repository(Application application){
        this.application = application;
        Roombuilder db = Roombuilder.getDatabase(application);
        identityDao = db.identityDao();
        connectionDao = db.connectionDao();
        jobDao = db.jobDao();
        snippetDao= db.snippetDao();

    }


    //Insert Methoden
    //Fertige identity muss übergeben werden , es erfolgt keine überpfüfung mehr
    public void identity_insert(Identity identity) throws Exception{
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                identityDao.insert(identity);
                return null;
            }

      });
            future.get();
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
    public void connection_insert(Connection connection)throws Exception {
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                connectionDao.insert(connection);
                return null;
            }

        });
        future.get();
    }

    public void job_insert(Job job){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
         long id = jobDao.insert(job);
        });
    }
    //Fertiges snippet übergeben es erfolgt eine überprüfung mehr, auch die identity id muss gesetzt werden
    public void snippet_insert(Snippet snippet)throws Exception{

        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                snippetDao.insert(snippet);
                return null;
            }

        });
        future.get();
    }


    //getEntityFromId/FromTitel
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
    public Connection getConnectionFromTitel(String titel){
        Flowable<Connection>connectionFlowable = connectionDao.connectionfromtitel(titel);
        return connectionFlowable.blockingFirst();
    }
    public Snippet getSnippet(long id){

        Flowable<Snippet> snippetFlowable = snippetDao.snippetfromid(id);
        return snippetFlowable.blockingFirst();
    }
    public Snippet getSnippetfromTitel(String titel){
        Flowable<Snippet> snippetFlowable = snippetDao.snippetfromtitel(titel);
        return snippetFlowable.blockingFirst();
    }
    public Job getJob(int id){
        Flowable<Job> jobFlowable = jobDao.jobfromId(id);
        return jobFlowable.blockingFirst();
    }
    public Job getJobfromConnection(String titel){
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future<Job> future = exe.submit(new Callable<Job>() {
            @Override
            public Job call() throws Exception {
                 Job job = jobDao.jobfromcoonectionTitel(titel);
                return job;
            }
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Job getJobfromSnippet(String titel){
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future<Job> future = exe.submit(new Callable<Job>() {
            @Override
            public Job call() throws Exception {
                Job job = jobDao.jobfromsnippetTitel(titel);
                return job;
            }
        });

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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
    public void identity_update(Identity identity)throws Exception {
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                identityDao.update(identity);
                return null;
            }

        });
        future.get();

    }
    public void connection_update(Connection connection)throws Exception{
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                connectionDao.update(connection);
                return null;
            }
        });
        future.get();
    }
    public void job_update(Job job) throws Exception{
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                jobDao.update(job);
                return null;
            }
        });
        future.get();
    }
    public void snippet_update(Snippet snippet)throws Exception{
        ExecutorService exe = Executors.newSingleThreadExecutor();
        Future future = exe.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                snippetDao.update(snippet);
                return null;
            }
        });
        future.get();
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
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
            Connection conection = connectionDao.connectionfromid(connectionId).blockingFirst();
            connectionDao.delete(conection);
        });
    }
    public void job_delete(int id){
        Executor exe = Executors.newSingleThreadExecutor();
        exe.execute(() -> {
        Job job = jobDao.jobfromId(id).blockingFirst();
        jobDao.delete(job);
        });

    }
}
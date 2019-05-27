package de.host.connectionmanagerapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.database.Repository;

public class JobViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<List<Job>> allJobs;

    public JobViewModel(Application application){
        super(application);
        repo = new Repository(application);
        allJobs = repo.getJobsLive();
    }
    LiveData<List<Job>> getAllJobs() { return allJobs; }
}

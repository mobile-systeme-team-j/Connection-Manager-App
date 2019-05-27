package de.host.connectionmanagerapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Repository;


public class IdentityViewModel extends AndroidViewModel {

    private Repository repo;
    private LiveData<List<Identity>> allIdenties;

    public IdentityViewModel(Application application){
        super(application);
        repo = new Repository(application);
        allIdenties = repo.getIdentitiesLive();
    }
    LiveData<List<Identity>> getAllIdenties() { return allIdenties; }
}

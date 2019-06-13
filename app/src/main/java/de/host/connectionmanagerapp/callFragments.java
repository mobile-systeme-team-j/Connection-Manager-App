package de.host.connectionmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.activityFragments.HomeFragment;

public class callFragments extends AppCompatActivity {

    public void addFragment(Fragment frag) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, frag)
                    .commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,  frag)
                .addToBackStack(null)
                .commit();

    }


}

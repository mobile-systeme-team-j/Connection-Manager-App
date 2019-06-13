package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;


/**
 * @author Manuel Trapp
 * @date 14.05.2019
 * */

public class ConnectionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_connections, container, false);
    }

    public void onClick(View v){
        if(v.getId() == R.id.fabNewConnection){
            ((MainActivity)getActivity()).changeFragment(new ConnectionDetailFragment());
        }
    }
}


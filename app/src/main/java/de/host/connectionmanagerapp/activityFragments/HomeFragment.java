package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;

/**
 * @author Manuel Trapp
 * @date 14.05.2019
 * */

public class HomeFragment extends Fragment {

    ListView lastConnections;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lastConnections = view.findViewById(R.id.ListViewLastConnections);

        // Verbindung aufbauen nach Click auf ListenElement
        lastConnections.setOnItemClickListener((adapterView, listView, position, id) -> {
            // Objekt aus ListView ziehen, SshSessionFragment mit Connection-ID aufrufen
            /*
            Object listItem = lastConnections.getItemAtPosition(position);
            int connID = listItem.getConnectionID;
            SshSessionFragment fragment = SshSessionFragment.newInstance(connID);
            getFragmentManager().beginTransaction()
                    .replace(view.getId(), fragment)
                    .addToBackStack(null)
                    .commit();
                    */

        });

        return view;
    }

    public void onClick(View view){
        if(view.getId() == R.id.WizardButton){
            ((MainActivity)getActivity()).replaceFragment(new WizardFragment());
        }
    }
}
package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.ConnectionAdapter;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;


public class HomeFragment extends Fragment implements View.OnClickListener{

    ListView lastConnections;
    RecyclerView recyclerView;
    ConnectionViewModel connectionViewModel;
    NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.con_recycler_view);

        FloatingActionButton wizard = view.findViewById(R.id.WizardButton);
        wizard.setOnClickListener(this);
        FloatingActionButton remote = view.findViewById(R.id.RemoteButton);
        remote.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.con_recycler_view);
        final ConnectionAdapter adapter = new ConnectionAdapter(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel = ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getRecentConnections().observe(this, connections -> adapter.setConnections(connections));

        // Verbindung aufbauen nach Click auf ListenElement
       // lastConnections.setOnItemClickListener((adapterView, listView, position, id) -> {
            // Objekt aus ListView ziehen, SshSessionFragment mit Connection-ID aufrufen
            /*
            Object listItem = lastConnections.getItemAtPosition(position);
            int connID = listItem.getConnectionID;
            ((MainActivity)getActivity()).replaceFragment(new SshSessionFragment());


        });
*/
        return view;
    }

    public void onClick(View view){
        if(view.getId() == R.id.WizardButton){
            ((MainActivity)getActivity()).replaceFragment(new WizardFragment());
        }
        if(view.getId() == R.id.RemoteButton){
            ((MainActivity)getActivity()).replaceFragment(new RemoteFragment());
        }
    }
}
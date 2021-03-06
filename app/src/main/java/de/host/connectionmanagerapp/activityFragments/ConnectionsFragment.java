package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.ConnectionAdapter;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;



public class ConnectionsFragment extends Fragment implements View.OnClickListener{


    private ConnectionViewModel connectionViewModel;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connections, container, false);

        recyclerView = view.findViewById(R.id.con_recycler_view);
        final ConnectionAdapter adapter = new ConnectionAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllConnections().observe(this,connections -> adapter.setConnections(connections));

        FloatingActionButton actionButton = view.findViewById(R.id.fabNewConnection);
        actionButton.setOnClickListener(this);

        return view;


    }

    public void onClick(View v){
        ((MainActivity)getActivity()).replaceFragment(new ConnectionDetailFragment());
    }
}


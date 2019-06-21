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
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.ConnectionAdapterSelection;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class ConnectionSelectionFragment extends Fragment {

    long id;
    ConnectionViewModel connectionViewModel;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_selection, container, false);
        recyclerView = view.findViewById(R.id.con_sel_recycler_view);

        final ConnectionAdapterSelection adapter = new ConnectionAdapterSelection(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel = ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllConnections().observe(this, connections ->
                adapter.setConnections(connections));

            return view;
        }
    }


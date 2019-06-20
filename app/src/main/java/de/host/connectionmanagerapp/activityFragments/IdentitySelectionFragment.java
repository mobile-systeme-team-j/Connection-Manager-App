package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.IdentityAdapter;
import de.host.connectionmanagerapp.adapter.IdentityAdapterSelection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.helper.Identity_id_holder;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class IdentitySelectionFragment extends Fragment {

    ConnectionViewModel connectionViewModel;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity_selection, container, false);
        recyclerView = view.findViewById(R.id.ide_sel_recycler_view);


        final IdentityAdapterSelection adapter = new IdentityAdapterSelection(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllIdenties().observe(this, new Observer<List<Identity>>()
        {
            @Override
            public void onChanged(@Nullable final List<Identity> identities) {
                adapter.setIdentities(identities);
            }
        });


        return view;
    }


}

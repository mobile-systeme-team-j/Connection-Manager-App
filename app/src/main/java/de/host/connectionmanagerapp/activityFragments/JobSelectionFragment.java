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
import de.host.connectionmanagerapp.adapter.JobAdapterSelection;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class JobSelectionFragment extends Fragment {

    long id;
    ConnectionViewModel connectionViewModel;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_selection, container, false);
        recyclerView = view.findViewById(R.id.job_sel_recycler_view);

        final JobAdapterSelection adapter = new JobAdapterSelection(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel = ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllJobs().observe(this, jobs ->
                adapter.setJobs(jobs));

        return view;
    }
}

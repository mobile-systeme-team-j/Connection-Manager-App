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
import de.host.connectionmanagerapp.adapter.JobAdapter;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class JobsFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    ConnectionViewModel connectionViewModel;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        FloatingActionButton actionButton = view.findViewById(R.id.fabNewJob);
        actionButton.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.job_recycler_view);
        final JobAdapter adapter = new JobAdapter(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllJobs().observe(this, jobs -> adapter.setJobs(jobs));


        return view;
    }

    public void onClick(View view){
        ((MainActivity)getActivity()).replaceFragment(new JobDetailFragment());
    }
}

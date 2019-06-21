package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.SnippetAdapter;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

/**
 * @author Manuel Trapp
 * @date 14.05.2019
 * */

public class SnippetsFragment extends Fragment implements View.OnClickListener{

    ConnectionViewModel connectionViewModel;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_snippets, container, false);

        FloatingActionButton actionButton = view.findViewById(R.id.fabNewSnippet);
        recyclerView = view.findViewById(R.id.sni_recycler_view);
        actionButton.setOnClickListener(this);

        final SnippetAdapter adapter = new SnippetAdapter(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllSnippets().observe(this, snippets -> adapter.setSnippets(snippets));

        return view;
    }

    @Override
    public void onClick(View v) {
       ((MainActivity)getActivity()).replaceFragment(new SnippetDetailFragment());
    }
}
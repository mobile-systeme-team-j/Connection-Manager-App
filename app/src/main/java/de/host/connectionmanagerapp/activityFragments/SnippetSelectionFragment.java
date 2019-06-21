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

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.MultiAdapterSnippet;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;
import de.host.connectionmanagerapp.viewmodels.SelectableSnippets;

public class SnippetSelectionFragment extends Fragment{

    long id;
    ConnectionViewModel connectionViewModel;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snippet_selection, container, false);
        recyclerView = view.findViewById(R.id.sni_sel_recycler_view);

        MultiAdapterSnippet adapter  = new MultiAdapterSnippet(getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel = ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllSnippets().observe(this, snippets ->
                adapter.setSnippets(snippets));

        return view;
    }

}

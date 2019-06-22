package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.adapter.SnippetAdapter;
import de.host.connectionmanagerapp.adapter.SnippetAdapterSelection;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.helper.Snippet_id_holder;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class SnippetSelectionFragment extends Fragment{

    long[] ids;
    ConnectionViewModel connectionViewModel;
    RecyclerView recyclerView;
    Button select;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snippet_selection, container, false);
        recyclerView = view.findViewById(R.id.snp_sel_recycler_view);

        SnippetAdapterSelection adapter = new SnippetAdapterSelection(getActivity());


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        connectionViewModel = ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        connectionViewModel.getAllSnippets().observe(this, snippets ->
                adapter.setSnippets(snippets));

        return view;
    }

}

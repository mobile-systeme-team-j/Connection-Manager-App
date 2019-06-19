package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class SnippetDetailFragment extends Fragment {

    EditText editTextSnippetname;
    EditText editTextSnippetText;
    Bundle arguments;
    long id;
    Snippet snippet;
    ConnectionViewModel connectionViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snippet_detail, container, false);

        editTextSnippetname = view.findViewById(R.id.editTextSnippetname);
        editTextSnippetText = view.findViewById(R.id.editTextSnippetText);

        arguments  = getArguments();
        if(arguments != null){
            id = arguments.getLong("id");
            snippet = connectionViewModel.getSnippets(id);

            getSnippet();
        }
        return view;
    }


    public Snippet getSnippet(){

        return snippet;
    }
}
package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
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

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class SnippetSelectionFragment extends Fragment {

    Bundle arguments;
    long id;
    Snippet snippet;
    ConnectionViewModel connectionViewModel;
    ListView snippetListView;
    Button select;
    List<Snippet> snippetList;
    String[] snippetArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_selection, container, false);

        snippetList = view.findViewById(R.id.snippetList);

        arguments = getArguments();
        if(arguments != null){
            id = arguments.getLong("id");
            snippetList = connectionViewModel.getListSnippets();
        }


        // android-coding.blogspot.com/2011/09/listview-with-multiple-choice.html

        ArrayAdapter<Snippet> adapter = new ArrayAdapter<Snippet>(getContext(),android.R.layout.simple_list_item_single_choice, snippetList);

        snippetListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        snippetListView.setAdapter(adapter);

        select.setOnClickListener(v -> {
            String selected = "";
            int cntChoice = snippetListView.getCount();

            SparseBooleanArray sparseBooleanArray = snippetListView.getCheckedItemPositions();

            for(int i = 0; i < cntChoice; i++){

                if(sparseBooleanArray.get(i)) {

                    selected += snippetListView.getItemAtPosition(i).toString() + "\n";
                }
            }

            Toast.makeText(getContext(),selected,Toast.LENGTH_LONG).show();

        });


        return view;
    }
}

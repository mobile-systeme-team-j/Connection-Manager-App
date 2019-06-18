package de.host.connectionmanagerapp;

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

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class ConnectionSelectionFragment extends Fragment {

    Bundle arguments;
    long id;
    Connection connection;
    ConnectionViewModel connectionViewModel;
    List<Connection> connectionList;
    ListView connectionListView;
    Button select;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_selection, container, false);

        connectionList = view.findViewById(R.id.connectionList);


        arguments = getArguments();
        if(arguments != null){
            id = arguments.getLong("id");
                connectionList = connectionViewModel.getListConnections();
        }

        // android-coding.blogspot.com/2011/09/listview-with-multiple-choice.html

       ArrayAdapter<Connection> adapter = new ArrayAdapter<Connection>(getContext(),android.R.layout.simple_list_item_single_choice, connectionList);

        connectionListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        connectionListView.setAdapter(adapter);

        select.setOnClickListener(v -> {
            String selected = "";
            int cntChoice = connectionListView.getCount();

            SparseBooleanArray sparseBooleanArray = connectionListView.getCheckedItemPositions();

            for(int i = 0; i < cntChoice; i++){

                if(sparseBooleanArray.get(i)) {

                    selected += connectionListView.getItemAtPosition(i).toString() + "\n";
                }
            }

            Toast.makeText(getContext(),selected,Toast.LENGTH_LONG).show();

        });


        return view;
    }
}

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

import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class IdentitySelectionFragment extends Fragment {

    Bundle arguments;
    long id;
    Identity identity;
    ConnectionViewModel connectionViewModel;
    ListView identityListView;
    Button select;
    List<Identity> identityList;
    String[] identityArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_selection, container, false);

        identityList = view.findViewById(R.id.identityList);

        arguments = getArguments();
        if(arguments != null){
            id = arguments.getLong("id");
            identityList = connectionViewModel.getListIdentities();
        }


       // android-coding.blogspot.com/2011/09/listview-with-multiple-choice.html

        ArrayAdapter<Identity> adapter = new ArrayAdapter<Identity>(getContext(),android.R.layout.simple_list_item_single_choice, identityList);

        identityListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        identityListView.setAdapter(adapter);

        select.setOnClickListener(v -> {
                String selected = "";
                int cntChoice = identityListView.getCount();

                SparseBooleanArray sparseBooleanArray = identityListView.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++){

                    if(sparseBooleanArray.get(i)) {

                        selected += identityListView.getItemAtPosition(i).toString() + "\n";
                    }
                }

                Toast.makeText(getContext(),selected,Toast.LENGTH_LONG).show();

            });


        return view;
    }

}

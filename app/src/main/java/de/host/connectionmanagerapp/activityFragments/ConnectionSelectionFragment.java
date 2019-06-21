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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class ConnectionSelectionFragment extends Fragment {

    Bundle arguments;
    long id;
    Connection connection;
    ConnectionViewModel connectionViewModel;
    List<Connection> connectionList;
    RecyclerView connectionListView;
    Button select;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_selection, container, false);

        connectionListView = view.findViewById(R.id.recycler_con_sel);



            return view;
        }

    }


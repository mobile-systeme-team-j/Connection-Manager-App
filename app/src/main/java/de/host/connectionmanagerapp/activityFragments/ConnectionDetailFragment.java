package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class ConnectionDetailFragment extends Fragment implements View.OnClickListener{
    FloatingActionButton delete;
    FloatingActionButton save;
    EditText editTextConnectionName;
    EditText editTextHostname;
    EditText editTextPort;
    Bundle arguments;
    Connection connection;
    long id;
    private ConnectionViewModel connectionViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        editTextConnectionName = view.findViewById(R.id.editTextHostname);
        editTextHostname = view.findViewById(R.id.editTextHostname);
        editTextPort = view.findViewById(R.id.editTextPort);


        arguments = getArguments();
        if(arguments != null){
            id = arguments.getLong("id");
            connection = connectionViewModel.getConnection(id);

            getConnection();
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabDelete:
                connectionViewModel.deleteConnection(id);
                break;
            case R.id.fabSave:
                if(arguments != null){
                    try{
                        connectionViewModel.updateConnection(setConnection());
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
                    }
                }
                else{
                    try{
                        connection = new Connection();
                        connectionViewModel.insertConnection(setConnection());
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
                    }
                }
                break;

        }
    }

    public Connection setConnection(){
        connection.setTitel(String.valueOf(editTextConnectionName.getText()));
        connection.setHostip(String.valueOf(editTextHostname.getText()));
        connection.setPort((Integer.parseInt(String.valueOf(editTextPort.getText()))));
        return connection;
    }

    public Connection getConnection(){
        editTextConnectionName.setText(connection.getTitel());
        editTextHostname.setText(connection.getHostip());
        editTextPort.setText(connection.getPort());
        return connection;
    }
}

package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.helper.Identity_id_holder;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class ConnectionDetailFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    FloatingActionButton delete;
    FloatingActionButton save;
    FloatingActionButton connect;
    Button identity;
    EditText editTextConnectionName;
    EditText editTextHostname;
    EditText editTextPort;
    Bundle arguments;
    private Connection connection;

    long id;
    private ConnectionViewModel connectionViewModel;
    Spinner spinnerConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_detail, container, false);

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        editTextConnectionName = view.findViewById(R.id.editTextConnectionName);
        editTextHostname = view.findViewById(R.id.editTextHostname);
        editTextPort = view.findViewById(R.id.editTextPort);

        delete = view.findViewById(R.id.fabDelete);
        save = view.findViewById(R.id.fabSave);
        connect = view.findViewById(R.id.fabConnect);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
        connect.setOnClickListener(this);
        identity = view.findViewById(R.id.btnIdentity);
        identity.setOnClickListener(this);



/*
        ArrayAdapter<Connection> adapter = new ArrayAdapter<Connection>(this,android.R.layout.simple_spinner_dropdown_item,arrayList1);
        spinnerConnection.setAdapter(adapter);
        spinnerConnection.setOnItemClickListener(this);
*/


        arguments = getArguments();
        if(arguments != null){
            id = arguments.getLong("con_id");
            connection = connectionViewModel.getConnection(id);

            getConnection();
        }
        else{
            connection = new Connection();
        }

        return view;
    }
    @Override
    public void onResume() {
        Identity_id_holder holder = new Identity_id_holder();
        Log.e("DEBUG", "" + holder.id);
        if(holder.id != 0){
            connection.setIdentity_Id(holder.id);
        }
            super.onResume();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabDelete:
                connectionViewModel.deleteConnection(id);
                Toast.makeText(getContext(),"Connection deleted",Toast.LENGTH_SHORT).show();

                break;
            case R.id.fabConnect:
                // CallSShSessionFragment
                ((MainActivity)getActivity()).replaceFragment(new SshSessionFragment());
            case R.id.fabSave:
                if(arguments != null){
                    try{
                        connectionViewModel.updateConnection(connection);
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
                    }
                }
                else{
                    try{
                        setConnection();
                        connectionViewModel.insertConnection(connection);
                        Toast.makeText(getContext(),"Identity saved", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
                    }
                }
                break;
            case R.id.btnIdentity:
                ((MainActivity)getActivity()).replaceFragment(new IdentitySelectionFragment());
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    public void setConnection(){
        connection.setTitel(String.valueOf(editTextConnectionName.getText()));
        connection.setHostip(String.valueOf(editTextHostname.getText()));
        connection.setPort((Integer.parseInt(String.valueOf(editTextPort.getText()))));
    }

    public void getConnection(){
        editTextConnectionName.setText(connection.getTitel());
        editTextHostname.setText(connection.getHostip());
        editTextPort.setText(connection.getPort()+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

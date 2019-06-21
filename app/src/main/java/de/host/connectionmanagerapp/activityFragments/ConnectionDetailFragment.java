package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.helper.HideKeyboard;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_detail, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        editTextConnectionName = view.findViewById(R.id.editTextConnectionName);
        editTextHostname = view.findViewById(R.id.editTextHostname);
        editTextPort = view.findViewById(R.id.editTextPort);

        delete = view.findViewById(R.id.fabDelete);
        save = view.findViewById(R.id.fabSave);
        connect = view.findViewById(R.id.fabConnectRemoteSSH);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
        connect.setOnClickListener(this);
        identity = view.findViewById(R.id.btnIdentity);
        identity.setOnClickListener(this);


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
        if(holder.id != null){
            connection.setIdentity_Id(holder.id);
            holder.id = null;
        }
            super.onResume();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabDelete:
                try {
                    connectionViewModel.deleteConnection(id);
                    Toast.makeText(getContext(),"Connection deleted",Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                    HideKeyboard.hideKeyboard(getContext());

                }catch (Exception e){

                }

                break;
            case R.id.fabConnectRemoteSSH:
                // CallSShSessionFragment
                if (id != 0) {
                    ((MainActivity)getActivity()).replaceFragment(SshSessionFragment.newInstance(id, false));
                }
                break;

            case R.id.fabSave:
                if(arguments != null){
                    try{
                        connectionViewModel.updateConnection(connection);
                        Toast.makeText(getContext(),"Connection updated",Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                        HideKeyboard.hideKeyboard(getContext());
                    }catch (Exception e){

                    }
                }
                else{
                    try{
                        setConnection();
                        connectionViewModel.insertConnection(connection);
                        Toast.makeText(getContext(),"Connection saved", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
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

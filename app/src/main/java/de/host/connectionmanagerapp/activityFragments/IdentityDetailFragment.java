package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Repository;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class IdentityDetailFragment extends Fragment implements View.OnClickListener{

    EditText editTextIdentityName;
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextKey;
    EditText editTextKeyPassword;
    Identity identity;
    Bundle arguments;
    private ConnectionViewModel connectionViewModel;
    long id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);


        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        editTextIdentityName = view.findViewById(R.id.identity_name);
        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        editTextKey = view.findViewById(R.id.key);
        editTextKeyPassword = view.findViewById(R.id.key_password);

        arguments = getArguments();
        if(arguments !=null){
            id = arguments.getLong("id");
            identity = connectionViewModel.getIdentity(id);

            editTextIdentityName.setText(identity.getTitel());
            editTextUsername.setText(identity.getUsername());
            editTextPassword.setText(identity.getPassword());
            editTextKey.setText(identity.getKeypath());
            editTextKeyPassword.setText(identity.getKeypassword());
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.fabDelete:
                if(arguments !=null) {
                    connectionViewModel.deleteIdentity(id);
                }
                else{

                }
                break;
            case R.id.fabSave:
                if(arguments !=null) {
                    try{
                        identity.setTitel(String.valueOf(editTextIdentityName.getText()));
                        identity.setUsername(String.valueOf(editTextUsername.getText()));
                        identity.setPassword(String.valueOf(editTextPassword.getText()));
                        identity.setKeypath(String.valueOf(editTextKey.getText()));
                        identity.setKeypassword(String.valueOf(editTextKeyPassword.getText()));
                        connectionViewModel.updateIdentity(identity);
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Please fill all lines",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    try{
                        identity = new Identity();
                        identity.setTitel(String.valueOf(editTextIdentityName.getText()));
                        identity.setUsername(String.valueOf(editTextUsername.getText()));
                        identity.setPassword(String.valueOf(editTextPassword.getText()));
                        identity.setKeypath(String.valueOf(editTextKey.getText()));
                        identity.setKeypassword(String.valueOf(editTextKeyPassword.getText()));
                        connectionViewModel.insertIdentity(identity);
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Please fill all lines",Toast.LENGTH_SHORT).show();
                    }
                }

                break;

        }
    }
}

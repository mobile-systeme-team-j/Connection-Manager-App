package de.host.connectionmanagerapp.activityFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

import static android.app.Activity.RESULT_OK;

public class IdentityDetailFragment extends Fragment implements View.OnClickListener{

    EditText editTextIdentityName;
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextKey;
    EditText editTextKeyPassword;
    private Identity identity;
    private Bundle arguments;
    private ConnectionViewModel connectionViewModel;
    long id;
    FloatingActionButton delete;
    FloatingActionButton save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity_detail, container, false);


        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        editTextIdentityName = view.findViewById(R.id.identity_name);
        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        editTextKey = view.findViewById(R.id.key);
        editTextKeyPassword = view.findViewById(R.id.key_password);
        delete = view.findViewById(R.id.fabDelete);
        save = view.findViewById(R.id.fabSave);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
        editTextKey.setOnClickListener(this);

        arguments = getArguments();
        if(arguments !=null){
            id = arguments.getLong("id");

            identity = connectionViewModel.getIdentity(id);

            getIdentity(identity);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.key:
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
                break;
            case R.id.fabDelete:
                if(arguments !=null) {
                    connectionViewModel.deleteIdentity(identity);
                }
                else{

                }
                break;
            case R.id.fabSave:
                if(arguments !=null) {
                    try{
                        connectionViewModel.updateIdentity(setIdentity());
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Please fill all lines",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    try{
                        identity = new Identity();
                        connectionViewModel.insertIdentity(setIdentity());
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Please fill all lines",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    public Identity setIdentity(){
        identity.setTitel(String.valueOf(editTextIdentityName.getText()));
        identity.setUsername(String.valueOf(editTextUsername.getText()));
        identity.setPassword(String.valueOf(editTextPassword.getText()));
        identity.setKeypath(String.valueOf(editTextKey.getText()));
        identity.setKeypassword(String.valueOf(editTextKeyPassword.getText()));
        return identity;
    }

    public void getIdentity(Identity identity){
        editTextIdentityName.setText(identity.getTitel());
        editTextUsername.setText(identity.getUsername());
        editTextPassword.setText(identity.getPassword());
        editTextKey.setText(identity.getKeypath());
        editTextKeyPassword.setText(identity.getKeypassword());
    }
}

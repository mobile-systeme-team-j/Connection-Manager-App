package de.host.connectionmanagerapp.activityFragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
    private Button btnKey;
    EditText editTextKeyPassword;
    private Identity identity;
    private Bundle arguments;
    private ConnectionViewModel connectionViewModel;
    long id;
    private String keyPath;
    FloatingActionButton delete;
    FloatingActionButton save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identity_detail, container, false);
        // Keyboard adjustResize, um die Veränderung der Floatingbuttons zu verhindern (= überlagert sich jetzt)
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        editTextIdentityName = view.findViewById(R.id.identity_name);
        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        btnKey = view.findViewById(R.id.key);
        editTextKeyPassword = view.findViewById(R.id.key_password);
        delete = view.findViewById(R.id.fabDelete);
        save = view.findViewById(R.id.fabSave);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
        btnKey.setOnClickListener(this);

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
            Uri selectedFile = data.getData(); //The uri with the location of the file
            keyPath = getFileName(selectedFile);
            btnKey.setText(keyPath);
        }
    }
    // Get FileName from Uri: https://stackoverflow.com/a/25005243
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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
        identity.setKeypath(String.valueOf(btnKey.getText()));
        identity.setKeypassword(String.valueOf(editTextKeyPassword.getText()));
        return identity;
    }

    public void getIdentity(Identity identity){
        editTextIdentityName.setText(identity.getTitel());
        editTextUsername.setText(identity.getUsername());
        editTextPassword.setText(identity.getPassword());
        btnKey.setText(identity.getKeypath());
        editTextKeyPassword.setText(identity.getKeypassword());
    }
}

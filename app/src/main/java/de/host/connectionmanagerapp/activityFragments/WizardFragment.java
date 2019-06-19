package de.host.connectionmanagerapp.activityFragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;

import static android.app.Activity.RESULT_OK;

/**
 * @author Manuel Trapp
 * @date 15.02.2019
 * */

public class WizardFragment extends Fragment implements View.OnClickListener {

    private String ip, port,identity,password,key, keyPassword, keyPath;
    private EditText tv_ip,tv_port,tv_user,tv_UserPassword, tv_keyPassword;
    Button btnConnection, btnIdentity, btnSnippet, tv_key;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout. fragment_wizard, container, false);

        FloatingActionButton send = view.findViewById(R.id.fabConnect);
        btnConnection = view.findViewById(R.id.btnConnection);
        btnIdentity = view.findViewById(R.id.btnIdentity);
        btnSnippet = view.findViewById(R.id.btnSnippet);
        tv_key = view.findViewById(R.id.wizard_KeyPath);
        send.setOnClickListener(this);
        btnConnection.setOnClickListener(this);
        btnIdentity.setOnClickListener(this);
        btnSnippet.setOnClickListener(this);
        tv_key.setOnClickListener(this);

        tv_ip = view.findViewById(R.id.wizard_IP);
        tv_port = view.findViewById(R.id.wizard_Port);
        tv_user = view.findViewById(R.id.wizard_Username);
        tv_UserPassword = view.findViewById(R.id.password);
        tv_keyPassword = view.findViewById(R.id.wizard_KeyPass);


        return view;

    }


    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnConnection:
                ((MainActivity)getActivity()).replaceFragment(new ConnectionSelectionFragment());
                break;
            case R.id.btnIdentity:
                ((MainActivity)getActivity()).replaceFragment(new IdentitySelectionFragment());
                break;
            case R.id.btnSnippet:
                ((MainActivity)getActivity()).replaceFragment(new SnippetSelectionFragment());
                break;
            case R.id.btn_Send:
                ((MainActivity)getActivity()).replaceFragment(new SshSessionFragment());
                break;
            case R.id.wizard_KeyPath:
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
                break;
        }
    // gets Strings from EditText-Inputs
        if(tv_ip != null && tv_port != null && tv_user != null && tv_UserPassword!=null && tv_key!=null&& tv_keyPassword != null) {
            ip = tv_ip.getText().toString();
            port = tv_port.getText().toString();
            identity = tv_user.getText().toString();
            password = tv_UserPassword.getText().toString();
            key = tv_key.getText().toString();
            keyPassword = tv_key.getText().toString();
        }else{
            Toast.makeText(getContext(), "Fill all information!", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedFile = data.getData(); //The uri with the location of the file
            keyPath = getFileName(selectedFile);
            tv_key.setText(keyPath);
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
}

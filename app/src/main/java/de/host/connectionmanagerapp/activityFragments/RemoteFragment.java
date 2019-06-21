package de.host.connectionmanagerapp.activityFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.helper.FileUtils;
import de.host.connectionmanagerapp.helper.HideKeyboard;
import de.host.connectionmanagerapp.helper.UriHelper;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

import static android.app.Activity.RESULT_OK;

public class RemoteFragment extends Fragment implements View.OnClickListener{
    private String ip, port,user,password,key, keyPassword, keyPath, keyFileName;
    private long connID;
    private EditText tv_ip,tv_port,tv_user,tv_UserPassword, tv_keyPassword;
    private Button tv_key;
    private ConnectionViewModel connectionViewModel;
    private Connection connection;
    private Identity identity;
    private FloatingActionButton send;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_remote, container, false);
        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        send = view.findViewById(R.id.fabConnectRemoteSSH);

        tv_key = view.findViewById(R.id.wizard_KeyPath);
        tv_key.setOnClickListener(this);
        tv_ip = view.findViewById(R.id.wizard_IP);
        tv_port = view.findViewById(R.id.wizard_Port);
        tv_user = view.findViewById(R.id.wizard_Username);
        tv_UserPassword = view.findViewById(R.id.wizard_UserPass);
        tv_keyPassword = view.findViewById(R.id.wizard_KeyPass);

        send.setOnClickListener(this);

        return view;
    }


    public void onClick(View view){
        switch(view.getId()){
            case R.id.fabConnectRemoteSSH:
                if (setConnection()){
                    ((MainActivity)getActivity()).replaceFragment(SshSessionFragment.newInstance(connID, true));
                    HideKeyboard.hideKeyboard(getContext());
                }
                break;
            case R.id.wizard_KeyPath:
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
                break;

        }
    }

    private boolean areFieldsEmpty(){
        if(!TextUtils.isEmpty(tv_ip.getText().toString()) &&
                !TextUtils.isEmpty(tv_port.getText().toString()) &&
                !TextUtils.isEmpty(tv_user.getText().toString())) {
            ip = tv_ip.getText().toString();
            port = tv_port.getText().toString();
            user = tv_user.getText().toString();
            // Wenn KeyPass gesetzt, setze
            if (!TextUtils.isEmpty(tv_keyPassword.getText().toString())) {
                keyPassword = tv_keyPassword.getText().toString();
            }
            // Wenn Password leer, aber Key gesetzt, return false
            if (TextUtils.isEmpty(tv_UserPassword.getText().toString())
                    && !tv_key.getText().toString().equals("KEY-PATH")){
                return false;
            }
            // Wenn Password gesetzt, setze und return false
            if (!TextUtils.isEmpty(tv_UserPassword.getText().toString())) {
                password = tv_UserPassword.getText().toString();
                return false;
            }
            // Wenn Key-Password gesetzt, aber kein Key, return true
            if (!TextUtils.isEmpty(tv_keyPassword.getText().toString())
                    && tv_key.getText().toString().equals("KEY-PATH")) {
                Toast.makeText(getContext(), "Key-Password without Key not allowed !", Toast.LENGTH_SHORT).show();
                return true;
            }
        }else{
            Toast.makeText(getContext(), "Fill at least IP, Port and User !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedFile = data.getData(); //The uri with the location of the file
            keyFileName = UriHelper.getFileName(selectedFile, getContext());
            keyPath = FileUtils.getPath(getContext(), selectedFile);
            tv_key.setText(keyFileName);
        }
    }

    private boolean setConnection() {
        if (!areFieldsEmpty()) {
            connection = new Connection();
            identity = new Identity();
            connection.setTitel("");
            identity.setTitel("TEMP");
            connection.setHostip(ip);
            connection.setPort(Integer.parseInt(port));
            identity.setPassword(password);
            identity.setUsername(user);
            identity.setKeypassword(keyPassword);
            identity.setKeypath(keyPath);
            // Wenn Identity schon inserted, muss diese geupdatet werden, einfach beides ausführen,
            // weil insert dann schon fehlt schlägt ?
            // Erst löschen, dann neu anlegen
            long id = connectionViewModel.insertIdentityid(identity);
            connectionViewModel.deleteIdentity(id);
            connectionViewModel.insertIdentityid(identity);
            connection.setIdentity_Id("TEMP");
            connID = connectionViewModel.insertConnectionId(connection);

            return true;
        } else {
            return false;
        }
    }
}

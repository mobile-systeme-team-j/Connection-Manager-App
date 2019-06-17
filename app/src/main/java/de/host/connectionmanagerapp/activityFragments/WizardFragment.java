package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.R;

/**
 * @author Manuel Trapp
 * @date 15.02.2019
 * */

public class WizardFragment extends Fragment implements View.OnClickListener {

    private String ip, port,identity,password,key, keyPassword;
    private EditText tv_ip,tv_port,tv_user,tv_UserPassword,tv_key, tv_keyPassword;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout. fragment_wizard, container, false);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(this);

        tv_ip = view.findViewById(R.id.wizard_IP);
        tv_port = view.findViewById(R.id.wizard_Port);
        tv_user = view.findViewById(R.id.wizard_Username);
        tv_UserPassword = view.findViewById(R.id.password);
        tv_key = view.findViewById(R.id.wizard_KeyPath);
        tv_keyPassword = view.findViewById(R.id.wizard_KeyPass);


        return view;

    }


    public void onClick(View view){
    // gets Strings from EditText-Inputs
        ip = tv_ip.getText().toString();
        port = tv_port.getText().toString();
        identity = tv_user.getText().toString();
        password = tv_UserPassword.getText().toString();
        key = tv_key.getText().toString();
        keyPassword = tv_key.getText().toString();
    }
}

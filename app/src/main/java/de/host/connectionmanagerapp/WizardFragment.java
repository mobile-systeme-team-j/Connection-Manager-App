package de.host.connectionmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Manuel Trapp
 * @date 15.02.2019
 * */

public class WizardFragment extends Fragment implements View.OnClickListener {

    String ip, port,identity,password,key;
    EditText tv_ip,tv_port,tv_identity,tv_password,tv_key;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout. fragment_wizard, container, false);

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(this);

        tv_ip = view.findViewById(R.id.ip);
        tv_port = view.findViewById(R.id.port);
        tv_identity = view.findViewById(R.id.identity);
        tv_password = view.findViewById(R.id.password);
        tv_key = view.findViewById(R.id.key);

        return view;

    }


    public void onClick(View view){
    // gets Strings from EditText-Inputs
        ip = tv_ip.getText().toString();
        port = tv_port.getText().toString();
        identity = tv_identity.getText().toString();
        password = tv_password.getText().toString();
        key = tv_key.getText().toString();
    }
}

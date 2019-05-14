package de.host.connectionmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WizardFragment extends Fragment {

    String ip, port,identity,password,key;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout. fragment_wizard, container, false);



    }


    public void onClick(View view){

        ip = ((EditText)view.findViewById(R.id.ip)).getText().toString();
        port = ((EditText)view.findViewById(R.id.port)).getText().toString();
        identity = ((EditText)view.findViewById(R.id.identity)).getText().toString();
        password = ((EditText)view.findViewById(R.id.password)).getText().toString();
        key = ((EditText)view.findViewById(R.id.key)).getText().toString();
    }
}

package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;

/**
 * @author Manuel Trapp
 *
 * */

public class WizardFragment extends Fragment implements View.OnClickListener {

    Button btnConnection, btnSnippet;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout. fragment_wizard, container, false);

        FloatingActionButton send = view.findViewById(R.id.fabConnectRemoteSSH);
        btnConnection = view.findViewById(R.id.btnConnection);
        btnSnippet = view.findViewById(R.id.btnSnippet);

        send.setOnClickListener(this);
        btnConnection.setOnClickListener(this);
        btnSnippet.setOnClickListener(this);
        return view;

    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.btnConnection:
                ((MainActivity)getActivity()).replaceFragment(new ConnectionSelectionFragment());
                break;
            case R.id.btnSnippet:
                ((MainActivity)getActivity()).replaceFragment(new SnippetSelectionFragment());
                break;
            case R.id.btn_Send:
                ((MainActivity)getActivity()).replaceFragment(new SshSessionFragment());
                break;
        }


    }
}

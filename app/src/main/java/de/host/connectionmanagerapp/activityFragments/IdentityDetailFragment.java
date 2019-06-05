package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.R;

public class IdentityDetailFragment extends Fragment {

    EditText editTextIdentityName;
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextKey;
    EditText editTextKeyPassword;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        editTextIdentityName = view.findViewById(R.id.identity_name);
        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        editTextKey = view.findViewById(R.id.key);
        editTextKeyPassword = view.findViewById(R.id.key_password);

        return view;
    }
}

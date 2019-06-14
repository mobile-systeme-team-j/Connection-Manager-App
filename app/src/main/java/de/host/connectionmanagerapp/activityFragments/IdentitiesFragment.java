package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;

/**
 * @author Manuel Trapp
 * @date 14.05.2019
 * */

public class IdentitiesFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identities, container, false);

        FloatingActionButton actionButton = view.findViewById(R.id.fabNewSnippet);
        actionButton.setOnClickListener(this);
        return view;
    }

    public void onClick(View view){
        ((MainActivity)getActivity()).replaceFragment(new IdentityDetailFragment());
    }
}

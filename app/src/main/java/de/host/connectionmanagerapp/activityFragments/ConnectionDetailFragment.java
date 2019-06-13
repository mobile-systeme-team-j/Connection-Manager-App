package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.R;

public class ConnectionDetailFragment extends Fragment implements View.OnClickListener{
    FloatingActionButton delete;
    FloatingActionButton save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabDelete:
                //Method for delete
                break;
            case R.id.fabSave:
                //Method for save
                break;

        }
    }
}

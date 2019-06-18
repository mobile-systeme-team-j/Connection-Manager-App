package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class JobSelectionFragment extends Fragment {

    Bundle arguments;
    long id;
    Job job;
    ConnectionViewModel connectionViewModel;
    List<Job> jobList;
    ListView jobListView;
    Button select;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection_selection, container, false);

        jobList = view.findViewById(R.id.jobList);


        arguments = getArguments();
        if(arguments != null){
            id = arguments.getLong("id");
            jobList = connectionViewModel.getListJobs();
        }

        // android-coding.blogspot.com/2011/09/listview-with-multiple-choice.html

        ArrayAdapter<Job> adapter = new ArrayAdapter<Job>(getContext(),android.R.layout.simple_list_item_single_choice, jobList);

        jobListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        jobListView.setAdapter(adapter);

        select.setOnClickListener(v -> {
            String selected = "";
            int cntChoice = jobListView.getCount();

            SparseBooleanArray sparseBooleanArray = jobListView.getCheckedItemPositions();

            for(int i = 0; i < cntChoice; i++){

                if(sparseBooleanArray.get(i)) {

                    selected += jobListView.getItemAtPosition(i).toString() + "\n";
                }
            }

            Toast.makeText(getContext(),selected,Toast.LENGTH_LONG).show();

        });


        return view;
    }
}

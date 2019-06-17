package de.host.connectionmanagerapp.activityFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import de.host.connectionmanagerapp.DatePickerFragment;
import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.TimePickerFragment;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

/**
 * @author  Jürgen Manuel Trapp
 */

public class JobDetailFragment extends Fragment
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{

    private DatePickerFragment datePickerFragment;
    private TimePickerFragment timePickerFragment;
    public static final int REQUEST_CODE_DatePicker = 11; // Used to identify the date result
    public static final int REQUEST_CODE_TimePicker = 22; // Used to identify the time result
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextJobName;
    Job job;
    Bundle arguments;
    private String selectedDate;
    private String selectedTime;
    long id;
    private ConnectionViewModel connectionViewModel;
    Spinner spinnerConnection,spinnerIdentity, spinnerSnippet;
    List<Connection> connectionList;
    List<Identity> identityList;
    List<Snippet> snippetList;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);

        view.findViewById(R.id.job_name);
        datePickerFragment = new DatePickerFragment();
        timePickerFragment = new TimePickerFragment();

         View Buttons[]={
                 editTextDate = view.findViewById(R.id.date),
                 editTextTime = view.findViewById(R.id.time),
                 editTextJobName = view.findViewById(R.id.job_name),
                 view.findViewById(R.id.btnConnection),
                 view.findViewById(R.id.btnIdentity),
                 view.findViewById(R.id.btnSnippet),
                 view.findViewById(R.id.fabSave),
                 view.findViewById(R.id.fabDelete)
         };

         for (View v : Buttons){
             v.setOnClickListener(this);
         }
/*
         connectionList = connectionViewModel.getAllConnections();
         identityList = connectionViewModel.getAllIdenties();
         snippetList = connectionViewModel.getAllSnippets();

        spinnerConnection = view.findViewById(R.id.spinnerConnection);
        ArrayAdapter<Connection> adapterConnection = new ArrayAdapter<Connection>(this,android.R.layout.simple_spinner_dropdown_item,connectionList);
        spinnerConnection.setAdapter(adapterConnection);

        spinnerIdentity = view.findViewById(R.id.spinnerIdentity);
        ArrayAdapter<Identity> adapterIdentity = new ArrayAdapter<Identity>(this,android.R.layout.simple_spinner_dropdown_item,identityList);
        spinnerConnection.setAdapter(adapterIdentity);

        spinnerSnippet = view.findViewById(R.id.spinnerSnippet);
        ArrayAdapter<Snippet> adapterSnippet = new ArrayAdapter<Snippet>(this,android.R.layout.simple_spinner_dropdown_item,snippetList);
        spinnerConnection.setAdapter(adapterSnippet);
*/

        arguments = getArguments();
        if(arguments !=null){
            id = arguments.getLong("id");
            job = connectionViewModel.getJobs(id);

            editTextJobName.setText(job.getTitel());
            //editTextDate.(job.getJob_date());
            //editTextTime.setText(job.getJobtime());
        }

        return view;
    }


    @Override
    public  void onClick(View view){

        switch(view.getId()){
            case R.id.btnConnection:
                ((MainActivity)getActivity()).replaceFragment(new ConnectionsFragment());
                break;
            case R.id.btnIdentity:
                ((MainActivity)getActivity()).replaceFragment(new IdentitiesFragment());
                break;
            case R.id.btnSnippet:
                ((MainActivity)getActivity()).replaceFragment(new SnippetsFragment());
                break;
            case R.id.date:
                DatePicker();
                break;
            case R.id.time:
                TimePicker();
                break;
            case R.id.fabSave:
                if( arguments != null){
                    try{
                        connectionViewModel.updateJob(Job());
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
                    }
                }else{
                    try{
                        job = new Job();
                       // connectionViewModel.insertJob(Job());
                    }catch (Exception e){
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
                    }
                }
                break;
            case R.id.fabDelete:
                if( arguments != null){
                    connectionViewModel.updateJob(Job());
                }
                break;
        }
    }

    public Job Job(){
        job.setTitel(String.valueOf(editTextJobName.getText()));

        return job;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE_DatePicker && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");
            // set the value of the editText
            editTextDate.setText(selectedDate);
        }

        if (requestCode == REQUEST_CODE_TimePicker && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedTime = data.getStringExtra("selectedTime");
            // set the value of the editText
            editTextTime.setText(selectedTime);
        }

    }

    public void DatePicker(){
        // set the targetFragment to receive the results, specifying the request code
        datePickerFragment.setTargetFragment(JobDetailFragment.this, REQUEST_CODE_DatePicker);
        // show the datePicker
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    public void TimePicker(){
        // set the targetFragment to receive the results, specifying the request code
        timePickerFragment.setTargetFragment(JobDetailFragment.this, REQUEST_CODE_TimePicker);
        // show the datePicker
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

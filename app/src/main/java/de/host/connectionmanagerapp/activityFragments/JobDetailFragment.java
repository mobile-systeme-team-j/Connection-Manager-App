package de.host.connectionmanagerapp.activityFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
        AdapterView.OnItemSelectedListener,
                    TimePickerDialog.OnTimeSetListener,
                    DatePickerDialog.OnDateSetListener {

    private DatePickerFragment datePickerFragment;
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextJobName;
    Job job;
    Bundle arguments;
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

         View Buttons[]={
                 view.findViewById(R.id.date),
                 view.findViewById(R.id.time),
                 view.findViewById(R.id.job_name),
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


    public void DatePicker(){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "DatePickerFragment.TAG");
    }

    public void TimePicker(){
          DialogFragment timePicker = new TimePickerFragment();
          timePicker.show(getFragmentManager(), "time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editTextTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editTextDate.setText(dayOfMonth+"-"+month+"-"+year);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

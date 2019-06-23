package de.host.connectionmanagerapp.activityFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.room.Dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.host.connectionmanagerapp.helper.Connection_id_holder;
import de.host.connectionmanagerapp.helper.DatePickerFragment;
import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.helper.HideKeyboard;
import de.host.connectionmanagerapp.helper.Snippet_id_holder;
import de.host.connectionmanagerapp.helper.TimePickerFragment;
import de.host.connectionmanagerapp.alarm.AlarmRepository;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

/**
 * @author  Philipp Kühling & Mattis Uphoff
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
    int id;
    private ConnectionViewModel connectionViewModel;
    Calendar c = null;
    Calendar timeCalender;
    Calendar dateCalendar;
    Calendar dataCalender;

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
                 view.findViewById(R.id.btnSnippet),
                 view.findViewById(R.id.fabSave),
                 view.findViewById(R.id.fabDelete)
         };

         for (View v : Buttons){
             v.setOnClickListener(this);
         }


        arguments = getArguments();
        if(arguments !=null){
            id = arguments.getInt("id");
            job = connectionViewModel.getJobs(id);
            dataCalender =job.getC();
            String stringDate = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(dataCalender.getTime());
            String stringTime = new SimpleDateFormat("HH:mm", Locale.GERMAN).format(dataCalender.getTime());


            editTextDate.setText(stringDate);
            editTextTime.setText(stringTime);
            editTextJobName.setText(job.getTitel());

        }


        return view;
    }


    @Override
    public  void onClick(View view){
        switch(view.getId()){
            case R.id.btnConnection:
                ((MainActivity)getActivity()).replaceFragment(new ConnectionSelectionFragment());
                break;
            case R.id.btnSnippet:
                ((MainActivity)getActivity()).replaceFragment(new SnippetSelectionFragment());
                break;
            case R.id.date:
                DatePicker();
                break;
            case R.id.time:
                TimePicker();
                break;
            case R.id.fabSave:
                if (TextUtils.isEmpty(editTextJobName.getText().toString())
                        || TextUtils.isEmpty(editTextDate.getText().toString())
                        || TextUtils.isEmpty(editTextTime.getText().toString())) {
                    Toast.makeText(getContext(), "Please add Jobname, Date & Time", Toast.LENGTH_LONG).show();
                    break;
                }
                if( arguments != null){
                    try{
                        String snp = Snippet_id_holder.snippet_Titel;
                        String con = Connection_id_holder.con_Titel;
                        if(createCalender()) {
                            if (con != null && snp != null) {

                                Snippet_id_holder.snippet_Titel = null;
                                Connection_id_holder.con_Titel = null;
                                job.setC(c);
                                connectionViewModel.updateJob(job(snp, con));
                                Toast.makeText(getContext(), "Job updated", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().popBackStack();
                                HideKeyboard.hideKeyboard(getContext());
                            } else {
                                job.setC(c);
                                connectionViewModel.updateJob(job());
                                Toast.makeText(getContext(), "Job updated", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().popBackStack();
                                HideKeyboard.hideKeyboard(getContext());
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(),"timepicker error",Toast.LENGTH_SHORT);
                        }
                        //
                        // te AlarmManager, was als Parameter ? Job selbst ? Dann Connection ziehen ?
                        //createAlarm();
                    }catch (Exception e){
                        Toast.makeText(getContext(),"update error",Toast.LENGTH_SHORT);
                    }
                }else{
                    try{
                        String snp = Snippet_id_holder.snippet_Titel;
                        String con = Connection_id_holder.con_Titel;
                        if(createCalender()) {
                            if (con != null && snp != null) {
                                Snippet_id_holder.snippet_Titel = null;
                                Connection_id_holder.con_Titel = null;
                                job = new Job();
                                job.setC(c);
                                connectionViewModel.insertJob(job(snp, con));
                                Toast.makeText(getContext(), "Job saved", Toast.LENGTH_SHORT).show();
                                //createAlarm();
                                getActivity().getSupportFragmentManager().popBackStack();
                                HideKeyboard.hideKeyboard(getContext());
                            } else {
                                Toast.makeText(getContext(), "Please Select one Snippet and one Connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.fabDelete:
                if( arguments != null){
                    connectionViewModel.deleteJob(id);
                    Toast.makeText(getContext(),"Job deleted",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    public Job job(String snp, String con){
        job.setTitel(editTextJobName.getText().toString());
        job.setConnection_titel(con);
        job.setSnippet_titel(snp);

        //job.setJob_date(selectedDate);
        return job;
    }
    public Job job(){
        job.setTitel(editTextJobName.getText().toString());
        return job;
    }

    public Boolean createCalender(){
        c = Calendar.getInstance();
        if (timeCalender != null && dateCalendar != null ) {
            c.set(Calendar.MINUTE, timeCalender.get(Calendar.MINUTE));
            c.set(Calendar.HOUR_OF_DAY, timeCalender.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
            c.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, dateCalendar.get(Calendar.DAY_OF_MONTH));
            return true;
        }
        else if(timeCalender == null && dateCalendar != null && arguments!= null){

            c.set(Calendar.MINUTE, dataCalender.get(Calendar.MINUTE));
            c.set(Calendar.HOUR_OF_DAY, dataCalender.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
            c.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, dateCalendar.get(Calendar.DAY_OF_MONTH));
            return true;
        }
        else if(timeCalender == null && dateCalendar == null && arguments!= null){
            c.set(Calendar.MINUTE, dataCalender.get(Calendar.MINUTE));
            c.set(Calendar.HOUR_OF_DAY, dateCalendar.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.YEAR, dataCalender.get(Calendar.YEAR));
            c.set(Calendar.MONTH, dataCalender.get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, dataCalender.get(Calendar.DAY_OF_MONTH));
            return true;
        }
        else if(timeCalender != null && dateCalendar == null && arguments!=null) {
            c.set(Calendar.MINUTE, timeCalender.get(Calendar.MINUTE));
            c.set(Calendar.HOUR_OF_DAY, timeCalender.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.YEAR, dataCalender.get(Calendar.YEAR));
            c.set(Calendar.MONTH, dataCalender.get(Calendar.MONTH));
            c.set(Calendar.DAY_OF_MONTH, dataCalender.get(Calendar.DAY_OF_MONTH));
            return true;
        }
        else {
            // Kein Datum & Zeit gesetzt --> Abbruch
            Toast.makeText(getContext(), "Please set time and date!", Toast.LENGTH_LONG);
            return false;
        }

    }

    private void createAlarm() {
        // Create Calendar from Time- und DateCalendar

        // Alarm hinzufügen
        AlarmRepository.addAlarm(c, getContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE_DatePicker && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");
            // set the value of the editText
            editTextDate.setText(selectedDate);
            // Setze DateCalender für AlarmManager
            dateCalendar = datePickerFragment.getDateCalender();
        }

        if (requestCode == REQUEST_CODE_TimePicker && resultCode == Activity.RESULT_OK) {
            // get date from string
            selectedTime = data.getStringExtra("selectedTime");
            // set the value of the editText
            editTextTime.setText(selectedTime);
            // Setze TimeCalender für AlarmManager
            timeCalender = timePickerFragment.getTimeCalender();
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

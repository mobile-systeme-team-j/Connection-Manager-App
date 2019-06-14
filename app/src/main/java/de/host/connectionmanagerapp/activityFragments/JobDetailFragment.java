package de.host.connectionmanagerapp.activityFragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import de.host.connectionmanagerapp.DatePickerFragment;
import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.TimePickerFragment;

/**
 * @author  Jürgen Manuel Trapp
 */

public class JobDetailFragment extends Fragment
        implements View.OnClickListener,
                    TimePickerDialog.OnTimeSetListener,
                    DatePickerDialog.OnDateSetListener {

    private DatePickerFragment datePickerFragment;
    EditText editTextDate;
    EditText editTextTime;
    EditText editTextJobName;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

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
                // Method
                break;
            case R.id.fabDelete:
                // Method
                break;
        }



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
}

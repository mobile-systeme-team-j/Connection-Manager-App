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
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.TimePickerFragment;

/**
 * @author  Jürgen Manuel Trapp
 */

public class JobDetailFragment extends Fragment
        implements View.OnClickListener,
                    TimePickerDialog.OnTimeSetListener,
                    DatePickerDialog.OnDateSetListener {


    EditText editTextDate;
    EditText editTextTime;
    EditText editTextJobName;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

         editTextDate = view.findViewById(R.id.date);
         editTextTime = view.findViewById(R.id.time);
         editTextJobName = view.findViewById(R.id.job_name);

        return view;
    }


    @Override
    public  void onClick(View view){

        switch(view.getId()){
            case R.id.btnConnection:
                startFragment(new ConnectionsFragment());
                break;
            case R.id.btnIdentity:
                startFragment(new IdentitiesFragment());
                break;
            case R.id.btnSnippet:
                startFragment(new SnippetsFragment());
                break;
            case R.id.date:
                startDatePicker();
                break;
            case R.id.time:
                startTimePicker();
                break;
        }



    }


    public void startFragment(Fragment frag){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frag).commit();
    }

    public void startDatePicker(){
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "date picker");
    }

    public void startTimePicker(){
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

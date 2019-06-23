package de.host.connectionmanagerapp.helper;

/**
 * @author Phillip Kühling
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// Zur Anzeige einer Datumsauswahl im Cronjob/Alarm - Activity
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG =
            DatePickerFragment.class.getSimpleName();
    final Calendar c = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // das aktuelle Datum als Voreinstellung nehmen
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // einen DatePickerDialog erzeugen und zurückliefern
        return new DatePickerDialog(getActivity(),
                DatePickerFragment.this, year, month, day);
    }

    @Override
    // called when a date has been selected
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE,0);
        currentDate.set(Calendar.SECOND,0);
        currentDate.set(Calendar.MILLISECOND,0);

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        if(c.after(currentDate)){
            String selectedDate = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(c.getTime());

            Log.d(TAG, "onDateSet: " + selectedDate);
            // send date back to the target fragment
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    Activity.RESULT_OK,
                    new Intent().putExtra("selectedDate", selectedDate)
            );
        }else {
            Toast.makeText(getContext(),"Can't go back in time", Toast.LENGTH_SHORT).show();
        }
    }

    public Calendar getDateCalender (){
        return c;
    }
}
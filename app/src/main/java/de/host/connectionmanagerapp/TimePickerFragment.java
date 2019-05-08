package de.host.connectionmanagerapp;

/**
 * @author Phillip Kühling
 * */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

// Zur Anzeige der Zeit in Cornjob/Alarm - Activity
public class TimePickerFragment extends DialogFragment {

    public static final String TAG =
            TimePickerFragment.class.getSimpleName();

    private TimePickerDialog.OnTimeSetListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimePickerDialog.OnTimeSetListener) {
            listener = (TimePickerDialog.OnTimeSetListener) context;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // aktuelle Uhrzeit als Voreinstellung
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),
                listener, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
package de.host.connectionmanagerapp.ssh;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import de.host.connectionmanagerapp.alarm.AlarmRepository;


public class SshService extends IntentService {

    public SshService() {
        super("SshService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (AlarmRepository.manager == null) {
            AlarmRepository.initSshAlarmManager(getApplicationContext());
        }
        // get PendingIntent-ID (unique Broadcast-ID) from Alarm
        int id = intent.getIntExtra("REQUESTCODE",-1);
        // Starte SSH-Connection
        // Get Connection via Snippet and calTime from Snippet
        long calTime = AlarmRepository.getCalendarTime(id);


    }
}

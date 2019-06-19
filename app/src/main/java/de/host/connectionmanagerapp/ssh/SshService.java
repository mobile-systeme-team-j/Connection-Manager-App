package de.host.connectionmanagerapp.ssh;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import de.host.connectionmanagerapp.alarm.AlarmRepository;


public class SshService extends IntentService {

    public SshService(Context context) {
        super("SshService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AlarmRepository.initSshAlarmManager(getApplicationContext());
    }
}

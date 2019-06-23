package de.host.connectionmanagerapp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import de.host.connectionmanagerapp.ssh.SshService;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Auszuführende Aktionen, wenn Alarm empfangen wird
        // Verbindung zu SSH-Server aufbauen und Befehl senden, also Service starten
        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
        // Restart the Alarm here after reboot
        // Get called PendingIntent-ID

        int id = intent.getIntExtra("REQUESTCODE",-1);
        // Get Job and Connection via gson
        String job = intent.getStringExtra("jsonJob");
        String connection = intent.getStringExtra("jsonConnection");
        String identity = intent.getStringExtra("jsonIdentity");
        String snippetCmd = intent.getStringExtra("snippetCmd");
        // Give Intent-ID to ServiceIntent
        Intent serviceIntent = new Intent(context, SshService.class);
        serviceIntent.putExtra("REQUESTCODE", id);
        serviceIntent.putExtra("jsonJob", job);
        serviceIntent.putExtra("jsonConnection", connection);
        serviceIntent.putExtra("jsonIdentity", identity);
        serviceIntent.putExtra("snippetCmd", snippetCmd);
        context.startService(serviceIntent);
        //  }else {
        //  Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
        // }

    }
}

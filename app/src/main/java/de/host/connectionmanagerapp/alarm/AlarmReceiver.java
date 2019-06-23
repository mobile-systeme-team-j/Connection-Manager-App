package de.host.connectionmanagerapp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import de.host.connectionmanagerapp.ssh.SshService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Auszuführende Aktionen, wenn Alarm empfangen wird
        // Verbindung zu SSH-Server aufbauen und Befehl senden, also Service starten
        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Restart the Alarm here after reboot
            // Get called PendingIntent-ID
            int id = intent.getIntExtra("REQUESTCODE",-1);
            // Give Intent-ID to ServiceIntent
            Intent serviceIntent = new Intent(context, SshService.class);
            serviceIntent.putExtra("REQUESTCODE", id);
            context.startService(serviceIntent);
      //  }else {
          //  Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
       // }

    }
}

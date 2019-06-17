package de.host.connectionmanagerapp.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Auszuführende Aktionen, wenn Alarm empfangen wird
        // Verbindung zu SSH-Server aufbauen und Befehl senden, also Service starten
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Restart the Alarm here after reboot
        }

    }
}

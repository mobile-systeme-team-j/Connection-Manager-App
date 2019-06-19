package de.host.connectionmanagerapp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

// Für mehrere Alarme können für die Identifizierung unterschiedliche Broadcast_IDs
// verwendet werden, die z.B. die Momentane System-Zeit wiederspiegeln
// In einer Map für die Calender Zeit währen diese an einen Job gebunden und der Alarm
// könnte auch wieder beendet werden, wenn ein Job gelöscht wird
// Die Map sollte gespeichert werden um nach Reboot die Alarme wiederherzustellen

public class AlarmRepository {
    private static AlarmManager manager;
    private static HashMap alarmJobs = new HashMap<Integer, Long>();

     // Methode zur Initialisierung des Alarms für den SSH-Service
    public static void initSshAlarmManager(Context context) {
        manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        // In While-Schleife Map durchgehen
        Iterator it = alarmJobs.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)pair.getKey(), intent, 0);
            manager.setExact(AlarmManager.RTC_WAKEUP, (long)pair.getValue(), pendingIntent);
        }
    }

    public static void addAlarm (Calendar c, Context context){
        manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int _id = getUniqueBroadcastID();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, intent, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        // Alarm in HashMap und SharedPreferences abspeichern
        alarmJobs.put(_id, c.getTimeInMillis());
    }

    private static int getUniqueBroadcastID(){
        final int _id = (int) System.currentTimeMillis();
        return _id;
    }

    private static void saveHashMap(Context context){
        // Use gson library to save HashMap
        SharedPreferences mPrefs = context.getSharedPreferences("AlarmRepository",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarmJobs);
        prefsEditor.putString("alarmJobs", json);
        prefsEditor.commit();
    }

    private static HashMap getHashMap(Context context) {
        Gson gson = new Gson();
        SharedPreferences mPrefs = context.getSharedPreferences("AlarmRepository",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = mPrefs.getString("alarmJobs", "");
        return gson.fromJson(json, HashMap.class);
    }

}

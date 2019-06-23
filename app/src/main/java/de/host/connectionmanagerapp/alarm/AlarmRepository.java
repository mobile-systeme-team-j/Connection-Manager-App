package de.host.connectionmanagerapp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Job;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

// Für mehrere Alarme können für die Identifizierung unterschiedliche Broadcast_IDs
// verwendet werden, die z.B. die Momentane System-Zeit wiederspiegeln
// In einer Map für die Calender Zeit währen diese an einen Job gebunden und der Alarm
// könnte auch wieder beendet werden, wenn ein Job gelöscht wird
// Die Map sollte gespeichert werden um nach Reboot die Alarme wiederherzustellen

public class AlarmRepository {
    public static AlarmManager manager;
    private static HashMap<Integer, Long> alarmJobs = new HashMap<Integer, Long>();
    private static long connID;

    public static void addAlarm (Calendar c, Context context, int _id, Job job, Connection connection, Identity identity, String cmd){
        // HashMap aktualisieren
        alarmJobs = getHashMap(context);
        manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        // Alarm bereits erstellt ?
        if (isAlarmCreated(_id)) {
            // Lösche vorhandenen Alarm und erstelle ihn neu
            deleteAlarm(_id, context, job, connection, identity, cmd);
            createAlarm(c, context, _id, job, connection, identity, cmd);
        } else {
            createAlarm(c, context, _id, job, connection, identity, cmd);
        }
    }

    public static void createAlarm(Calendar c, Context context, int _id, Job job, Connection connection, Identity identity, String cmd) {
        // HashMap aktualisieren
        alarmJobs = getHashMap(context);
        Intent intent = new Intent(context, AlarmReceiver.class);
        // Job und Connection via Gson als Json convertieren und in Intent speichern
        Gson gson = new Gson();
        String jsonJob = gson.toJson(job);
        String jsonConnection = gson.toJson(connection);
        String jsonIdentity = gson.toJson(identity);
        intent.putExtra("jsonJob", jsonJob);
        intent.putExtra("jsonConnection", jsonConnection);
        intent.putExtra("jsonIdentity", jsonIdentity);
        intent.putExtra("snippetCmd", cmd);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)_id,
                intent, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        // Alarm in HashMap und SharedPreferences abspeichern
        alarmJobs.put(_id, c.getTimeInMillis());
        // HashMap speichern
        saveHashMap(context);
    }

    public static boolean isAlarmCreated(int id){
        Iterator it = alarmJobs.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if ((int) pair.getKey() == id) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    public static void deleteAlarm(int id, Context context, Job job,Connection connection, Identity identity, String cmd) {
        // HashMap aktualisieren
        alarmJobs = getHashMap(context);
        manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        Gson gson = new Gson();
        String jsonJob = gson.toJson(job);
        String jsonConnection = gson.toJson(connection);
        String jsonIdentity = gson.toJson(identity);
        intent.putExtra("jsonJob", jsonJob);
        intent.putExtra("jsonConnection", jsonConnection);
        intent.putExtra("jsonIdentity", jsonIdentity);
        intent.putExtra("snippetCmd", cmd);
        Iterator it = alarmJobs.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if ((int) pair.getKey() == id) {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)pair.getKey(), intent, 0);
                manager.cancel(pendingIntent);
                alarmJobs.remove(pair.getKey());
            }
        }
        // HashMap speichern
        saveHashMap(context);
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

    private static HashMap<Integer,Long> getHashMap(Context context) {
        Gson gson = new Gson();
        SharedPreferences mPrefs = context.getSharedPreferences("AlarmRepository",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String json = mPrefs.getString("alarmJobs", "");
        Type type = new TypeToken<HashMap<Integer, Long>>(){}.getType();
        HashMap<Integer, Long> map = (HashMap<Integer, Long>) gson.fromJson(json, type);
        if (map == null) {
            return alarmJobs;
        } else {
            return map;
        }
    }

    public static long getCalendarTime(long id){
        long calTime = -1L;
        Iterator it = alarmJobs.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            if ((int)pair.getKey() == id) {
                calTime = (long) pair.getValue();
            }
        }
        return calTime;
    }

    public static void setConnID(long conn) {
        connID = conn;
    }

}

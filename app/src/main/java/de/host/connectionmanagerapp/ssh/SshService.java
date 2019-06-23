package de.host.connectionmanagerapp.ssh;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.alarm.AlarmRepository;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.database.Snippet;


public class SshService extends IntentService {

    public SshService() {
        super("SshService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String result = "";
        // job and connection Json-String from intent and convert to Object via gson
        String jsonJob = intent.getStringExtra("jsonJob");
        String jsonConnection = intent.getStringExtra("jsonConnection");
        String jsonIdentity = intent.getStringExtra("jsonIdentity");
        String cmd = intent.getStringExtra("snippetCmd");
        Gson gson = new Gson();
        Job job = (Job) gson.fromJson(jsonJob, Job.class);
        Connection connection = (Connection) gson.fromJson(jsonConnection, Connection.class);
        Identity identity = (Identity) gson.fromJson(jsonIdentity, Identity.class);

        // Starte SSH-Connection
        SshConfig config = new SshConfig(connection.getHostip(), connection.getPort(), identity.getUsername());
        config = config.useHostKey(false);
        if (!TextUtils.isEmpty(identity.getPassword())) {
            config = config.usePassword(identity.getPassword());
        }
        if (!TextUtils.isEmpty(identity.getKeypath())) {
            config = config.useKey(identity.getKeypath());
        }
        if (!TextUtils.isEmpty(identity.getKeypassword())) {
            config = config.useKeyPass(identity.getKeypassword());
        }

        SshConn conn = new SshConn(config, new SSHClient());
        try {
            conn.openConnection();
        } catch (Exception e) {
            result = e.getMessage();
            Log.e("SshService", e.getMessage());
        }

        try {
            result = conn.sendCommand(cmd);
        } catch (Exception e) {
            result = e.getMessage();
            Log.e("SshService", e.getMessage());
        }
        // Create Notification
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this,"ch01");
        }else{
            builder = new Notification.Builder(this);
        }
        // Eigenschaften setzen
        builder.setContentTitle("SSH Job Result");
        if (!TextUtils.isEmpty(result)) {
            builder.setContentText("Result des Jobs " + job.getTitel() + ": " + result);
        } else {
            builder.setContentText("Result des Jobs " + job.getTitel() + ": " + "Befehl erfolgreich ausgeführt.");
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);
        // AutoCancel setzen --> Notification wird nach Click auto. entfernt
        builder.setAutoCancel(true);

        // Absenden
        NotificationManager mng = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        // Ab Android 8 sind zwingend Notification Channel notwendig!, siehe:
        // https://stackoverflow.com/a/43093261
        if (mng != null) {
            // Kanal anlegen, wenn Version Code >= 8
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("ch01",
                        getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT);
                mng.createNotificationChannel(channel);
            }
            mng.notify(0,builder.build());
        }
        // Connection close
        try {
            conn.closeConnection();
        } catch (Exception e) {
            Log.e("SSH dissconnect: ", e.getMessage());
        }
    }
}

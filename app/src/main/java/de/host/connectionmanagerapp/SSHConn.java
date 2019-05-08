package de.host.connectionmanagerapp;

import android.text.TextUtils;
import android.util.Log;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.ConsoleKnownHostsVerifier;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.Console;
import java.io.File;
import java.io.IOException;

// Klasse für die Einbindung der sshj-lib
public class SSHConn {

    public static final String TAG = SSHConn.class.getSimpleName();
    public static final Console con = System.console();
    private final SSHConfig config;
    private final SSHClient client;

    // Konstruktor
    public SSHConn(SSHConfig config, SSHClient client) {
        this.config = config;
        this.client = client;
    }

    public void openConnection () {
        try {
            // Erst evtl. vorhandene KnownHosts z.B. aus ~/.ssh/known_hosts laden
            // Autodetect-Methode verwenden und dem SSHClient hinzufügen
            //final File khFile = new File(OpenSSHKnownHosts.detectSSHDir(), "known_hosts");
            //client.addHostKeyVerifier(new ConsoleKnownHostsVerifier(khFile, con));
            // Verbinden
            client.connect(config.getHost(), config.getPort());
            // Wenn KeyPath vorhanden, dann authentifiziere mit Key, sonst mit Password
            // TODO: Evtl. KeyProvider nutzen, statt Pfad

            if (!config.isHostKeyNeeded()) {
                client.addHostKeyVerifier(new PromiscuousVerifier());
            }
            // Authentifizierung, je nach verfügbaren Eigenschaften
            if (!TextUtils.isEmpty(config.getKeyPath().toString())) {
                // Authentifiziere mit KeyFile
                client.authPublickey(config.getUser(), config.getKeyPath().toString());
            } else if (!TextUtils.isEmpty(config.getPassword())) {
                // Authentifiziere mit Passwort
                client.authPassword(config.getUser(), config.getPassword());
            } else {
                // Authentifiziere mit leerem Passwort
                client.authPassword(config.getUser(), "");
            }

        } catch (IOException e) {
            Log.e(TAG, "SSHConn: Error openConnection().", e);
        }
    }

    private void startShell () {

    }

    private void sendCommands () {

    }

    public SSHConfig getConfig() {
        return config;
    }
}

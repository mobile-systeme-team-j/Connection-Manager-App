package de.host.connectionmanagerapp.ssh;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.Security;
import java.util.concurrent.TimeUnit;

// Klasse für die Einbindung der sshj-lib
public class SshConn {

    public static final String TAG = SshConn.class.getSimpleName();
    // public static final Console con = System.console();
    private final SshConfig config;
    private final SSHClient client;
    private Context context;

    // Konstruktor
    public SshConn(SshConfig config, SSHClient client, Context context) {
        this.config = config;
        this.client = client;
        this.context = context;
    }

    public void closeConnection() {
        try {
            client.disconnect();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(context, "Disconnect error!", Toast.LENGTH_LONG).show();
        }
    }

    public void openConnection () {
        try {
            // Bouncy Castle Security Provider registrieren (u.a. für ECDSA benötigt)
            // Android liefert nur abgespeckte Version mit
            Security.removeProvider("BC");
            Security.addProvider(new BouncyCastleProvider());
            //SecurityUtils.registerSecurityProvider("org.spongycastle.jce.provider.BouncyCastleProvider");
            // Erst evtl. vorhandene KnownHosts z.B. aus ~/.ssh/known_hosts laden
            // Autodetect-Methode verwenden und dem SSHClient hinzufügen
            //final File khFile = new File(OpenSSHKnownHosts.detectSSHDir(), "known_hosts");
            //client.addHostKeyVerifier(new ConsoleKnownHostsVerifier(khFile, con));
            // Verbinden

            // Akzeptiere alle HostKeys mit PromiscuousVerifier
            if (!config.isHostKeyNeeded()) {
                client.addHostKeyVerifier(new PromiscuousVerifier());
            }

            client.connect(config.getHost(), config.getPort());

            // Authentifizierung, je nach verfügbaren Eigenschaften
            if (config.getKeyPath() != null) {
                if (config.getKeyPass() != null) {
                    KeyProvider keys = client.loadKeys(config.getKeyPath(), config.getKeyPass());
                    client.authPublickey(config.getUser(), keys);
                } else {
                    // Authentifiziere mit KeyFile
                    client.authPublickey(config.getUser(), config.getKeyPath());
                }
            } else if (!TextUtils.isEmpty(config.getPassword())) {
                // Authentifiziere mit Passwort
                client.authPassword(config.getUser(), config.getPassword());

            } else {
                // Authentifiziere mit leerem Passwort
                client.authPassword(config.getUser(), "");
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(context, "SshConn: Error openConnection().", Toast.LENGTH_LONG).show();
        }
    }

    public String sendCommand (String command) {
        String response = "";

        try {
            Session session = client.startSession();
            Session.Command cmd = session.exec(command);
            response = (IOUtils.readFully(cmd.getInputStream()).toString());
            cmd.join(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(context, "Error sending command.", Toast.LENGTH_LONG).show();
        }
        return response;
    }

    public SshConfig getConfig() {
        return config;
    }

    public SSHClient getClient() {
        return client;
    }
}

package de.host.connectionmanagerapp;

import android.text.TextUtils;
import android.util.Log;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.common.LoggerFactory;
import net.schmizz.sshj.common.StreamCopier;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Shell;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.Security;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static net.sf.expectit.filter.Filters.removeColors;
import static net.sf.expectit.filter.Filters.removeNonPrintable;
import static net.sf.expectit.matcher.Matchers.contains;

// Klasse für die Einbindung der sshj-lib
public class SSHConn {

    public static final String TAG = SSHConn.class.getSimpleName();
    // public static final Console con = System.console();
    private final SSHConfig config;
    private final SSHClient client;

    // Konstruktor
    public SSHConn(SSHConfig config, SSHClient client) {
        this.config = config;
        this.client = client;
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

            // Wenn KeyPath vorhanden, dann authentifiziere mit Key, sonst mit Password
            // TODO: Evtl. KeyProvider nutzen, statt Pfad

            // Akzeptiere alle HostKeys mit PrmiscuousVerifier
            if (!config.isHostKeyNeeded()) {
                client.addHostKeyVerifier(new PromiscuousVerifier());
            }
            client.connect(config.getHost(), config.getPort());

            // Authentifizierung, je nach verfügbaren Eigenschaften
            if (config.getKeyPath() != null) {
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

    // Shell with simple PTY
    // https://github.com/hierynomus/sshj/blob/master/examples/src/main/java/net/schmizz/sshj/examples/RudimentaryPTY.java
    public void startShell () {
        try {
            Session session = client.startSession();
            try {
                session.allocateDefaultPTY();
                Shell shell = session.startShell();

                new StreamCopier(shell.getInputStream(), System.out, LoggerFactory.DEFAULT)
                        .bufSize(shell.getLocalMaxPacketSize())
                        .spawn("stdout");

                new StreamCopier(shell.getErrorStream(), System.err, LoggerFactory.DEFAULT)
                        .bufSize(shell.getLocalMaxPacketSize())
                        .spawn("stderr");

                // Now make System.in act as stdin. To exit, hit Ctrl+D (since that results in an EOF on System.in)
                // This is kinda messy because java only allows console input after you hit return
                // But this is just an example... a GUI app could implement a proper PTY
                new StreamCopier(System.in, shell.getOutputStream(), LoggerFactory.DEFAULT)
                        .bufSize(shell.getRemoteMaxPacketSize())
                        .copy();

            } catch (IOException e) {
                Log.e(TAG, "startShell(): IO error.", e);
            } finally {
                    session.close();
            }
        } catch (ConnectionException e) {
            Log.e(TAG, "startShell(): Connection error.", e);
        } catch (TransportException e) {
            Log.e(TAG, "startShell(): Transport error.", e);
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                Log.e(TAG, "startShell(): IO-Disconnect error.", e);
            }
        }
    }

    public String sendCommand (String command) {
        String response = "";

        try {
            Session session = client.startSession();
            Session.Command cmd = session.exec(command);
            response = (IOUtils.readFully(cmd.getInputStream()).toString());
            cmd.join(5, TimeUnit.SECONDS);
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void createPTY () throws IOException {
        Session session = client.startSession();
        session.allocateDefaultPTY();
        Shell shell  = session.startShell();
        final Expect expect = new ExpectBuilder()
                .withOutput(shell.getOutputStream())
                .withInputs(shell.getInputStream(), shell.getErrorStream())
                .withEchoInput(System.out)
                .withEchoOutput(System.err)
                .withInputFilters(removeColors(), removeNonPrintable())
                .withExceptionOnFailure()
                .build();
        try {
            // Terminal like Eingabe von der Console
            final Scanner scanner = new Scanner(System.in);

            while (true) {
                expect.withTimeout(30, TimeUnit.SECONDS)
                    .sendLine(scanner.nextLine());
                String result = expect.expect(contains("$")).getBefore();
            }

        } finally {
            expect.close();
            session.close();
            client.close();
        }
    }

    public SSHConfig getConfig() {
        return config;
    }
}

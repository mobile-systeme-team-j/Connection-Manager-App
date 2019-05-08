package de.host.connectionmanagerapp;

import java.nio.file.Path;

/**
 * @author Phillip Kühling
 * */

// Angelehnt an https://www.programcreek.com/java-api-examples/?code=bugminer/bugminer/bugminer-master/bugminer-server/src/main/java/de/unistuttgart/iste/rss/bugminer/computing/SshConnection.java#
public class SSHConfig {
    // Instanzvariablen
    private String user;
    private String password;
    private String host;
    private int port;
    private Path keyPath;
    private boolean hostKeyNeeded = true;
    private static final int DEFAULT_PORT = 22;

    // Default privater, leerer Konstruktor mit mehreren public Konstruktoren, je nach Parameter
    private SSHConfig () {

    }

    // Konstruktor mit Port, User und Host
    public SSHConfig(String host, int port, String user) {
        this.host = host;
        this.port = port;
        this.user = user;
    }

    // Konstruktor mit DEFAULT_PORT
    public SSHConfig (String host, String user) {
        this(host, DEFAULT_PORT, user);
    }

    // Methode, um SSHConfig um Passwort zu ergänzen.
    public SSHConfig usePassword (String password) {
        SSHConfig temp = copy();
        temp.password = password;
        return temp;
    }

    // Methode, um SSHConfig um KeyFile zu ergänzen.
    public SSHConfig useKey (Path keyPath) {
        SSHConfig temp = copy();
        temp.keyPath = keyPath;
        return temp;
    }

    // Methode, um SSHConfig um HostKey-Verifizierung zu ergänzen.
    public SSHConfig useHostKey (boolean key) {
        SSHConfig temp = copy();
        temp.hostKeyNeeded = key;
        return temp;
    }

    // Methode um Config zu kopieren
    private SSHConfig copy() {
        SSHConfig temp = new SSHConfig(host, port, user);
        temp.password = password;
        temp.keyPath = keyPath;
        temp.hostKeyNeeded = hostKeyNeeded;
        return temp;
    }

    // Getter
    public String getHost() {
        return host;
    }

    public int getPort() {
        if (port == 0) {
            return DEFAULT_PORT;
        }
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Path getKeyPath() {
        return keyPath;
    }

    public boolean isHostKeyNeeded() {
        return hostKeyNeeded;
    }
}

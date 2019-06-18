package de.host.connectionmanagerapp.ssh;

/**
 * @author Phillip Kühling
 * */

// Angelehnt an https://www.programcreek.com/java-api-examples/?code=bugminer/bugminer/bugminer-master/bugminer-server/src/main/java/de/unistuttgart/iste/rss/bugminer/computing/SshConnection.java#
public class SshConfig {
    // Instanzvariablen
    private String user;
    private String password;
    private String host;
    private String keyPass;
    private int port;
    private String keyPath;
    private boolean hostKeyNeeded = true;
    private static final int DEFAULT_PORT = 22;

    // Default privater, leerer Konstruktor mit mehreren public Konstruktoren, je nach Parameter
    private SshConfig() {

    }

    // Konstruktor mit Port, User und Host
    public SshConfig(String host, int port, String user) {
        this.host = host;
        this.port = port;
        this.user = user;
    }

    // Konstruktor mit DEFAULT_PORT
    public SshConfig(String host, String user) {
        this(host, DEFAULT_PORT, user);
    }

    // Methode, um SshConfig um Passwort zu ergänzen.
    public SshConfig usePassword (String password) {
        SshConfig temp = copy();
        temp.password = password;
        return temp;
    }

    // Methode, um SshConfig um KeyFile zu ergänzen.
    public SshConfig useKey (String keyPath) {
        SshConfig temp = copy();
        temp.keyPath = keyPath;
        return temp;
    }

    // Methode, um SshConfig um verschlüsseltes KeyFile mit Passwort zu ergänzen.
    public SshConfig useKeyPass (String pass) {
        SshConfig temp = copy();
        temp.keyPass = pass;
        return temp;
    }

    // Methode, um SshConfig um HostKey-Verifizierung zu ergänzen.
    public SshConfig useHostKey (boolean key) {
        SshConfig temp = copy();
        temp.hostKeyNeeded = key;
        return temp;
    }

    // Methode um Config zu kopieren
    private SshConfig copy() {
        SshConfig temp = new SshConfig(host, port, user);
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

    public String getKeyPath() {
        return keyPath;
    }

    public boolean isHostKeyNeeded() {
        return hostKeyNeeded;
    }

    public String getKeyPass() {
        return keyPass;
    }
}

package de.host.connectionmanagerapp.activityFragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.LoggerFactory;
import net.schmizz.sshj.common.StreamCopier;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.TransportException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.ssh.SshConfig;
import de.host.connectionmanagerapp.ssh.SshConn;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;


public class SshSessionFragment extends Fragment {

    public static final String TAG = SshSessionFragment.class.getSimpleName();

    private long connection_ID;
    private long identityID;
    private TextView terminal;
    private EditText command;
    private ImageButton send;
    private ByteArrayOutputStream baos;
    private PrintStream ps;
    private boolean firstConn, toBeDeleted;
    private Future future;
    private ConnectionViewModel connectionViewModel;
    Connection connection;
    private OnFragmentInteractionListener mListener;

    public SshSessionFragment() {
        // Required empty public constructor
    }


    // Konstruktor mit connection_ID Parameter für Verbindungsaufbau
    public static SshSessionFragment newInstance(long connection_ID, boolean toBeDeleted) {
        SshSessionFragment fragment = new SshSessionFragment();
        Bundle args = new Bundle();
        args.putLong("connection_ID", connection_ID);
        args.putBoolean("toBeDeleted", toBeDeleted);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            connection_ID = getArguments().getLong("connection_ID");
            toBeDeleted = getArguments().getBoolean("toBeDeleted");
        }

        // Non-Ui Variablen initialisieren
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        System.setOut(ps);
        firstConn = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ssh_session, container, false);

        // Ui initialisieren
        terminal = view.findViewById(R.id.tv_Terminal);
        // Autoscroll aktivieren
        terminal.setMovementMethod(new ScrollingMovementMethod());
        command = view.findViewById(R.id.et_Command);
        send = view.findViewById(R.id.btn_Send);
        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        // Wirft Error: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
        connection = connectionViewModel.getConnection(connection_ID);
        
        // Soft-Keyboard automatisch anzeigen, notwendige Ui-Elemente resizen
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Baue Verbindung zu Server auf
        future = connectToServer(connection_ID);

        send.setOnClickListener((v) -> {
            // Wenn ET nicht empty, schicke Befehl an AsyncTask
            if (!TextUtils.isEmpty(command.getText().toString())) {
                try {
                    SshConn conn = (SshConn) future.get();
                    Object[] objects = new Object[2];
                    objects[0] = conn;
                    objects[1] = command.getText().toString();
                    new SshAsyncTask().execute(objects);
                } catch (Exception e) {
                    e.printStackTrace();
                    createToast(e.getMessage());
                }
            }
        });

        // Enter-/ Return Taste des Soft-Keyboards abfangen zur "click"-Ausführung auf Button
        command.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Wenn return gedrückt, also new line
                if (before == 0 && count == 1 && s.charAt(start) == '\n') {
                    // Führe Click aus
                    send.performClick();
                    //remove the <enter>
                    command.getText().replace(start, start + 1, "");
                }
            }
            
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/


    @Override
    public void onStop() {
        super.onStop();
        // Verbindung beenden über Conn
        // In neuem Thread, da wieder Netzwerk
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SshConn conn = (SshConn) future.get();
                    conn.getClient().disconnect();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

        });
        thread.start();

        // Wenn toBeDeleted gesetzt, dann Connection und Identity aus DB löschen
        if (toBeDeleted) {
            connectionViewModel.deleteConnection(connection_ID);
            connectionViewModel.deleteIdentity(identityID);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private Future connectToServer(long connection_ID) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<SshConn> future = executorService.submit(new Callable<SshConn>() {
            @Override
            public SshConn call() {
                Identity identity = connectionViewModel.getIdentityFromTitel(connection.getIdentity_Id());
                identityID = identity.getIdentiy_id();
                String host = connection.getHostip();
                String password = identity.getPassword();
                String username = identity.getUsername();
                String keyPass = identity.getKeypassword();
                //TODO DB: Pfad für HostKey String hostKey = connection.getHostKey();
                int port = connection.getPort();
                String keyPath = "";
                if (!TextUtils.isEmpty(identity.getKeypath())) {
                        keyPath = identity.getKeypath();

                }

                // Setup Connection
                SshConfig config;
                if (port != 0) {
                    config = new SshConfig(host, port, username);
                } else {
                    config = new SshConfig(host, username);
                }

                if (!TextUtils.isEmpty(keyPath)) {
                    // Request READ_EXTERNAL_STORAGE Permission if not already set
                    int permissionCheck = ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        // Permission granted
                    } else {
                        requestPermissions(new String []{Manifest.permission.READ_EXTERNAL_STORAGE},99);
                    }
                    config = config.useKey(keyPath);
                }
                if (!TextUtils.isEmpty(keyPass)) {
                    config = config.useKeyPass(keyPass);
                }
                if (!TextUtils.isEmpty(password)) {
                    config = config.usePassword(password);
                }

                config = config.useHostKey(false);

                SshConn conn = new SshConn(config, new SSHClient());
                try {
                    conn.openConnection();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    createToast(e.getMessage());
                }
                return conn;
            }
        });
        return future;
    }

    // AsyncTask zur Ausführung des Befehls in der Shell
    public class SshAsyncTask extends AsyncTask<Object, Integer, String>  {

        @Override
        protected String doInBackground(Object[] objects) {

            SshConn conn = (SshConn) objects[0];
            String command = (String) objects[1];

            SSHClient client = conn.getClient();

            try {
                Session session = client.startSession();
                session.allocateDefaultPTY();
                Session.Shell shell = session.startShell();

                InputStream in = shell.getInputStream();
                InputStream err = shell.getErrorStream();

                new StreamCopier(in, System.out, LoggerFactory.DEFAULT)
                        .bufSize(session.getLocalMaxPacketSize())
                        .spawn("stdout");

                new StreamCopier(err, System.out, LoggerFactory.DEFAULT)
                        .bufSize(session.getLocalMaxPacketSize())
                        .spawn("stderr");
                // modt und co. anzeigen bei erster Verbindung
                if (!firstConn) {
                Thread.sleep(200);
                    terminal.append(baos.toString());
                    firstConn = true;
                }

                // Befehl senden
                String result = "";
                result = conn.sendCommand(command);
                // Befehl an Outputstream hängen
                baos.write((command).getBytes());

                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                createToast(e.getMessage());
            }

            /*
            // Befehl direkt über Outputstream an Shell senden
            try {
                // Befehl an Shell senden und close
                out.write((cmd + "\n").getBytes());
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            } */
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                // Outputstream mit Result füllen
                baos.write(s.getBytes());
                terminal.setText(baos.toString());
                // Append notwendig, da sonst Autoscroll nicht funktioniert
                terminal.append(" ");
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                createToast(e.getMessage());
            }
        }
    }
    private void createToast(String message) {
        getActivity().runOnUiThread(() ->
                Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show());
    }
}

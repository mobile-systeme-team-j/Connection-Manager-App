package de.host.connectionmanagerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import net.schmizz.sshj.SSHClient;

import de.host.connectionmanagerapp.activityFragments.ConnectionsFragment;
import de.host.connectionmanagerapp.activityFragments.HomeFragment;
import de.host.connectionmanagerapp.activityFragments.IdentitiesFragment;
import de.host.connectionmanagerapp.activityFragments.JobsFragment;
import de.host.connectionmanagerapp.activityFragments.SnippetsFragment;
import de.host.connectionmanagerapp.activityFragments.WizardFragment;

/**
 * @author Manuel Trapp
 * @date 14.05.2019
 * */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout side_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        side_menu = findViewById(R.id.layout_container);
        NavigationView navigationView= findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, side_menu, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        side_menu.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        //testSSH();

    }


    @Override
    public void onBackPressed(){
        // handles side-menu when Back-Button of Phone is pressed
        if(side_menu.isDrawerOpen(GravityCompat.START)){
            side_menu.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_side, menu);
        return true;
    }

    private void testSSH () {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    SSHConfig config = new SSHConfig("54.37.204.238", "userwp");
                    config = config.usePassword("wFf4]18&");
                    config = config.useHostKey(false);
                    /*
                    SSHConfig config = new SSHConfig("sdf.org", "new");
                    config = config.useHostKey(false);
                    */
                    SSHConn conn = new SSHConn(config, new SSHClient());
                    conn.openConnection();
                    //conn.startShell();
                    //String response = conn.sendCommand("ls -a");
                    //System.out.println(response);
                    conn.createPTY();
                } catch (Exception e) {
                    Log.e(SSHConn.TAG, e.getMessage());
                }
            }
        });
        thread.start();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // handles selected menu option

        switch(menuItem.getItemId()){
            case R.id.menu_home:
                  changeFragment(new HomeFragment());
                break;
            case R.id.menu_wizard:
                changeFragment(new WizardFragment());
                break;
            case R.id.menu_connections:
                changeFragment(new ConnectionsFragment());
                break;
            case R.id.menu_identities:
                changeFragment(new IdentitiesFragment());
                break;
            case R.id.menu_snippets:
                changeFragment(new SnippetsFragment());
                break;
            case R.id.menu_job:
                changeFragment(new JobsFragment());
                break;
         /*   case R.id.menu_settings:
                changeFragment(new SettingsFragment());
                break;
            case R.id.menu_about:
                changeFragment(new AboutFragment());
                break;
           */
        }
        side_menu.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, frag)
                .commit();
    }
}
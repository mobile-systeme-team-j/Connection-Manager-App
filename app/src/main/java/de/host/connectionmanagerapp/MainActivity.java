package de.host.connectionmanagerapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import de.host.connectionmanagerapp.activityFragments.ConnectionsFragment;
import de.host.connectionmanagerapp.activityFragments.HomeFragment;
import de.host.connectionmanagerapp.activityFragments.IdentitiesFragment;
import de.host.connectionmanagerapp.activityFragments.JobsFragment;
import de.host.connectionmanagerapp.activityFragments.RemoteFragment;
import de.host.connectionmanagerapp.activityFragments.SnippetsFragment;
import de.host.connectionmanagerapp.activityFragments.SshSessionFragment;
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
            addFragment(new HomeFragment());
        }
    }

    @Override
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            new AlertDialog.Builder(this)
                    .setMessage("A")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();        }

        // handles side-menu when Back-Button of Phone is pressed
        if(side_menu.isDrawerOpen(GravityCompat.START)){
            side_menu.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // handles selected menu option

        switch(menuItem.getItemId()){
            case R.id.menu_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.menu_wizard:
                replaceFragment(new WizardFragment());
                break;
            case R.id.menu_remote:
                replaceFragment(new RemoteFragment());
                break;
            case R.id.menu_connections:
                replaceFragment(new ConnectionsFragment());
                break;
            case R.id.menu_identities:
                replaceFragment(new IdentitiesFragment());
                break;
            case R.id.menu_snippets:
                replaceFragment(new SnippetsFragment());
                break;
            case R.id.menu_job:
                replaceFragment(new JobsFragment());
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


    public void addFragment(Fragment frag) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, frag)
                    .addToBackStack(null)
                    .commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment frag){
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .addToBackStack(null)
                    .commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
package de.host.connectionmanagerapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import de.host.connectionmanagerapp.activityFragments.WizardFragment;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    private DrawerLayout side_menu;
    private NavigationView navigationView;
    private Toast toast;
    private long lastBackPressTime = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        side_menu = findViewById(R.id.layout_container);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.menu_home);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, side_menu, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        side_menu.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            addFragment(new HomeFragment());
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);
    }

    @Override
    public void onBackPressed() {


        //https://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity
        //Firefog, 26.12.2018
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
                toast = Toast.makeText(this, "Press back again to close this app", Toast.LENGTH_SHORT);
                toast.show();
                this.lastBackPressTime = System.currentTimeMillis();

            }else{
                if (toast != null) {
                    toast.cancel();
                }
                finish();

            }

        }else{
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
        }

        // handles side-menu when Back-Button of Phone is pressed
        if (side_menu.isDrawerOpen(GravityCompat.START)) {
            side_menu.closeDrawer(GravityCompat.START);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // handles selected menu item
        // starts the Fragment

        switch (menuItem.getItemId()) {
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
        // adds Fragment to container

        try {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, frag)
                    .addToBackStack(null)
                    .commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment frag) {
        // replaces Fragment
        // pushes onto BackStack
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, frag)
                    .addToBackStack(null)
                    .commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackStackChanged() {
    // trivially handles highlighting of menu item on back press

        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(current instanceof HomeFragment){
            navigationView.setCheckedItem(R.id.menu_home);
        }else
        if( current instanceof WizardFragment){
            navigationView.setCheckedItem(R.id.menu_wizard);
        }else
        if( current instanceof RemoteFragment){
            navigationView.setCheckedItem(R.id.menu_remote);
        }else
        if(current instanceof ConnectionsFragment){
            navigationView.setCheckedItem(R.id.menu_connections);
        }else
        if(current instanceof IdentitiesFragment){
            navigationView.setCheckedItem(R.id.menu_identities);
        }else
        if(current instanceof SnippetsFragment){
            navigationView.setCheckedItem(R.id.menu_snippets);
        }else
        if(current instanceof JobsFragment){
            navigationView.setCheckedItem(R.id.menu_job);
        }

    }


}
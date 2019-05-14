package de.host.connectionmanagerapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // handles selected menu option

        switch(menuItem.getItemId()){
            case R.id.menu_home:
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.menu_wizard:
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WizardFragment()).commit();
                break;
            case R.id.menu_connections:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConnectionsFragment()).commit();
                break;
            case R.id.menu_identities:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IdentitiesFragment()).commit();
                break;
            case R.id.menu_snippets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SnippetsFragment()).commit();
                break;
          /*  case R.id.menu_job:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new JobsFragment()).commit();
                break;
            case R.id.menu_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SnippetsFragment()).commit();
                break;
            case R.id.menu_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SnippetsFragment()).commit();
                break;
           */
        }
        side_menu.closeDrawer(GravityCompat.START);
        return true;
    }
}
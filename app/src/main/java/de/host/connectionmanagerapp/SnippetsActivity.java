package de.host.connectionmanagerapp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class SnippetsActivity extends AppCompatActivity {

    private DrawerLayout side_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        side_menu = findViewById(R.id.layout_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, side_menu, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        side_menu.addDrawerListener(toggle);
        toggle.syncState();
    }

    // handles side-menu when Back-Button of Phone is pressed
    @Override
    public void onBackPressed() {
        if (side_menu.isDrawerOpen(GravityCompat.START)) {
            side_menu.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

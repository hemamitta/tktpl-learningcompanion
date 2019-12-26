package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Learning Companion");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CardView cardLibrary = findViewById(R.id.card_library);
        CardView cardLearningProgress = findViewById(R.id.card_learning_progress);

        cardLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchLibrary();
            }
        });

        cardLearningProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchLearningProgress();
            }
        });
    }

    private void launchLibrary() {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }

    private void launchLearningProgress() {
        Intent intent = new Intent(this, LearningProgressActivity.class);
        startActivity(intent);
    }

    private void launchAccount() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (id == R.id.nav_library) {
            if (!toolbar.getTitle().equals("Library")) {
                launchLibrary();
            }
        }

        else if (id == R.id.nav_learning_progress) {
            if (!toolbar.getTitle().equals("Learning Progress")) {
                launchLearningProgress();
            }
        }

        else if (id == R.id.nav_account) {
            if (!toolbar.getTitle().equals("Account")) {
                launchAccount();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

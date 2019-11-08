package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.io.Serializable;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Library;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding.LibraryAdapter;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.viewmodel.LibraryViewModel;

public class LibraryActivity extends MainActivity {

    private LibraryViewModel libraryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Library");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // List of Library
        RecyclerView recyclerView = findViewById(R.id.library_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        final LibraryAdapter adapter = new LibraryAdapter();
        recyclerView.setAdapter(adapter);

        libraryViewModel = ViewModelProviders.of(this).get(LibraryViewModel.class);
        libraryViewModel.getAllLibs().observe(this, new Observer<List<Library>>() {
            @Override
            public void onChanged(@Nullable List<Library> libraries) {
                adapter.setLibraries(libraries);
            }
        });

        // View detail
        adapter.setOnItemClickListener(new LibraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Library library) {
                Intent intent = new Intent(LibraryActivity.this, LibraryDetailActivity.class);
                intent.putExtra(LibraryDetailActivity.EXTRA_LIBRARY_ID, library.getLibraryId());
                intent.putExtra(LibraryDetailActivity.EXTRA_LIBRARY_NAME, library.getLibraryName());
                intent.putExtra(LibraryDetailActivity.EXTRA_BOOK_LIST, (Serializable) library.getBookList());
                startActivityForResult(intent, 1);
            }
        });
    }

}

package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Book;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding.BookAdapter;

public class LibraryDetailActivity extends AppCompatActivity  {

    public static final String EXTRA_LIBRARY_ID =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_LIBRARY_ID";
    public static final String EXTRA_LIBRARY_NAME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_LIBRARY_NAME";
    public static final String EXTRA_BOOK_LIST =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_BOOK_LIST";

    String libraryName;
    List<Book> bookList;

    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        Intent intentCourse = getIntent();
        libraryName = intentCourse.getStringExtra(EXTRA_LIBRARY_NAME);
        bookList = ((List<Book>) getIntent().getExtras().getSerializable(EXTRA_BOOK_LIST));

        setTitle(libraryName);

        // List of Book
        recyclerView = (RecyclerView) findViewById(R.id.book_recycler_view);
        adapter = new BookAdapter(bookList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LibraryDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}

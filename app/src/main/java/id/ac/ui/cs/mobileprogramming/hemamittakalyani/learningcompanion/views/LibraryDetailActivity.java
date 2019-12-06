package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Book;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding.BookAdapter;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LibraryDetailActivity extends AppCompatActivity  {

    public static final String EXTRA_LIBRARY_ID =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_LIBRARY_ID";
    public static final String EXTRA_LIBRARY_NAME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_LIBRARY_NAME";
    public static final String EXTRA_BOOK_LIST =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_BOOK_LIST";
    private static final int PERMISSION_REQUEST_CODE = 200;

    private String libraryName;
    private List<Book> bookList;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    ScrollView scrollView;
    String path;

    private final Handler toastHandlerSuccess = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "Download file is in " + path, Toast.LENGTH_LONG).show();
        }
    };

    private final Handler toastHandlerFail = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "Download failed", Toast.LENGTH_LONG).show();
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                path = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK) {
                    toastHandlerSuccess.sendEmptyMessage(0);
                }
                else {
                    toastHandlerFail.sendEmptyMessage(0);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        Intent intentCourse = getIntent();
        libraryName = intentCourse.getStringExtra(EXTRA_LIBRARY_NAME);
        bookList = ((List<Book>) getIntent().getExtras().getSerializable(EXTRA_BOOK_LIST));

        setTitle(libraryName);
        scrollView = findViewById(R.id.activity_library_detail);

        // List of Book
        recyclerView = findViewById(R.id.book_recycler_view);
        adapter = new BookAdapter(bookList, new BookAdapter.OnItemClickListener() {
            @Override public void onItemClick(Book book) {
                onClickDownload(book);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LibraryDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                DownloadService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void onClickDownload(Book book) {
        if (!permissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission();
            }
        }
        else {
            proceedDownload(book);
        }
    }

    public void proceedDownload(Book book) {
        String title = book.getTitle().replaceAll("\\s+","") + ".pdf";
        String pdfUrl = book.getPdfUrl();

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.FILENAME, title);
        intent.putExtra(DownloadService.URL, pdfUrl);
        startService(intent);
        Toast.makeText(LibraryDetailActivity.this, "Download is starting...", Toast.LENGTH_LONG).show();
    }

    private boolean permissionGranted() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(LibraryDetailActivity.this, new String[]{INTERNET, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean internetAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (internetAccepted && storageAccepted)
                        Snackbar.make(scrollView, "Permission Granted. Now you can download e-book.", Snackbar.LENGTH_LONG).show();
                    else {
                        Snackbar.make(scrollView, "Permission must be granted to download e-book.", Snackbar.LENGTH_LONG).show();
                    }
                }

                break;
        }
    }
}

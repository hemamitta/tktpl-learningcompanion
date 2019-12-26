package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "Notification Channel";
    public static int NOTIFICATION_ID = 101;
    NotificationChannel notificationChannel;

    ScrollView scrollView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        Intent intentCourse = getIntent();
        String libraryName = intentCourse.getStringExtra(EXTRA_LIBRARY_NAME);
        @SuppressWarnings("unchecked")
        List<Book> bookList = ((List<Book>) Objects.requireNonNull(getIntent().getExtras()).getSerializable(EXTRA_BOOK_LIST));

        setTitle(libraryName);
        scrollView = findViewById(R.id.activity_library_detail);

        // List of Book
        RecyclerView recyclerView = findViewById(R.id.book_recycler_view);
        BookAdapter adapter = new BookAdapter(bookList, new BookAdapter.OnItemClickListener() {
            @Override public void onItemClick(Book book) {
                onClickDownload(book);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LibraryDetailActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Notification Channel
        initNotificationChannel();
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
    }

    public void onClickDownload(Book book) {
        if (!permissionGranted()) {
            requestPermission();
        }
        else {
            proceedDownload(book);
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean internetAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (internetAccepted && storageAccepted)
                        Snackbar.make(scrollView, R.string.permissionLibraryDetail, Snackbar.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent(this, PermissionActivity.class);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private void initNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[] { 500, 500, 500, 500, 500 });
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    @SuppressLint("HandlerLeak")
    private final Handler toastHandlerFail = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), R.string.downloadFailed, Toast.LENGTH_LONG).show();
        }
    };

    public void proceedDownload(Book book) {
        String title = book.getTitle().replaceAll("\\s+","") + ".pdf";
        String pdfUrl = book.getPdfUrl();

        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.FILENAME, title);
        intent.putExtra(DownloadService.URL, pdfUrl);
        startService(intent);
        Toast.makeText(LibraryDetailActivity.this, R.string.downloadStarting, Toast.LENGTH_LONG).show();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String filename = bundle.getString(DownloadService.FILENAME);
                path = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK) {
                    displayNotification(filename);
                }
                else {
                    toastHandlerFail.sendEmptyMessage(0);
                }
            }
        }
    };

    private void displayNotification(String filename) {
        Intent intent = getOpenFileIntent(getApplicationContext(), path);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(contentIntent);
        builder.setContentTitle("Downloaded: " + filename);
        builder.setContentText("Tap to open file");
        builder.setSmallIcon(R.drawable.ic_learning_companion_foreground);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_learning_companion_foreground));
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        NOTIFICATION_ID++;
    }


    public static Intent getOpenFileIntent(Context context, String path) {
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri apkURI = FileProvider.getUriForFile(
                context,
                context.getApplicationContext()
                        .getPackageName() + ".provider", file);

        intent.setDataAndType(apkURI, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return intent;
    }
}

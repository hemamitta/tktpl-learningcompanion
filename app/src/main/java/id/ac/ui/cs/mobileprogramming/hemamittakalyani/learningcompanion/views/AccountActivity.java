package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding.QuoteAdapter;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Quote;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.viewmodel.QuoteViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AccountActivity extends MainActivity {

    private Uri picUri;
    private Bitmap selectedImage;
    private CircleImageView imageView;
    private SharedPreferences.Editor prefsEditor;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;

    private RecyclerView my_recycler_view;
    private QuoteAdapter adapter;
    private Quote quote = new Quote();
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.account);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        root = findViewById(R.id.content_account_activity);

        loadQuote();
        loadProfilePicture();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (selectedImage != null) {
            selectedImage.recycle();
        }
    }

    private boolean permissionGranted() {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);
        return (permissionsToRequest.size() == 0);
    }

    private void requestPermissions() {
        requestPermissions(permissionsToRequest.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
    }

    private void loadQuote() {
        my_recycler_view = findViewById(R.id.quote_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AccountActivity.this);
        my_recycler_view.setLayoutManager(layoutManager);

        adapter = new QuoteAdapter(AccountActivity.this, quote);
        my_recycler_view.setAdapter(adapter);

        QuoteViewModel model = ViewModelProviders.of(this).get(QuoteViewModel.class);
        model.getRandomQuote().observe(this, new Observer<Quote>() {
            @Override
            public void onChanged(@Nullable Quote quote) {
                adapter = new QuoteAdapter(AccountActivity.this, quote);
                my_recycler_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void loadProfilePicture() {
        FloatingActionButton fab = findViewById(R.id.fabImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissionGranted()) {
                    startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
                }
                else {
                    requestPermissions();
                }
            }
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Prefs", 0);
        prefsEditor = pref.edit();

        String userPhoto = pref.getString("userPhoto", null);

        imageView = findViewById(R.id.imageView);

        if (userPhoto != null) {
            Bitmap photo = BitmapFactory.decodeFile(userPhoto);
            imageView.setImageBitmap(photo);
        }

        prefsEditor.apply();
    }

    public Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (Objects.requireNonNull(intent.getComponent()).getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[0]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_RESULT) {
                String filePath = getImageFilePath(data);

                if (filePath != null) {
                    selectedImage = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(selectedImage);
                    prefsEditor.putString("userPhoto", filePath);
                    prefsEditor.commit();
                }
            }
        }
    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());
    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        @SuppressLint("Recycle")
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = Objects.requireNonNull(cursor).getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean hasPermission(String permission) {
        return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    Intent intent = new Intent(this, PermissionActivity.class);
                    startActivity(intent);
                }
                else {
                    Snackbar.make(root, R.string.permissionGrantedAccount, Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
}
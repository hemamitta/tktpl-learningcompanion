package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        setTitle(R.string.permissionTitle);
    }
}

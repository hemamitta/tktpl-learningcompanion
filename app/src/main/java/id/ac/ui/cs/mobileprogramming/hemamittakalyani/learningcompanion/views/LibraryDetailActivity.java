package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;

public class LibraryDetailActivity extends AppCompatActivity  {

    public static final String EXTRA_LIBRARY_ID =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_LIBRARY_ID";
    public static final String EXTRA_LIBRARY_NAME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_LIBRARY_NAME";

    String libraryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_detail);

        Intent intentCourse = getIntent();
        libraryName = intentCourse.getStringExtra(EXTRA_LIBRARY_NAME);

        setTitle(libraryName);
    }

}

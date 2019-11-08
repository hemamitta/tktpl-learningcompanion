package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;

public class AddCourseActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_COURSE_NAME";
    public static final String EXTRA_TARGET_TIME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_TARGET_TIME";
    public static final String EXTRA_TOTAL_TIME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_TOTAL_TIME";

    private EditText editTextCourseName;
    private EditText editTextTargetMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editTextCourseName = findViewById(R.id.inCourseName);
        editTextTargetMinute = findViewById(R.id.inTargetMinute);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Course");
    }

    private void saveCourse() {
        String courseName = editTextCourseName.getText().toString();
        String targetMin = editTextTargetMinute.getText().toString();

        if (courseName.trim().isEmpty() || targetMin.trim().isEmpty()) {
            Toast.makeText(AddCourseActivity.this, "Please fill all the field", Toast.LENGTH_SHORT);
            return;
        }

        int targetMinute = Integer.parseInt(targetMin);
        int targetSecond = targetMinute * 60;

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_NAME, courseName);
        data.putExtra(EXTRA_TARGET_TIME, targetSecond);
        data.putExtra(EXTRA_TOTAL_TIME, 0);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;

public class CourseProgressActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_COURSE_ID =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_NAME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_COURSE_NAME";
    public static final String EXTRA_TARGET_TIME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_TARGET_TIME";
    public static final String EXTRA_TOTAL_TIME =
            "id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.EXTRA_TOTAL_TIME";

    Button start, stop, saveProgress;
    TextView stopWatch, progressTimeText;
    String courseName;
    int targetSecond, targetMinute, totalTime;
    double totalTimeMinute;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;

    DecimalFormat df = new DecimalFormat("#.###");

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_progress);

        TextView targetTimeView = findViewById (R.id.targetTime);
        TextView totalTimeView = findViewById (R.id.totalTime);

        Intent intentCourse = getIntent();
        courseName = intentCourse.getStringExtra(EXTRA_COURSE_NAME);

        targetSecond = intentCourse.getIntExtra(EXTRA_TARGET_TIME, 9000);
        targetMinute = targetSecond / 60;

        totalTime = intentCourse.getIntExtra(EXTRA_TOTAL_TIME, 0);
        totalTimeMinute = totalTime / 60.00;

        String targetTimeText= Integer.toString(targetMinute) + " min / " + Integer.toString(targetSecond) + " sec";
        String totalTimeText = df.format(totalTimeMinute) + " min / " + Integer.toString(totalTime) + " sec";
        targetTimeView.setText(targetTimeText);
        totalTimeView.setText(totalTimeText);
        setTitle(courseName);

        start = findViewById(R.id.startButton);
        stop = findViewById(R.id.stopButton);
        saveProgress = findViewById(R.id.saveProgressButton);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        saveProgress.setOnClickListener(this);

        stopWatch = findViewById(R.id.stopWatch);
        progressTimeText = findViewById(R.id.progressTime);

        double progressInt = percentageFromJNI(totalTime, targetSecond);
        String result = df.format(progressInt) + "%";
        progressTimeText.setText(result);

        handler = new Handler() ;
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            stopWatch.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onClick(View v) {
        if (v == start) {
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
        }
        else if (v == stop) {
            TimeBuff += MillisecondTime;
            handler.removeCallbacks(runnable);
        }
        else if (v == saveProgress) {
            saveProgress();
        }
    }

    private void saveProgress() {
        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_NAME, courseName);
        data.putExtra(EXTRA_TARGET_TIME, targetSecond);
        data.putExtra(EXTRA_TOTAL_TIME, totalTime + Seconds);

        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_COURSE_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    public native double percentageFromJNI(int totalTime, int targetSecond);

}

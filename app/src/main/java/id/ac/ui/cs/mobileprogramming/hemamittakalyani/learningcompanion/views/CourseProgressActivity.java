package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView counterText, stopText, progressTimeText;
    MyResultReceiver resultReceiver;
    Intent intentTimer;

    String courseName;
    int targetSecond;
    int targetMinute;
    int totalTime;
    double totalTimeMinute;

    DecimalFormat df = new DecimalFormat("#.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_progress);

        TextView targetTimeView = (TextView) findViewById (R.id.targetTime);
        TextView totalTimeView = (TextView) findViewById (R.id.totalTime);

        Intent intentCourse = getIntent();
        courseName = intentCourse.getStringExtra(EXTRA_COURSE_NAME);

        targetSecond = intentCourse.getIntExtra(EXTRA_TARGET_TIME, 9000);
        targetMinute = (Integer) targetSecond / 60;


        totalTime = intentCourse.getIntExtra(EXTRA_TOTAL_TIME, 0);
        totalTimeMinute = totalTime / 60.00;

        targetTimeView.setText(Integer.toString(targetMinute) + " min / " + Integer.toString(targetSecond) + " sec");
        totalTimeView.setText(df.format(totalTimeMinute) + " min / " + Integer.toString(totalTime) + " sec");
        setTitle(courseName);

        start = findViewById(R.id.startButton);
        stop = findViewById(R.id.stopButton);
        saveProgress = findViewById(R.id.saveProgressButton);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        saveProgress.setOnClickListener(this);

        resultReceiver = new MyResultReceiver(null);
        counterText = findViewById(R.id.counterText);
        stopText = findViewById(R.id.stopText);
        progressTimeText = findViewById(R.id.progressTime);

        stopText.setText("If your timer has started, click 'START' to show the time. This will not restart your timer." );
        double progressInt = (totalTime * 1.0 / targetSecond * 1.0) * 100.0;
        progressTimeText.setText(df.format(progressInt) + "%");
    }

    @Override
    public void onClick(View v) {
        if (v == start) {
            initIntent();
            startService(intentTimer);
            stopText.setText("Timer started." );
        }
        else if (v == stop) {
            initIntent();
            stopService(intentTimer);
            stopText.setText("Timer stopped.");
        }
        else if (v == saveProgress) {
            initIntent();
            stopService(intentTimer);
            stopText.setText("Timer stopped.");

            saveProgress();
        }
    }

    public void initIntent() {
        intentTimer = new Intent(this, StopwatchService.class);
        intentTimer.putExtra("receiver", resultReceiver);
    }

    private void saveProgress() {
        String counterTime = (String) counterText.getText();
        int courseTimeSecond = Integer.parseInt(counterTime);

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_NAME, courseName);
        data.putExtra(EXTRA_TARGET_TIME, targetSecond);
        data.putExtra(EXTRA_TOTAL_TIME, totalTime + courseTimeSecond);

        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_COURSE_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


    class UpdateUI implements Runnable {
        String updateString;

        public UpdateUI(String updateString) {
            this.updateString = updateString;
        }
        public void run() {
            counterText.setText(updateString);
        }
    }

    class MyResultReceiver extends ResultReceiver {
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if(resultCode == Integer.MAX_VALUE){
                runOnUiThread(new UpdateUI(resultData.getString("start")));
            }
            else {
                runOnUiThread(new UpdateUI("" + resultCode));
            }
        }
    }
}

package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class StopwatchService extends Service {

    Timer timer = new Timer();
    MyTimerTask timerTask;
    ResultReceiver resultReceiver;
    long start;
    long end;
    float seconds;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        start = System.currentTimeMillis();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        resultReceiver = intent.getParcelableExtra("receiver");
        timerTask = new MyTimerTask();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    class MyTimerTask extends TimerTask
    {
        public MyTimerTask() {
            Bundle bundle = new Bundle();
            bundle.putString("start", "Timer Started....");
            resultReceiver.send(Integer.MAX_VALUE, bundle);
        }

        @Override
        public void run() {
            end = System.currentTimeMillis();
            seconds = (end - start) / 1000F;
            resultReceiver.send((int) seconds, null);
        }
    }

}
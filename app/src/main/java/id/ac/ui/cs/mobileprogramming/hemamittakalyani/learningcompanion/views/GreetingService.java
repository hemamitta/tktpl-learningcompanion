package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.IGreetingService;

public class GreetingService extends Service {
    private static final String TAG = "GreetingService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IGreetingService.Stub() {
            public String generateGreeting(String name) throws RemoteException {
                String greeting = "Hello, " + name + "!";
                Log.i(TAG, greeting);
                return (greeting);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}

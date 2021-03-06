package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.IGreetingService;

public class GreetingService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IGreetingService.Stub() {
            public String generateGreeting() throws RemoteException {
                return ("Hello World!");
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
package com.Hargis.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 660162328 on 9/16/2015.
 */
public class ReminderService extends Service {

    private Timer timer;
    private NotificationManager manager;
    private final int NOTIFICATION_ID = 1;

    public void onCreate(){
        Log.d("new reader", "service Created");
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Reminder", "service started ");
        startTimer();
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Reminder", "No binding for the service");
        return null;
    }

    public void onDestroy(){
        Log.d("reminder", "Service destroyed");
        stopTimer();
    }

    private void startTimer(){
        // create task
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                Log.d("Reminder", "Look into the distance. It is good for your eyes!");

                Intent notificationIntent = new Intent(getApplicationContext(), ReminderActivity.class);

                // create the pending intent
                int flags = PendingIntent.FLAG_UPDATE_CURRENT;

                PendingIntent pendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, flags);

                int icon = R.drawable.ic_launcher;
                CharSequence tickerText = getText(R.string.app_name);
                CharSequence contentTitle = getText(R.string.app_name);
                CharSequence contentText = getText(R.string.app_description);

                Notification notification = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(icon)
                        .setTicker(tickerText)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setContentIntent(pendingIntent)
                        .build();
                // notify the user here
                manager.notify(NOTIFICATION_ID, notification);

            }
        };
        // create the start timer
        timer = new Timer(true);
        int delay = 0;
        int interval = 1000;
        timer.schedule(task, delay, interval);
    }
    private void stopTimer(){
        if(timer !=null){
            timer.cancel();
        }
    }



    @Override
    public Context getApplicationContext() {
        return null;
    }


    private class ItemsActivity {
    }
}

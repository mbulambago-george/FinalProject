package com.example.client.finalproject;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class TimeCounterService extends Service {
    String TAG="com.example.client.finalproject";
    public TimeCounterService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer=new Timer();
        timer.schedule(timerTask,2000,2*1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

        }catch (Exception e) {
            e.printStackTrace();


        }
        return super.onStartCommand(intent,flags,startId);
    }
    private Timer timer;
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            Log.e("Log","Running");
            notify();
        }
    };

    @Override
    public void onDestroy() {
        try {
            timer.cancel();
            timerTask.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent= new Intent("com.example.client.finalproject");
        intent.putExtra("your vLue","to restore");
        sendBroadcast(intent);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void noty(){
        IntentFilter filter=new IntentFilter();
        filter.addAction("TimerCounterService");

        Intent myint=new Intent(Intent.ACTION_VIEW, Uri.parse(""));
        PendingIntent pend = PendingIntent.getActivity(getBaseContext(),0,myint, Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context=getApplicationContext();
        Notification.Builder builder;

        builder=new Notification.Builder(context)
        .setContentTitle("i am George")
        .setContentText("You")
        .setContentIntent(pend)
        .setDefaults(Notification.DEFAULT_SOUND)
        .setAutoCancel(true)
        .setSmallIcon(R.drawable.rolex1);
        Notification notification = builder.build();
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);


    }
}
//        Log.i(TAG,"onStartCommand method called");
//        Runnable ru = new Runnable(){
//            public void run(){
//                for(int i=0;i<5;i++){
//                    long futureTime = System.currentTimeMillis()+5000;
//                    while (System.currentTimeMillis()<futureTime){
//                        synchronized (this){
//                            try{
//                                wait(futureTime-System.currentTimeMillis());
//                                Log.i(TAG,"Service is running");
//                            }catch(Exception e){
//
//                            }
//                        }
//                    }
//                }
//            }
//        };
//        Thread thr = new Thread(ru);
//        thr.start();
//        return Service.START_STICKY;

//
//    @Override
//    public void onDestroy() {
//        Log.i(TAG,"onDestroy method called");
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//       return null;
//    }


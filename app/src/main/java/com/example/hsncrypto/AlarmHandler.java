package com.example.hsncrypto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class AlarmHandler {

    private final Context context;
    public AlarmHandler(Context context){
        this.context = context;

    }


    public  void setAlarmManeger(){
        Intent intent = new Intent(context, WidgetsService.class);
        PendingIntent sender;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sender = PendingIntent.getBroadcast(context,2,intent,PendingIntent.FLAG_IMMUTABLE);
        }else
        {
            sender = PendingIntent.getBroadcast(context,2,intent,0);

        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        Calendar c = Calendar.getInstance();
        long l = c.getTimeInMillis() + 10000;

        if (alarmManager != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,l,sender);
            }else
            {
                alarmManager.set(AlarmManager.RTC_WAKEUP,l,sender);
            }
        }

    }

    public void cancelAlarmManeger(){
        Intent intent = new Intent(context, WidgetsService.class);
        PendingIntent sender;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sender = PendingIntent.getBroadcast(context,2,intent,PendingIntent.FLAG_IMMUTABLE);
        }else
        {
            sender = PendingIntent.getBroadcast(context,2,intent,0);

        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(alarmManager != null)
        {
            alarmManager.cancel(sender);
        }

    }
}

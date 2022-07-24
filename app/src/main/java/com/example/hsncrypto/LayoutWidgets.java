package com.example.hsncrypto;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LayoutWidgets extends AppWidgetProvider {
   static int temp = 0;

    void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widgets_layout);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.image_backg,pendingIntent);


        // for btc
//        SharedPreferences preferencesBtc = context.getSharedPreferences("PREFS",0);
//        String valueBTC = preferencesBtc.getString("value","No Internet");



        //get widget value
        SharedPreferences preferences = context.getSharedPreferences("PREFS",0);
        int value = preferences.getInt("value",1);

        if(value >temp){
            views.setTextColor(R.id.widget_price,Color.GREEN);

        }
        else if (value < temp){
            views.setTextColor(R.id.widget_price,Color.RED);
        }
        else
        {
            views.setTextColor(R.id.widget_price,Color.WHITE);
        }
        Log.d("temp","temp: "+ temp);
        temp = value;
        Log.d("temp","temp: "+ temp);
        NumberFormat formatter = new DecimalFormat("###,###");


        views.setTextViewText(R.id.widget_price,"BTC: "+formatter.format(value)+ "₺");
        //Intent intent = new Intent(context,MainActivity.class);
       // views.setOnClickPendingIntent(R.id.image_backg,intent);
        //update the widget
        appWidgetManager.updateAppWidget(appWidgetId,views);

        //reshedule the widget refresh
        AlarmHandler alarmHandler = new AlarmHandler(context);
        alarmHandler.cancelAlarmManeger();
        alarmHandler.setAlarmManeger();

        Log.d("widget","widget updated!");


    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appwidgetsId : appWidgetIds){
            updateWidget(context,appWidgetManager,appwidgetsId);

        }


    }

    @Override
    public void onDisabled(Context context) {
        //stop updating widgets
        AlarmHandler alarmHandler = new AlarmHandler(context);
        alarmHandler.cancelAlarmManeger();
        Log.d("widget","widget onDisabled!");
    }
}

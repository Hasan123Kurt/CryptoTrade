package com.example.hsncrypto;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WidgetsService extends BroadcastReceiver {

    Effects effects;


    @Override
    public void onReceive(Context context, Intent intent) {
        //wake the device
        WakeLocker.acquire(context);
        effects = Effects.slideOnTop;

        //for btc  string

//        SharedPreferences preferencesBTC = context.getSharedPreferences("PREFS",0);
//        String valueBtc = preferencesBTC.getString("value","deneme");
//        SharedPreferences.Editor editorBtc = preferencesBTC.edit();
//        editorBtc.putString("value",valueBtc);
//        editorBtc.apply();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "https://api.btcturk.com/api/v2/ticker";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    JsonObject ob = new JsonParser().parse(myResponse).getAsJsonObject();
                    String rates = ob.getAsJsonArray("data").get(0).toString();
                    JsonObject ob2 = new JsonParser().parse(rates).getAsJsonObject();
                    String Price = ob2.get("last").toString();
                    SharedPreferences preferences = context.getSharedPreferences("PREFS",0);
                    int value = preferences.getInt("value",1);
                    SharedPreferences.Editor editor = preferences.edit();
                    // value++;
                    editor.putInt("value",Math.round(Float.parseFloat(Price)));
                    editor.apply();
                }

            }
        });






        //force widget update
        Intent widgeIntent = new Intent(context,LayoutWidgets.class);
        widgeIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,LayoutWidgets.class));
        widgeIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(widgeIntent);


        Log.d("widget","Widgets set to update");
        //go back to sleep
        WakeLocker.realese();

    }

    private void BtcPriceService() {



    }
}

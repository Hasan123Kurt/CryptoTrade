package com.example.hsncrypto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.github.mikephil.charting.data.CandleEntry;
import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements recyclerAdepter.OnClickListener {
    private ArrayList<ListItem> listItems;
    private RecyclerView recyclerView;
    public String Price =" ";
    recyclerAdepter adepter;
    TextView usdPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerList);
        usdPrice = findViewById(R.id.usdprice);
            listItems = new ArrayList<>();
            setListItem();
            setadepter();
            notification();



        Configuration cfg = new Configuration.Builder()
                .setBackgroundColor("#3E3D3D")
                .setAnimDuration(2500)
                .setDispalyDuration(6500)
                .setTextColor("#FFFFFFFF")
                .setTextPadding(5)
                .setViewHeight(48)
                .setTextLines(2)
                .setTextGravity(Gravity.CENTER)
                .build();

        NiftyNotificationView.build(this,"Welcome to HSNCrypto", Effects.slideOnTop,R.id.notication,cfg)
                .setIcon(R.drawable.bitcoin)
                .show();





            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dataThread();
                    Log.d("Thread","Thread");
                }
            };

            handler.postDelayed(runnable,1000);




    }

    private void notification() {


    }

    private void dataThread() {
        Handler handler = new Handler();
        Runnable timeRunable =new Runnable() {
            @Override
            public void run() {

                LiveFetchData();
                Log.d("handler","test ");
                handler.postDelayed(this,15000);

            }
        };
        handler.postDelayed(timeRunable,0);
    }


    private void setadepter() {
        adepter = new recyclerAdepter(listItems,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adepter);

    }

    private void setListItem() {
        listItems.add(new ListItem("Bitcoin","$32.15","BTC"));
        listItems.add(new ListItem("Etherium","$24.67","ETH"));
        listItems.add(new ListItem("Doge","$12.87","Doge"));



    }

    public void LiveFetchData(){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "https://api.btcturk.com/api/v2/ticker";

        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();

                    JsonObject ob = new JsonParser().parse(myResponse).getAsJsonObject();
                    String rates = ob.getAsJsonArray("data").get(0).toString();
                    String eth = ob.getAsJsonArray("data").get(2).toString();
                    String doge = ob.getAsJsonArray("data").get(81).toString();

                    String usdt = ob.getAsJsonArray("data").get(5).toString();

                    JsonObject ob2 = new JsonParser().parse(rates).getAsJsonObject();
                    JsonObject obEth = new JsonParser().parse(eth).getAsJsonObject();
                    JsonObject obdoge = new JsonParser().parse(doge).getAsJsonObject();
                    JsonObject obUsd = new JsonParser().parse(usdt).getAsJsonObject();


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("data",ob2.get("last").toString());
                            Price = ob2.get("last").toString();
                            listItems.get(0).setBtcPrice(Price + "₺");
                            adepter.notifyItemChanged(0);


                            listItems.get(1).setBtcPrice(obEth.get("last").toString() +"₺");
                            adepter.notifyItemChanged(1);

                            listItems.get(2).setBtcPrice(obdoge.get("last").toString() + "₺");
                            adepter.notifyItemChanged(2);

                            usdPrice.setText(obUsd.get("last").toString()+"₺");




                        }
                    });
                }

            }
        });

    }

    @Override
    public void onClick(int position) {
        Log.d("position","position" + position);
        Intent intent = new Intent(this,ScrollingActivity.class);
        startActivity(intent);

    }
}
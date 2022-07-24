package com.example.hsncrypto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {
    int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(time < 7) {
                    handler.postDelayed(this, 1000);
                    time++;

                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

                }

                Log.d("Thread","Thread");
            }
        };

        handler.postDelayed(runnable,0);
    }
}
package com.example.hsncrypto;

import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.os.Bundle;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsncrypto.databinding.ActivityScrollingBinding;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    Toolbar toolbar;
    CollapsingToolbarLayout toolBarLayout;
    TextView mainPrice;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainPrice = findViewById(R.id.mainPrice);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Bitcoin");

        imageView = findViewById(R.id.iconTitle);

        AdjustCandleChart();
        TimerService();





    }

    private void TimerService() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("handler","anim ");
                mainPrice.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in));
                handler.postDelayed(this,5000);

            }
        };
        handler.postDelayed(runnable,0);
    }

    @Override
    protected void onResume() {
        super.onResume();




    }

    private void AdjustCandleChart() {
        CandleStickChart candleStickChart = findViewById(R.id.candleChart);
        candleStickChart.setHighlightPerDragEnabled(true);
        candleStickChart.setDrawBorders(true);
        candleStickChart.setBorderColor(getResources().getColor(R.color.lightGray));

        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setDrawGridLines(false);
        candleStickChart.requestDisallowInterceptTouchEvent(true);

        XAxis xAxis = candleStickChart.getXAxis();

        xAxis.setDrawGridLines(true);
        xAxis.setDrawLabels(true);
        rightAxis.setTextColor(R.color.white);
        yAxis.setTextColor(R.color.white);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(0.6f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = candleStickChart.getLegend();
        l.setEnabled(false);



        ArrayList<CandleEntry> yValueEntry = new ArrayList<CandleEntry>();

        yValueEntry.add(new CandleEntry(0,225.0f,219.84f,224.94f,221.07f));
        yValueEntry.add(new CandleEntry(1,228.35f,222.57f,223.52f,226.41f));
        yValueEntry.add(new CandleEntry(2,226.84f,222.48f,225.75f,223.84f));
        yValueEntry.add(new CandleEntry(3,222.95f,217.27f,222.15f,217.88f));
        yValueEntry.add(new CandleEntry(4,221.14f,211.84f,224.94f,221.07f));
        yValueEntry.add(new CandleEntry(5,228.35f,222.57f,223.52f,226.41f));
        yValueEntry.add(new CandleEntry(6,226.84f,222.48f,225.75f,223.84f));
        yValueEntry.add(new CandleEntry(7,222.95f,217.27f,222.15f,217.88f));


        CandleDataSet candleDataSet = new CandleDataSet(yValueEntry,"DATASET 1");
        candleDataSet.setColor(Color.rgb(80,80,80));
        candleDataSet.setShadowColor(getResources().getColor(R.color.lightGray));
        candleDataSet.setShadowWidth(0.8f);
        candleDataSet.setDecreasingColor(getResources().getColor(R.color.red));
        candleDataSet.setDrawHighlightIndicators(true);
        candleDataSet.setHighlightLineWidth(0.4f);
        candleDataSet.setDecreasingPaintStyle(Paint.Style.FILL);

        candleDataSet.setIncreasingColor(getResources().getColor(R.color.green));
        candleDataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        candleDataSet.setValueTextColor(R.color.white);
        candleDataSet.setNeutralColor(R.color.white);
        candleDataSet.setDrawValues(true);


        CandleData candleData = new CandleData(candleDataSet);

        candleStickChart.setData(candleData);
        candleStickChart.invalidate();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
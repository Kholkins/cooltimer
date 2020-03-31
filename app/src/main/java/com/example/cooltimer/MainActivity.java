package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        seekBar.setMax(600);
        seekBar.setProgress(60);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress/60;
                int seconds = progress - (minutes*60);

                String strMinutes = "";
                String strSeconds = "";

                if (minutes<10) {
                    strMinutes = "0"+minutes;
                }else strMinutes = String.valueOf(minutes);

                if (seconds<10) {
                    strSeconds = "0"+seconds;
                }else strSeconds = String.valueOf(seconds);

                textView.setText(strMinutes+":"+strSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /*CountDownTimer downTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("timer", String.valueOf(millisUntilFinished/1000)+"seconds left.");
            }

            @Override
            public void onFinish() {
                Log.d("timer", "finish");
            }
        };
        downTimer.start();*/

       /* final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("runnable", "Two seconds are passed");
                handler.postDelayed(this,2000);
            }
        };
        handler.post(runnable);*/

    }

    public void start(View view) {
        new CountDownTimer(seekBar.getProgress()*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}

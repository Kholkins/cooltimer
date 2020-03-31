package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountDownTimer downTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }

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
}

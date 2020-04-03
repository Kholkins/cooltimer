package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SeekBar seekBar;
    private TextView textView;
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;
    private int defaultInterval;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        seekBar.setMax(600);
        setIntervalFromSharedPreference(sharedPreferences);
        isTimerOn = false;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long progressMS = progress*1000;
                updateTimer(progressMS);
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

        if (!isTimerOn){
            button.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = true;
            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (sharedPreferences.getBoolean("enable_sound", true)) {

                        String melodyName = sharedPreferences.getString("timer_melody", "bell");
                        if (melodyName.equals("bell")){
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                            mediaPlayer.start();
                        }
                        if (melodyName.equals("alarm_siren")){
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.siren);
                            mediaPlayer.start();
                        }
                        if (melodyName.equals("beep")){
                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.original);
                            mediaPlayer.start();
                        }

                    }
                    resetTimer();
                }
            }.start();

        }else {
            resetTimer();
        }
    }

    private void updateTimer (long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished/1000/60);
        int seconds = (int) (millisUntilFinished/1000 - (minutes*60));

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

    private void resetTimer(){
        countDownTimer.cancel();
        setIntervalFromSharedPreference(sharedPreferences);
        button.setText("Start");
        seekBar.setEnabled(true);
        isTimerOn = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_memu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
            return true;
        }
        if (id == R.id.action_about){
            Intent openAbout = new Intent(this, AboutActivity.class);
            startActivity(openAbout);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setIntervalFromSharedPreference(SharedPreferences sharedPreferences){
        try {
            defaultInterval = Integer.valueOf(sharedPreferences.getString("default_timer","30"));
        }catch (Exception e){
            Toast.makeText(this, "Somr error happens", Toast.LENGTH_SHORT).show();
        }

        long defaultIntervalMS = defaultInterval*1000;
        updateTimer(defaultIntervalMS);
        seekBar.setProgress(defaultInterval);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("default_timer")){
            setIntervalFromSharedPreference(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}

package com.example.peepo.homepage;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Heater extends AppCompatActivity {
    //instance Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView txtCountDown;
    private Button btnStartPause, btnReset;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;

    private long timerLeftInMillis = START_TIME_IN_MILLIS;

    ToggleButton tgbHeater_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heater);

        txtCountDown = findViewById(R.id.txtCountDown);
        tgbHeater_status = findViewById(R.id.tgbHeater_status);
        btnStartPause = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);

        //Add HeaterStatus Firebase
        tgbHeater_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myRef.child("HeaterStatus").setValue("ON");
                } else {
                    myRef.child("HeaterStatus").setValue("OFF");
                }
            }
        });

        //Event Firebase
        myRef.child("HeaterStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tgbHeater_status.setChecked(dataSnapshot.getValue().toString().equals("ON")?true:false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Timer
        btnStartPause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(timerRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });
        

        btnReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(timerRunning==true) pauseTimer();
    }

    private void  startTimer(){
        countDownTimer = new CountDownTimer(timerLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStartPause.setText("Start");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerRunning = true;
        btnStartPause.setText("Pause");
        btnReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        btnStartPause.setText("Start");
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        timerLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
        myRef.child("HeaterTimer").setValue("00:00");
    }

    private void updateCountDownText() {
        int minutes = (int) (timerLeftInMillis / 1000) / 60;
        int seconds = (int) (timerLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        txtCountDown.setText(timeLeftFormatted);

        myRef.child("HeaterTimer").setValue(timeLeftFormatted);
    }
}

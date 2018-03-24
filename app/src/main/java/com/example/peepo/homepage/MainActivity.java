package com.example.peepo.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CardView temp_card, heater_card, motor_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp_card = findViewById(R.id.temp_card);
        heater_card = findViewById(R.id.heater_card);
        motor_card = findViewById(R.id.motor_card);

        temp_card.setOnClickListener(this);
        heater_card.setOnClickListener(this);
        motor_card.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.temp_card : i = new Intent(this, Temperature.class);startActivity(i); break;
            case R.id.heater_card : i = new Intent(this, Heater.class);startActivity(i);break;
            case R.id.motor_card : i = new Intent(this, Motor.class);startActivity(i);break;
            default:break;
        }

    }
}

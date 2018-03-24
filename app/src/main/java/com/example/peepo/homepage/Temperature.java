package com.example.peepo.homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;

public class Temperature extends AppCompatActivity {
    //instance Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    final String DEGREE = "\u00b0";
    PieView pieView_temp, pieView_hum;

    private double temp,hum;
    PieAngleAnimation animationTemp;
    PieAngleAnimation animationHum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        pieView_temp = findViewById(R.id.pieView_temp);
        pieView_hum = findViewById(R.id.pieView_hum);
        pieView_temp.setInnerText("Loading...");
        pieView_hum.setInnerText("Loading...");

        // Read Temperature Firebase
        myRef.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                temp = dataSnapshot.getValue(Double.class);
                pieView_temp.setPercentage((int) temp);
                pieView_temp.setInnerText(String.valueOf(temp) + " C" + DEGREE);
                //Duration
                animationTemp = new PieAngleAnimation(pieView_temp);
                pieView_temp.startAnimation(animationTemp);
                animationTemp.setDuration(700);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Temp", "Failed to read value.", error.toException());
            }
        });

        // Read Humidity Firebase
        myRef.child("Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                hum = dataSnapshot.getValue(Double.class);
                pieView_hum.setPercentage((int) hum);
                pieView_hum.setInnerText(String.valueOf(hum) + " %");
                //Duration
                animationHum = new PieAngleAnimation(pieView_hum);
                pieView_hum.startAnimation(animationHum);
                animationHum.setDuration(700);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Temp", "Failed to read value.", error.toException()
                );
            }
        });

    }
}

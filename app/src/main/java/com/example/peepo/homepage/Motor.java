package com.example.peepo.homepage;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Motor extends AppCompatActivity {

    //instance Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Button btnDegree;
    ToggleButton tbgMotor_status;
    Spinner degree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);

        btnDegree = findViewById(R.id.btnDegree);
        tbgMotor_status = findViewById(R.id.tgbMotor_status);
        degree = findViewById(R.id.deegree);


        //Add MotorMode Firebase
        tbgMotor_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    myRef.child("MotorMode").setValue("ON");
                }else{
                    myRef.child("MotorMode").setValue("OFF");
                }
            }
        });

        //Add MotorDegree Firebase
        btnDegree.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                myRef.child("MotorDegree").setValue(degree.getSelectedItem().toString());
                Snackbar.make(findViewById(R.id.motorLayout), "Setting degree finish.", Snackbar.LENGTH_SHORT).show();
            }
        });


        //Event Firebase
        myRef.child("MotorMode").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null)
                    try {
                        tbgMotor_status.setChecked(dataSnapshot.getValue().toString().equals("ON") ? true : false);
                    }catch (NullPointerException ex){
                        tbgMotor_status.setChecked(false);
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

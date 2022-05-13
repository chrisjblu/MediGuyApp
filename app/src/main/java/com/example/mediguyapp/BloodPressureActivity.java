package com.example.mediguyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BloodPressureActivity extends AppCompatActivity {
    EditText sys, dia;
    TextView result;
    String cal, bpresult;
    Button calbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();


        setContentView(R.layout.activity_blood_pressure);

        sys = findViewById(R.id.sys);
        dia = findViewById(R.id.dia);
        result = findViewById(R.id.bpresult);
        calbtn = findViewById(R.id.calbtn);

        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer S1 = Integer.parseInt(String.valueOf(sys.getText()));
                Integer S2 = Integer.parseInt(String.valueOf(dia.getText()));


               if(S1 <= 120 && S2 <= 80){
                   bpresult = "Your Blood Pressure is normal";

               }
               else if (S1 >= 120 && S1 <= 129 && S2 <= 80){
                   bpresult = "You have elevated blood pressure";

               }
               else if ( S1 >= 130 && S1 <= 139 & S2 >= 80 && S2 <= 89){
                   bpresult = "You have HyperTension Stage 1";
               }
               else if ( S1 >= 140 && S1 <= 159 && S2 >= 90 && S2 <= 99){
                   bpresult = "You have Hyper Tension Stage 2";
               }
               else if ( S1 > 180 && S2 > 120){
                   bpresult = "HypterTension Crisis";
               }
               else {
                   bpresult = "Urgent care required";
               }

               cal = bpresult;

               result.setText(cal);

            }
        });

    }




}
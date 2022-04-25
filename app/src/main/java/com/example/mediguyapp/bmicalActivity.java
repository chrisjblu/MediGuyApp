package com.example.mediguyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class bmicalActivity extends AppCompatActivity {

    TextView bmidisplay, bmicategory, bmigender;
    Intent intent;
    ImageView bmiimageview;
    String mbmi;
    float intbmi;

    String height;
    String weight;
    float intheight, intweight;
    RelativeLayout bmibackground;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmical);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Result");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#e74c3c"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);


        intent= getIntent();

        bmidisplay = findViewById(R.id.bmid);
        bmicategory = findViewById(R.id.displayt);
        bmigender = findViewById(R.id.genderd);
        bmibackground = findViewById(R.id.layoutcontent);
        bmiimageview = findViewById(R.id.imgdetail);

        height=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");

        intheight = Float.parseFloat(height);
        intweight = Float.parseFloat(weight);

        intheight=intheight/100;

        intbmi=intweight/(intheight*intheight);

        mbmi= Float.toString(intbmi);

        if(intbmi<16){
            bmicategory.setText("Extremely Thin");
            bmiimageview.setImageResource(R.drawable.incorrect);
        }
        else if(intbmi<16.9 && intbmi>16){
            bmicategory.setText("Medium Thin");
            bmiimageview.setImageResource(R.drawable.greentick);


        }
        else if(intbmi<18.4 && intbmi>17){
            bmicategory.setText("Mild Thin");
            bmiimageview.setImageResource(R.drawable.greentick);


        }
        else if(intbmi<25 && intbmi>18){
            bmicategory.setText("Normal");
            bmiimageview.setImageResource(R.drawable.greentick);


        }
        else if(intbmi<29 && intbmi>25){
            bmicategory.setText("Overweight");
            bmiimageview.setImageResource(R.drawable.incorrect);


        }
        else{
            bmicategory.setText("Obese");
            bmiimageview.setImageResource(R.drawable.incorrect);

        }

        bmigender.setText(intent.getStringExtra("gender"));
        bmidisplay.setText(mbmi);







    }
}
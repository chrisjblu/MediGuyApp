package com.example.mediguyapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CheckBmiActivity extends AppCompatActivity {

    android.widget.Button calculatebmi;

    TextView mheight;
    TextView mcurrentdage, mcurrentweight;
    ImageView plusage, minusage, plusweight, minusweight;
    SeekBar mseekbarheight;
    RelativeLayout mmale, mfemale;

    int intweight=22;
    int intage = 22;
    int currentp;
    String mprogress= "180";
    String typeuser = "0";
    String weight2="11";
    String age2="22";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_check_bmi);
        getSupportActionBar().hide();

        calculatebmi = findViewById(R.id.bmical);
        mcurrentdage = findViewById(R.id.cage);
        mcurrentweight = findViewById(R.id.cweight);
        mheight = findViewById(R.id.cheight);
        minusage = findViewById(R.id.minusage);
        plusage = findViewById(R.id.plusage);
        minusweight = findViewById(R.id.minusweight);
        plusweight = findViewById(R.id.plusweight);
        mseekbarheight= findViewById(R.id.seekbarh);
        mmale = findViewById(R.id.male);
        mfemale = findViewById(R.id.female);

        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.male));
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malenonfc));
                typeuser="Male";


            }
        });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.male));
                mmale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.malenonfc));
                ///type of user
                typeuser="Female";


            }
        });

        ///setting the max for the seekbar
        mseekbarheight.setMax(300);
        mseekbarheight.setProgress(170);
        mseekbarheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentp = progress;
                mprogress=String.valueOf(currentp);
                mheight.setText(mprogress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        plusage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage=intage+1;
                age2=String.valueOf(intage);
                mcurrentdage.setText(age2);

            }
        });

        minusage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intage=intage-1;
                age2=String.valueOf(intage);
                mcurrentdage.setText(age2);

            }
        });

        plusweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intweight=intweight+1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);

            }
        });

        minusweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intweight=intweight-1;
                weight2=String.valueOf(intweight);
                mcurrentweight.setText(weight2);

            }
        });






        calculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /// if the user does not pick a gender
                if(typeuser.equals("0")){
                    Toast.makeText(getApplicationContext(),"Select Gender",Toast.LENGTH_SHORT).show();
                }
                //if the user height is 0
                else if(mprogress.equals("0")){
                    Toast.makeText(getApplicationContext(),"Select Height",Toast.LENGTH_SHORT).show();

                }
                else if(intage==0 || intage<0){
                    Toast.makeText(getApplicationContext(),"Age Cannot be this",Toast.LENGTH_SHORT).show();

                }
                else if (intweight ==0 || intweight<0){
                    Toast.makeText(getApplicationContext(),"Weight cannot be this",Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent intent = new Intent(CheckBmiActivity.this,bmicalActivity.class);
                    intent.putExtra("gender", typeuser);
                    intent.putExtra("height", mprogress);
                    intent.putExtra("weight", weight2);
                    intent.putExtra("age", age2);
                    startActivity(intent);
                }


            }
        });


    }
}
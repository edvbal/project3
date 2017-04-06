package com.example.android.project3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Edvinas on 04/04/2017.
 */



public class FinalScore extends AppCompatActivity{
    ImageView finalImage;
    TextView finalScore;
    TextView finalMessage;
    Button finalLeft;
    Button finalRight;
    int finalScores;
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.final_score);
        finalImage = (ImageView) findViewById(R.id.finalImage);
        finalScore = (TextView) findViewById(R.id.finalScore);
        finalMessage = (TextView) findViewById(R.id.finalText);
        finalLeft = (Button) findViewById(R.id.finalButtonLeft);
        finalLeft.setText(R.string.toMenu);
        finalRight = (Button) findViewById(R.id.finalButtonRight);
        finalRight.setText(R.string.share);
        SharedPreferences sharedPref = getApplication().getSharedPreferences("score", Context.MODE_PRIVATE);
        finalScores = sharedPref.getInt("score", 0);
        if (finalScores > 7){
            finalImage.setImageResource(R.drawable.android_high);
            finalScore.setText(Integer.toString(finalScores));
            finalMessage.setText(R.string.finalHigh);
        }
        else if (finalScores < 8 && finalScores > 4){
            finalImage.setImageResource(R.drawable.android_mid);
            finalScore.setText(Integer.toString(finalScores));
            finalMessage.setText(R.string.finalMed);
        }
        else if (finalScores < 5){
            finalImage.setImageResource(R.drawable.android_low);
            finalScore.setText(Integer.toString(finalScores));
            finalMessage.setText(R.string.finalLow);
        }
        finalRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout my Android Development quiz score !");
                intent.putExtra(Intent.EXTRA_TEXT, "I have scored " + Integer.toString(finalScores));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

                Toast.makeText(getApplicationContext(),"Not implemented",Toast.LENGTH_SHORT).show();
            }
        });
        finalLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

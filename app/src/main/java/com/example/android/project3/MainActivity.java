package com.example.android.project3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText enterName;
    Button startQuizz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferencesName = getSharedPreferences("name", 0);
        preferencesName.edit().remove("name").apply();
        SharedPreferences preferencesScore = getSharedPreferences("score", 0);
        preferencesScore.edit().remove("score").apply();

        enterName = (EditText) findViewById(R.id.enterName);
        startQuizz = (Button) findViewById(R.id.startQuizz);

    }

    public void startQuizz(View view){
        if (enterName.getText().toString().isEmpty()){
            Toast.makeText(this,"First enter your name",Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences name = this.getSharedPreferences("name", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = name.edit();
            edit.putString("name", enterName.getText().toString());
            edit.apply();

            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);


        }
    }
    public void test(View view){
        int test1 = 0;
        int test2 = 1;
        Log.d("TestValue",Integer.toString(test1++ + ++test2));

        test1 = test2 + test1;
    }
}

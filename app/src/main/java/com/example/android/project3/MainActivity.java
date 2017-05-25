package com.example.android.project3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText enterName; // @enterName EditText field to store user name input
    Button startQuizz;  // @startQuizz Button that will start QuizActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create name and score variables that will be stored in internal storage
        SharedPreferences preferencesName = getSharedPreferences("name", 0);
        preferencesName.edit().remove("name").apply();
        SharedPreferences preferencesScore = getSharedPreferences("score", 0);
        preferencesScore.edit().remove("score").apply();
        SharedPreferences preferencesQuestion = getSharedPreferences("score", 0);
        preferencesQuestion.edit().remove("question").apply();
        // Find Views in XML for entering name edit text and start quizz button
        enterName = (EditText) findViewById(R.id.enterName);
        startQuizz = (Button) findViewById(R.id.startQuizz);
    }
    //----------------------------------------------------------------------------------------------
    // @startQuizz method that sets user name and question number variables starts QuizActivity
    //----------------------------------------------------------------------------------------------
    public void startQuizz(View view) {
        // if statement checks if edit text field for name is not empty
        if (enterName.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.firstEnterName, Toast.LENGTH_SHORT).show();
        } else {
            // user name input will be stored as name in internal storage
            SharedPreferences name = this.getSharedPreferences("name", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = name.edit();
            edit.putString("name", enterName.getText().toString());
            edit.apply();
            // question number is set to 0 and stored as question in internal storage
            SharedPreferences question = this.getSharedPreferences("question", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit2 = question.edit();
            edit2.putInt("question", 0);
            edit2.apply();
            Toast.makeText(this,getString(R.string.goodLuck)+enterName.getText().toString(),Toast.LENGTH_SHORT).show();
            // starts QuizActivity
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        }
    }
    //----------------------------------------------------------------------------------------------
}

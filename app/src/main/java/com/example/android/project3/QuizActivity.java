package com.example.android.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Edvinas on 05/04/2017.
 */

public class QuizActivity extends AppCompatActivity {


    TextView question;
    ImageView image;
    RadioButton[] answersRadio;
    RadioButton answer1;
    RadioButton answer2;
    RadioButton answer3;
    RadioButton answer4;
    Button left;
    Button right;

    int questionNumber;
    int score;
    int[] questions = new int[]{R.string.question1, R.string.question2, R.string.question3, R.string.question4,
            R.string.question5, R.string.question6, R.string.question7, R.string.question8, R.string.question9, R.string.question10};

    int[] answers = new int[]{R.string.answer1_1, R.string.answer1_2, R.string.answer1_3, R.string.answer1_4,
            R.string.answer2_1, R.string.answer2_2, R.string.answer2_3, R.string.answer2_4,
            R.string.answer3_1, R.string.answer3_2, R.string.answer3_3, R.string.answer3_4,
            R.string.answer4_1, R.string.answer4_2, R.string.answer4_3, R.string.answer4_4,
            R.string.answer5_1, R.string.answer5_2, R.string.answer5_3, R.string.answer5_4,
            R.string.answer6_1, R.string.answer6_2, R.string.answer6_3, R.string.answer6_4,
            R.string.answer7_1, R.string.answer7_2, R.string.answer7_3, R.string.answer7_4,
            R.string.answer8_1, R.string.answer8_2, R.string.answer8_3, R.string.answer8_4,
            R.string.answer9_1, R.string.answer9_2, R.string.answer9_3, R.string.answer9_4,
            R.string.answer10_1, R.string.answer10_2, R.string.answer10_3, R.string.answer10_4,};

    int[] rightAnswers = new int[]{R.string.answer1_1, R.string.answer2_1, R.string.answer3_2, R.string.answer4_1, R.string.answer5_3,
            R.string.answer6_2, R.string.answer7_1, R.string.answer8_3, R.string.answer9_1, R.string.answer10_3};

    int[] rightAnswers2 = new int[]{0, 0, 1, 0, 2, 1, 0, 2, 0, 2};

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_quiz);

        questionNumber = 0;
        score = 0;
        question = (TextView) findViewById(R.id.question);
        answersRadio = new RadioButton[]{(RadioButton) findViewById(R.id.answer1),
                (RadioButton) findViewById(R.id.answer2),
                (RadioButton) findViewById(R.id.answer3),
                (RadioButton) findViewById(R.id.answer4)
        };
        image = (ImageView) findViewById(R.id.image);
        left = (Button) findViewById(R.id.buttonLeft);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber == 0) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (questionNumber > 0 && questionNumber < 9) {
                    questionNumber--;
                    showQuiz();
                }

            }
        });
        right = (Button) findViewById(R.id.buttonRight);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber >= 0 && questionNumber < 9) {
                    if (answersRadio[rightAnswers2[questionNumber]].isChecked()){
                        score++;
                        Toast.makeText(getApplicationContext(), Integer.toString(score), Toast.LENGTH_SHORT).show();
                    }
                    questionNumber++;
                    showQuiz();
                }
                else if (questionNumber == 9) {

                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                }
            }
        });

        showQuiz();


        //String[] questions =
    }

    private void showQuiz() {
        //cardView.findViewById(R.id.question).tex;
        int tempRadio = 0;
        question.setText(questions[questionNumber]);
        if (questionNumber == 0) {
            left.setText(R.string.toMenu);
            right.setText(R.string.nextQuestion);
            for (int i = questionNumber; i < questionNumber + 3; i++) {

                answersRadio[tempRadio].setText(answers[i]);
                tempRadio++;
            }

        } else if (questionNumber > 0 && questionNumber < 9) {
            left.setText(R.string.prevQuestion);
            right.setText(R.string.nextQuestion);
            for (int i = questionNumber * 4; i <= questionNumber * 4 + 3; i++) {
                answersRadio[tempRadio].setText(answers[i]);
                tempRadio++;
            }
        } else if (questionNumber == 9) {
            left.setText(R.string.prevQuestion);
            right.setText(R.string.finishQuiz);
        }
    }
}

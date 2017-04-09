package com.example.android.project3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Edvinas on 05/04/2017.
 */

public class QuizActivity extends AppCompatActivity {


    RadioButton[] answersRadio;         // RadioButton array that will hold answers
    CheckBox[] answersCheckBox;         // CheckBox array that will hold answers
    EditText insertedAnswer;            // EditText which will store user's input for answer

    CardView cardViewRadioB;             // @cardViewRadioB for radio button type questions
    CardView cardViewCheckB;             // @cardViewCheckB for checkbox button type questions
    CardView cardViewRadioInsertText;    // @cardViewRadioInsertText for input type questions
    CardView cardViewFinalScore;         // @cardViewFinalScore widget for showing final score

    int questionNumber;                  // @questionNumber stores question number
    double scores;                       // @scores stores user's score

    int[] questions = new int[]{         // @questions stores all the questions
            R.string.question1, R.string.question2, R.string.question3,
            R.string.question4, R.string.question5, R.string.question6,
            R.string.question7, R.string.question8, R.string.question9, R.string.question10};

    int[] answersRadioB = new int[]{      // @answersRadioB stores answers for radio button type questions
            R.string.answer1_1, R.string.answer1_2, R.string.answer1_3, R.string.answer1_4,
            0, 0, 0, 0,                   // 0000 inputs are for different type questions, not needed here
            R.string.answer3_1, R.string.answer3_2, R.string.answer3_3, R.string.answer3_4,
            R.string.answer4_1, R.string.answer4_2, R.string.answer4_3, R.string.answer4_4,
            R.string.answer5_1, R.string.answer5_2, R.string.answer5_3, R.string.answer5_4,
            R.string.answer6_1, R.string.answer6_2, R.string.answer6_3, R.string.answer6_4,
            R.string.answer7_1, R.string.answer7_2, R.string.answer7_3, R.string.answer7_4,
            R.string.answer8_1, R.string.answer8_2, R.string.answer8_3, R.string.answer8_4,
            0, 0, 0, 0,
            R.string.answer10_1, R.string.answer10_2, R.string.answer10_3, R.string.answer10_4
    };
    int[] answersCheckB = new int[]{      // @answersCheckB stores answers for checkbox  type questions
            R.string.answer2_1, R.string.answer2_2, R.string.answer2_3, R.string.answer2_4
    };

    String answerInsertText;      // @answerInsertText holds user inserted answer
    int[] correctAnswersRadioB;   // @correctAnswersRadioB stores correct answers for radio button type questions
    int[] correctAnswersCheckB;   // @correctAnswersCheckB stores correct answers for checkbox type question
    String correctAnswersInsertT; // @correctAnswersInsertT stores correct answers for user insert type question

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_quiz);
        // Put string resources into @correctAnswersRadioB
        //------------------------------------------------------------------------------------------
        correctAnswersRadioB = new int[]{
                this.getResources().getInteger(R.integer.correctAnswer1), 0,
                this.getResources().getInteger(R.integer.correctAnswer3),
                this.getResources().getInteger(R.integer.correctAnswer4),
                this.getResources().getInteger(R.integer.correctAnswer5),
                this.getResources().getInteger(R.integer.correctAnswer6),
                this.getResources().getInteger(R.integer.correctAnswer7),
                this.getResources().getInteger(R.integer.correctAnswer8), 0,
                this.getResources().getInteger(R.integer.correctAnswer10)
        };
        //------------------------------------------------------------------------------------------
        // Put string resources into @correctAnswersCheckB
        // -----------------------------------------------------------------------------------------
        correctAnswersCheckB = new int[]{
                this.getResources().getInteger(R.integer.correctAnswer2),
                this.getResources().getInteger(R.integer.correctAnswer2_1)
        };
        // -----------------------------------------------------------------------------------------
        // Put string resources into @correctAnswersInsertT
        correctAnswersInsertT = this.getString(R.string.correctAnswer9);
        // -----------------------------------------------------------------------------------------
        // Reset user score
        // -----------------------------------------------------------------------------------------
        scores = 0;
        // -----------------------------------------------------------------------------------------
        // Find views in the XML resources
        //------------------------------------------------------------------------------------------
        cardViewCheckB = (CardView) findViewById(R.id.cardViewCheckB);
        cardViewRadioB = (CardView) findViewById(R.id.cardViewRadioB);
        cardViewRadioInsertText = (CardView) findViewById(R.id.cardViewInsertText);
        cardViewFinalScore = (CardView) findViewById(R.id.cardViewFinalScore);
        insertedAnswer = (EditText) cardViewRadioInsertText.findViewById(R.id.insertAnswer);
        //------------------------------------------------------------------------------------------
        // @showQuiz starts showing questions
        //------------------------------------------------------------------------------------------
        showQuiz();
        //------------------------------------------------------------------------------------------
    }
    //----------------------------------------------------------------------------------------------
    // @showQuiz initiates and fills up CardViews for all the questions
    //----------------------------------------------------------------------------------------------
    private void showQuiz() {
        int tempRadio = 0;
        SharedPreferences sharedPref = getApplication().getSharedPreferences("question", Context.MODE_PRIVATE);
        questionNumber = sharedPref.getInt("question", 0);

        Button right, left;
        TextView question;
        ImageView image;
        /**
         * Show quiz window for check box answers
         */
        if (questionNumber == 1) {
            cardViewRadioB.setVisibility(View.INVISIBLE);
            cardViewRadioInsertText.setVisibility(View.INVISIBLE);
            cardViewCheckB.setVisibility(View.VISIBLE);
            cardViewFinalScore.setVisibility(View.INVISIBLE);

            question = (TextView) cardViewCheckB.findViewById(R.id.questionCheckB);
            image = (ImageView) cardViewCheckB.findViewById(R.id.imageCheckB);
            right = (Button) cardViewCheckB.findViewById(R.id.buttonRightCheckB);
            left = (Button) cardViewCheckB.findViewById(R.id.buttonLeftCheckB);
            question.setText(questions[questionNumber]);
            right.setText(R.string.nextQuestion);
            left.setText(R.string.prevQuestion);
            image.setImageResource(R.drawable.android_quiz);
            answersCheckBox = new CheckBox[]{
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB1),
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB2),
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB3),
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB4)
            };
            for (int i = questionNumber * 0; i < questionNumber * 0 + 4; i++) {
                answersCheckBox[tempRadio].setText(answersCheckB[i]);
                tempRadio++;
            }
        }
        /**
         * show quiz window for text insertion answer
         */
        else if (questionNumber == 8) {
            cardViewRadioB.setVisibility(View.INVISIBLE);
            cardViewRadioInsertText.setVisibility(View.VISIBLE);
            cardViewCheckB.setVisibility(View.INVISIBLE);
            question = (TextView) cardViewRadioInsertText.findViewById(R.id.questionInsertText);
            image = (ImageView) cardViewRadioInsertText.findViewById(R.id.imageInsertText);
            right = (Button) cardViewRadioInsertText.findViewById(R.id.buttonRightInsertText);
            left = (Button) cardViewRadioInsertText.findViewById(R.id.buttonLeftInsertText);
            question.setText(questions[questionNumber]);
            right.setText(R.string.nextQuestion);
            left.setText(R.string.prevQuestion);
            image.setImageResource(R.drawable.android_quiz);
        }
        /**
         * show quiz window for radio button answer
         */
        else {
            /**
             * Assigning objects to CardView layout items for RadioButtons type of layout.
             */
            cardViewRadioB.setVisibility(View.VISIBLE);
            cardViewRadioInsertText.setVisibility(View.INVISIBLE);
            cardViewCheckB.setVisibility(View.INVISIBLE);
            cardViewFinalScore.setVisibility(View.INVISIBLE);

            question = (TextView) cardViewRadioB.findViewById(R.id.questionRadioB);
            image = (ImageView) cardViewRadioB.findViewById(R.id.imageRadioB);
            right = (Button) cardViewRadioB.findViewById(R.id.buttonRightRadioB);
            left = (Button) cardViewRadioB.findViewById(R.id.buttonLeftRadioB);
            /**
             * Making sure questions that require different image or button labels will get them
             */
            if (questionNumber == 0) {
                image.setImageResource(R.drawable.android);
                right.setText(R.string.nextQuestion);
                left.setText(R.string.toMenu);
            } else if (questionNumber == 5) {
                image.setImageResource(R.drawable.question6);
                right.setText(R.string.nextQuestion);
                left.setText(R.string.prevQuestion);
            } else if (questionNumber == 6) {
                image.setImageResource(R.drawable.question7);
                right.setText(R.string.nextQuestion);
                left.setText(R.string.prevQuestion);
            } else if (questionNumber == 9) {
                right.setText(R.string.finishQuiz);
                left.setText(R.string.prevQuestion);
            } else {
                right.setText(R.string.nextQuestion);
                left.setText(R.string.prevQuestion);
                image.setImageResource(R.drawable.android);

            }
            /**
             * Creating RadioButton array to hold answers
             */
            answersRadio = new RadioButton[]{
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB1),
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB2),
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB3),
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB4)
            };

            /**
             * Setting up RadioButtons answers
             */
            question.setText(questions[questionNumber]);
            for (int i = questionNumber * 4; i < questionNumber * 4 + 4; i++) {
                answersRadio[tempRadio].setText(answersRadioB[i]);
                tempRadio++;
            }

        }
    }
    //----------------------------------------------------------------------------------------------

    /**
     * Right button on click listener that will finish the quiz if its the last question
     * or send to next question otherwise.
     */

    public void rightClick(View view) {
        if (questionNumber >= 0 && questionNumber < 9) {
            if (questionNumber != 1 && questionNumber != 8) {
                if (answersRadio[correctAnswersRadioB[questionNumber]].isChecked()) {
                    scores++;
                    Toast.makeText(getApplicationContext(), Double.toString(scores), Toast.LENGTH_SHORT).show();

                }
            } else if (questionNumber == 1) {
                if (answersCheckBox[0].isChecked() && answersCheckBox[1].isChecked() &&
                        answersCheckBox[2].isChecked() && answersCheckBox[3].isChecked()) {
                    Toast.makeText(this, "You can't select all the options !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (answersCheckBox[0].isChecked() && answersCheckBox[3].isChecked()) {
                    scores++;
                    Toast.makeText(getApplicationContext(), Double.toString(scores), Toast.LENGTH_SHORT).show();
                } else if (answersCheckBox[0].isChecked() || answersCheckBox[3].isChecked()) {
                    scores += 0.5;
                    Toast.makeText(getApplicationContext(), Double.toString(scores), Toast.LENGTH_SHORT).show();
                }
            } else if (questionNumber == 8) {
                answerInsertText = insertedAnswer.getText().toString();
                if (answerInsertText.equals(correctAnswersInsertT)) {
                    scores++;
                    Toast.makeText(getApplicationContext(), Double.toString(scores), Toast.LENGTH_SHORT).show();
                }
            }

            SharedPreferences question = getApplicationContext().getSharedPreferences("question", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = question.edit();
            edit.putInt("question", ++questionNumber);
            edit.apply();
            showQuiz();
        } else if (questionNumber == 9) {
            if (answersRadio[correctAnswersRadioB[questionNumber]].isChecked()) {
                scores++;
                Toast.makeText(getApplicationContext(), Double.toString(scores), Toast.LENGTH_SHORT).show();
            }
            finalScore();
        }
    }

    /**
     * Left button on click listener that will send back to menu if its the first question
     * or send back to the previous question otherwise
     */

    public void leftClick(View view) {
        if (questionNumber == 0) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (questionNumber > 0 && questionNumber < 9) {
            SharedPreferences question = getApplicationContext().getSharedPreferences("question", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = question.edit();
            edit.putInt("question", --questionNumber);
            edit.apply();
            showQuiz();
        }
    }

    public void finalScore() {
        Button finalRight, finalLeft;
        TextView finalScore, finalMessage;
        ImageView finalImage;
        cardViewRadioB.setVisibility(View.INVISIBLE);
        cardViewRadioInsertText.setVisibility(View.INVISIBLE);
        cardViewCheckB.setVisibility(View.INVISIBLE);
        cardViewFinalScore.setVisibility(View.VISIBLE);
        finalImage = (ImageView) cardViewFinalScore.findViewById(R.id.finalImage);
        finalScore = (TextView) cardViewFinalScore.findViewById(R.id.finalScore);
        finalMessage = (TextView) cardViewFinalScore.findViewById(R.id.finalText);
        finalLeft = (Button) cardViewFinalScore.findViewById(R.id.finalButtonLeft);
        finalRight = (Button) cardViewFinalScore.findViewById(R.id.finalButtonRight);
        finalLeft.setText(R.string.toMenu);
        finalRight.setText(R.string.share);
        if (scores > 7) {
            finalImage.setImageResource(R.drawable.android_high);
            finalScore.setText(Double.toString(scores));
            finalMessage.setText(R.string.finalHigh);
        } else if (scores < 8 && scores > 4) {
            finalImage.setImageResource(R.drawable.android_mid);
            finalScore.setText(Double.toString(scores));
            finalMessage.setText(R.string.finalMed);
        } else if (scores < 5) {
            finalImage.setImageResource(R.drawable.android_low);
            finalScore.setText(Double.toString(scores));
            finalMessage.setText(R.string.finalLow);
        }
    }

    public void backToMenuButton(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void shareButton(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout my Android Development quiz score !");
        intent.putExtra(Intent.EXTRA_TEXT, "I have scored " + Double.toString(scores));
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

}



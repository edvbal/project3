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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.project3.R.id.finalImage;

/**
 * Created by Edvinas on 05/04/2017.
 */

public class QuizActivity extends AppCompatActivity {
    // String variables that will store key to save instance
    public static final String SAVE_SCORE = "saveScore";
    public static final String SAVE_CURRENT_QUESTION = "save_current_question";
    public static final String SAVE_FINAL_SCORE = "saveFinalScore";
    //----------------------------------------------------------------------------------------------
    RadioButton[] answersRadio;    // RadioButton array that will hold answers
    CheckBox[] answersCheckBox;    // CheckBox array that will hold answers
    EditText insertedAnswer;       // EditText which will store user's input for answer
    RadioGroup radioGroup;         // RadioGroup of radio buttons
    //----------------------------------------------------------------------------------------------
    CardView cardViewRadioB;       // @cardViewRadioB for radio button type answers
    CardView cardViewCheckB;       // @cardViewCheckB for checkbox button type answers
    CardView cardViewInsertText;   // @cardViewInsertText for input type answers
    CardView cardViewFinalScore;   // @cardViewFinalScore widget for showing final score
    //----------------------------------------------------------------------------------------------
    Button right, left;            // @right,@left Buttons that will be used in CardViews
    TextView question;             // @question TextView that will be used in CardViews
    TextView finalScoreTextView, finalText; // @finalScoreText,@finalText TextView used in final screen
    TextView showQuestionNumber;            // @showQuestionNumber shows question number
    //----------------------------------------------------------------------------------------------
    ImageView image;               // @image ImageView that will be used in CardViews
    //----------------------------------------------------------------------------------------------
    int questionNumber;            // @questionNumber stores question number
    //----------------------------------------------------------------------------------------------
    int[] questions = new int[]{   // @questions stores all the questions
            R.string.question1, R.string.question2, R.string.question3,
            R.string.question4, R.string.question5, R.string.question6,
            R.string.question7, R.string.question8, R.string.question9, R.string.question10};
    //----------------------------------------------------------------------------------------------
    // @answersRadioB stores answers for radio button type answers.
    int[] answersRadioB = new int[]{
            R.string.answer1_1, R.string.answer1_2, R.string.answer1_3, R.string.answer1_4,
            0, 0, 0, 0,            // 0000 inputs are for different type questions, not needed here.
            R.string.answer3_1, R.string.answer3_2, R.string.answer3_3, R.string.answer3_4,
            R.string.answer4_1, R.string.answer4_2, R.string.answer4_3, R.string.answer4_4,
            R.string.answer5_1, R.string.answer5_2, R.string.answer5_3, R.string.answer5_4,
            R.string.answer6_1, R.string.answer6_2, R.string.answer6_3, R.string.answer6_4,
            R.string.answer7_1, R.string.answer7_2, R.string.answer7_3, R.string.answer7_4,
            R.string.answer8_1, R.string.answer8_2, R.string.answer8_3, R.string.answer8_4,
            0, 0, 0, 0,
            R.string.answer10_1, R.string.answer10_2, R.string.answer10_3, R.string.answer10_4
    };
    //----------------------------------------------------------------------------------------------
    // @answersCheckB stores answers for checkbox  type questions.
    int[] answersCheckB = new int[]{
            R.string.answer2_1, R.string.answer2_2, R.string.answer2_3, R.string.answer2_4
    };
    String answerInsertText;      // @answerInsertText holds user inserted answer
    //----------------------------------------------------------------------------------------------
    // @correctAnswersRadioB stores correct answers for radio button type questions.
    // @correctAnswersCheckB stores correct answers for checkbox type question.
    //----------------------------------------------------------------------------------------------
    int[] correctAnswersRadioB, correctAnswersCheckB;
    //----------------------------------------------------------------------------------------------
    // @correctAnswersInsertT stores correct answers for user insert type question.
    String correctAnswersInsertT;
    // @questionScores stores score for each question answered.
    double[] questionScores = new double[10];
    // @finalScoreText stores final score.
    Double finalScore;
    // @questionNumberText will store question number text
    String questionNumberText;
    // @finalScoreText will store final score text
    String finalScoreText;
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_quiz);
        // Reset user score.
        for (int i = 0; i < questionScores.length; i++)
            questionScores[i] = 0.0;
        finalScore = 0.0;
        // Save the user's current game state
        if (savedInstance != null) {
            questionScores = savedInstance.getDoubleArray(SAVE_SCORE);
            questionNumber = savedInstance.getInt(SAVE_CURRENT_QUESTION, questionNumber);
            finalScore = savedInstance.getDouble(SAVE_FINAL_SCORE, finalScore);
        }
        // Put string resources into @correctAnswersRadioB.
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
        // Put string resources into @correctAnswersCheckB, @correctAnswersInsertT.
        correctAnswersCheckB = new int[]{
                this.getResources().getInteger(R.integer.correctAnswer2),
                this.getResources().getInteger(R.integer.correctAnswer2_1)
        };
        correctAnswersInsertT = this.getString(R.string.correctAnswer9);
        // Find views in the XML resources.
        cardViewCheckB = (CardView) findViewById(R.id.cardViewCheckB);
        cardViewRadioB = (CardView) findViewById(R.id.cardViewRadioB);
        cardViewInsertText = (CardView) findViewById(R.id.cardViewInsertText);
        cardViewFinalScore = (CardView) findViewById(R.id.cardViewFinalScore);
        insertedAnswer = (EditText) cardViewInsertText.findViewById(R.id.insertAnswer);
        // @showQuiz starts showing questions if final score wasn't already shown.
        if (questionNumber == 11) {
            finalScore();
        } else
            showQuiz();
    }

    //----------------------------------------------------------------------------------------------
    // Save state when rotating
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putDoubleArray(SAVE_SCORE, questionScores);
        savedInstanceState.putInt(SAVE_CURRENT_QUESTION, questionNumber);
        savedInstanceState.putDouble(SAVE_FINAL_SCORE, finalScore);
        super.onSaveInstanceState(savedInstanceState);
    }

    //----------------------------------------------------------------------------------------------
    // Restore state when rotating
    //----------------------------------------------------------------------------------------------
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getDoubleArray(SAVE_SCORE);
        savedInstanceState.getInt(SAVE_CURRENT_QUESTION);
        savedInstanceState.getDouble(SAVE_FINAL_SCORE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    //----------------------------------------------------------------------------------------------
    // Makes physical back button work correctly when going back to previous questions
    @Override
    public void onBackPressed() {
        if (questionNumber > 0 && questionNumber < 9) {
            SharedPreferences question = getApplicationContext().getSharedPreferences(
                    "question", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = question.edit();
            edit.putInt("question", --questionNumber);
            edit.apply();
            showQuiz();
        }
    }

    //----------------------------------------------------------------------------------------------
    // @showQuiz() initiates and fills up CardViews for all the questions.
    //----------------------------------------------------------------------------------------------
    private void showQuiz() {
        int tempIndex = 0;// @tempIndex temp index to cycle through CheckBox and RadioButton arrays.
        //------------------------------------------------------------------------------------------
        // @questionNumber gets its number from internal storage
        SharedPreferences sharedPref = getApplication().getSharedPreferences(
                "question", Context.MODE_PRIVATE);
        questionNumber = sharedPref.getInt("question", 0);
        //------------------------------------------------------------------------------------------
        // Show quiz window for question 2 which has check box type answers.
        //------------------------------------------------------------------------------------------
        if (questionNumber == 1) {
            // Making CheckBox CardView visible and rest invisible.
            cardViewRadioB.setVisibility(View.INVISIBLE);
            cardViewInsertText.setVisibility(View.INVISIBLE);
            cardViewCheckB.setVisibility(View.VISIBLE);
            cardViewFinalScore.setVisibility(View.INVISIBLE);
            // Find Views from XML.
            question = (TextView) cardViewCheckB.findViewById(R.id.questionCheckB);
            image = (ImageView) cardViewCheckB.findViewById(R.id.imageCheckB);
            right = (Button) cardViewCheckB.findViewById(R.id.buttonRightCheckB);
            left = (Button) cardViewCheckB.findViewById(R.id.buttonLeftCheckB);
            showQuestionNumber = (TextView) cardViewCheckB.findViewById(R.id.questionNumberCheckB);
            // Setting question text, buttons text and image.
            question.setText(questions[questionNumber]);
            right.setText(R.string.nextQuestion);
            left.setText(R.string.prevQuestion);
            image.setImageResource(R.mipmap.android_quiz);
            int tmp = questionNumber + 1;
            questionNumberText = getString(R.string.questionNumber) + " " + Integer.toString(tmp);
            showQuestionNumber.setText(questionNumberText);
            // Creating CheckBox array and find CheckBox Views from XML.
            answersCheckBox = new CheckBox[]{
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB1),
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB2),
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB3),
                    (CheckBox) cardViewCheckB.findViewById(R.id.answerCheckB4)
            };
            // Setting answers for checkboxes. For loop to 4 because there are 4 check boxes.
            for (int i = 0; i < 4; i++) {
                answersCheckBox[tempIndex].setText(answersCheckB[i]);
                tempIndex++;
            }
        }
        //------------------------------------------------------------------------------------------
        // Show quiz window for question 9 which has answer insertion.
        //------------------------------------------------------------------------------------------
        else if (questionNumber == 8) {
            // Making InsertText CardView visible and rest invisible.
            cardViewRadioB.setVisibility(View.INVISIBLE);
            cardViewInsertText.setVisibility(View.VISIBLE);
            cardViewCheckB.setVisibility(View.INVISIBLE);
            cardViewFinalScore.setVisibility(View.INVISIBLE);
            // Find Views from XML.
            question = (TextView) cardViewInsertText.findViewById(R.id.questionInsertText);
            image = (ImageView) cardViewInsertText.findViewById(R.id.imageInsertText);
            right = (Button) cardViewInsertText.findViewById(R.id.buttonRightInsertText);
            left = (Button) cardViewInsertText.findViewById(R.id.buttonLeftInsertText);
            showQuestionNumber = (TextView) cardViewInsertText.findViewById(R.id.questionNumberInsertText);
            // Setting question text, buttons text and image.
            question.setText(questions[questionNumber]);
            right.setText(R.string.nextQuestion);
            left.setText(R.string.prevQuestion);
            image.setImageResource(R.mipmap.android_quiz);
            int tmp = questionNumber + 1;
            questionNumberText = getString(R.string.questionNumber) + " " + Integer.toString(tmp);
            showQuestionNumber.setText(questionNumberText);
        }
        //------------------------------------------------------------------------------------------
        // Show quiz window for all the rest questions which has RadioButton type answers.
        //------------------------------------------------------------------------------------------
        else {
            // Making RadioButton CardView visible and rest invisible
            cardViewRadioB.setVisibility(View.VISIBLE);
            cardViewInsertText.setVisibility(View.INVISIBLE);
            cardViewCheckB.setVisibility(View.INVISIBLE);
            cardViewFinalScore.setVisibility(View.INVISIBLE);
            // Find Views from XML.
            question = (TextView) cardViewRadioB.findViewById(R.id.questionRadioB);
            image = (ImageView) cardViewRadioB.findViewById(R.id.imageRadioB);
            right = (Button) cardViewRadioB.findViewById(R.id.buttonRightRadioB);
            left = (Button) cardViewRadioB.findViewById(R.id.buttonLeftRadioB);
            showQuestionNumber = (TextView) cardViewRadioB.findViewById(R.id.questionNumberRadioB);
            radioGroup = (RadioGroup) cardViewRadioB.findViewById(R.id.radioGroup);
            // Making sure questions that require different image or button labels will get them.
            int tmp = questionNumber + 1;
            questionNumberText = getString(R.string.questionNumber) + " " + Integer.toString(tmp);
            showQuestionNumber.setText(questionNumberText);
            if (questionNumber == 0) {
                image.setImageResource(R.mipmap.android_quiz);
                right.setText(R.string.nextQuestion);
                left.setText(R.string.toMenu);
            } else if (questionNumber == 5) {
                image.setImageResource(R.mipmap.question6);
                right.setText(R.string.nextQuestion);
                left.setText(R.string.prevQuestion);
            } else if (questionNumber == 6) {
                image.setImageResource(R.mipmap.question7);
                right.setText(R.string.nextQuestion);
                left.setText(R.string.prevQuestion);
            } else if (questionNumber == 9) {
                right.setText(R.string.finishQuiz);
                left.setText(R.string.prevQuestion);
            } else {
                right.setText(R.string.nextQuestion);
                left.setText(R.string.prevQuestion);
                image.setImageResource(R.mipmap.android_quiz);
            }
            // Creating RadioButton array and find RadioButton Views from XML.
            answersRadio = new RadioButton[]{
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB1),
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB2),
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB3),
                    (RadioButton) cardViewRadioB.findViewById(R.id.answerRadioB4)
            };
            radioGroup.clearCheck();
            // Setting answers for radio buttons. For loop to 4 because there are 4 radio buttons.
            question.setText(questions[questionNumber]);
            for (int i = questionNumber * 4; i < questionNumber * 4 + 4; i++) {
                answersRadio[tempIndex].setText(answersRadioB[i]);
                tempIndex++;
            }

        }
        //--------------------------------------------------------------------------------------
    }

    //----------------------------------------------------------------------------------------------
    // @rightClick() Right button on click method that will finish the quiz if its the last question
    // or send to next question otherwise after increasing the score if answer input is correct.
    //----------------------------------------------------------------------------------------------
    public void rightClick(View view) {
        //------------------------------------------------------------------------------------------
        // if statement checks if it's first 9 questions, because the last question has different
        // logic to apply.
        //------------------------------------------------------------------------------------------
        if (questionNumber >= 0 && questionNumber < 9) {
            // if statement checks if its not the 2nd or 9th questions which are not radio button
            // answer questions. So the logic after this if statement applies to radio button
            // questions evaluating.
            if (questionNumber != 1 && questionNumber < 8) {
                int temp = 0;
                for (RadioButton button : answersRadio)
                    if (button.isChecked())
                        temp++;
                if (temp == 0) {
                    Toast.makeText(this, "Please, check atleast one answer !", Toast.LENGTH_SHORT).show();
                    return;
                }
                // if statement checks if correct answer is checked and increases score if so and
                // checks question answered.
                if (answersRadio[correctAnswersRadioB[questionNumber]].isChecked())
                    questionScores[questionNumber] = 1.0;

            }
            // else if statement checks if its 2nd question which is checkbox answer question.
            // So the logic after this if statement applies to user input question evaluating.
            else if (questionNumber == 1) {
                int temp = 0;
                for (CheckBox button : answersCheckBox)
                    if (button.isChecked())
                        temp++;
                if (temp == 0) {
                    Toast.makeText(this, "Please, check atleast one answer !", Toast.LENGTH_SHORT).show();
                    return;
                }
                //if statement checks if all answers is checked and if it is informs user to fix it.
                if (answersCheckBox[0].isChecked() && answersCheckBox[1].isChecked() &&
                        answersCheckBox[2].isChecked() && answersCheckBox[3].isChecked()) {
                    Toast.makeText(this,
                            "You can't select all the options !", Toast.LENGTH_SHORT).show();
                    return;
                }
                // else if statement checks if both correct answers is checked and gives 1 point and
                // checks question answered.
                else if (answersCheckBox[0].isChecked() && answersCheckBox[3].isChecked())
                    questionScores[questionNumber] = 1.0;

                //else if statement checks if either correct answer is checked and gives 0.5 points.
                // and checks question answered.
                else if (answersCheckBox[0].isChecked() || answersCheckBox[3].isChecked())
                    questionScores[questionNumber] = 0.5;

            }
            //--------------------------------------------------------------------------------------
            // else if statement checks if its 9th question which is user input answer question.
            // So the logic after this if statement applies to user input question evaluating.
            //--------------------------------------------------------------------------------------
            else if (questionNumber == 8) {
                // User input answer is getting stored into @answerInsertText String.
                answerInsertText = insertedAnswer.getText().toString();
                // If user input is correct score is increased by 1. Also checks if input isnt empty
                if(answerInsertText.isEmpty()){
                    Toast.makeText(this,"Please insert an answer !",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (answerInsertText.equals(correctAnswersInsertT)) {
                    questionScores[questionNumber] = 1.0;
                }
            }
            //--------------------------------------------------------------------------------------
            // After evaluating answer, question in internal storage is being increased and
            // @showQuiz() method is called.
            SharedPreferences question = getApplicationContext().getSharedPreferences(
                    "question", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = question.edit();
            edit.putInt("question", ++questionNumber);
            edit.apply();
            showQuiz();
        }
        //------------------------------------------------------------------------------------------
        // This else if statement checks if its the last question.
        //------------------------------------------------------------------------------------------
        else if (questionNumber == 9) {
            int temp = 0;
            for (RadioButton button : answersRadio)
                if (button.isChecked())
                    temp++;
            if (temp == 0) {
                Toast.makeText(this, "Please, check atleast one answer !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (answersRadio[correctAnswersRadioB[questionNumber]].isChecked())
                questionScores[questionNumber] = 1.0;
            for (int i = 0; i < questionScores.length; i++)
                finalScore += questionScores[i];
            finalScore();
        }
        //------------------------------------------------------------------------------------------
    }

    //----------------------------------------------------------------------------------------------
    // @leftClick() Left button on click method that will send back to menu if it's the first
    // question or send to the previous question.
    //----------------------------------------------------------------------------------------------
    public void leftClick(View view) {
        if (questionNumber == 0) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (questionNumber > 0 && questionNumber <= 9) {
            SharedPreferences question = getApplicationContext().getSharedPreferences(
                    "question", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = question.edit();
            edit.putInt("question", --questionNumber);
            edit.apply();
            showQuiz();
        }
    }

    //----------------------------------------------------------------------------------------------
    // @question() initiates and fills up CardViews for final score window.
    //----------------------------------------------------------------------------------------------
    public void finalScore() {
        // making question number 11 to avoid bugs after switching orientation
        questionNumber = 11;
        // Making final score CardView visible and rest invisible.
        cardViewRadioB.setVisibility(View.INVISIBLE);
        cardViewInsertText.setVisibility(View.INVISIBLE);
        cardViewCheckB.setVisibility(View.INVISIBLE);
        cardViewFinalScore.setVisibility(View.VISIBLE);
        // Find Views from XML.
        image = (ImageView) cardViewFinalScore.findViewById(finalImage);
        finalScoreTextView = (TextView) cardViewFinalScore.findViewById(R.id.finalScore);
        finalText = (TextView) cardViewFinalScore.findViewById(R.id.finalText);
        left = (Button) cardViewFinalScore.findViewById(R.id.finalButtonLeft);
        right = (Button) cardViewFinalScore.findViewById(R.id.finalButtonRight);
        // Setting buttons text.
        left.setText(R.string.toMenu);
        right.setText(R.string.share);
        // if statements check the score and depending on user's score, different texts and images
        // is displayed.
        if (finalScore > 7) {
            image.setImageResource(R.mipmap.android_high);
            finalScoreText = Double.toString(finalScore) + " " + getString(R.string.points);
            finalScoreTextView.setText(finalScoreText);
            finalText.setText(R.string.finalHigh);
        } else if (finalScore < 8 && finalScore > 4) {
            image.setImageResource(R.mipmap.android_mid);
            finalScoreText = Double.toString(finalScore) + " " + getString(R.string.points);
            finalScoreTextView.setText(finalScoreText);
            finalText.setText(R.string.finalMed);
        } else if (finalScore < 5) {
            image.setImageResource(R.mipmap.android_low);
            finalScoreText = Double.toString(finalScore) + " " + getString(R.string.points);
            finalScoreTextView.setText(finalScoreText);
            finalText.setText(R.string.finalLow);
        }
    }

    //----------------------------------------------------------------------------------------------
    // @backToMenuButton() button on final score window that lets user get back to menu.
    //----------------------------------------------------------------------------------------------
    public void backToMenuButton(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------
    // @backToMenuButton() button on final score window that lets user share his score via email.
    //----------------------------------------------------------------------------------------------
    public void shareButton(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout my Android Development quiz score !");
        intent.putExtra(Intent.EXTRA_TEXT, "I have scored " + Double.toString(finalScore));
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

}



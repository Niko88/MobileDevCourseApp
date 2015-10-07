package com.example.nicholasesposito.courseapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView question;
    private TextView scoreView;
    private Button buttonFalse;
    private Button buttonTrue;
    private ImageView picture;
    private int index;
    private List<QuestionObject> questions;
    private QuestionObject currentQuestion;
    private int score;
    private static final String KEY_INDEX = "index";
    private static final String SCORE_VALUE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
        {index = savedInstanceState.getInt(KEY_INDEX);
         score = savedInstanceState.getInt(SCORE_VALUE);
         index --;
        }
        else
        {
            index = 0;
            score = 0;
        }

        question = (TextView) findViewById(R.id.lblQuestion);
        buttonTrue = (Button) findViewById(R.id.button_true);
        buttonFalse = (Button) findViewById(R.id.button_false);
        picture = (ImageView) findViewById(R.id.imgPicture);
        scoreView = (TextView) findViewById(R.id.scoreViewId);
        picture.setImageResource(R.drawable.glasses);
        scoreView.setText(String.format("Score = %d", score));

        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                determineButtonPress(true);
            }
        });

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(false);
            }
        });

        generateQuestions();

        setUpQuestion();

    }

    private void generateQuestions(){

        questions = new ArrayList<>();
        questions.add(new QuestionObject("is the capital of England London?", true, R.drawable.london));
        questions.add(new QuestionObject("is Egypt in France?", false, R.drawable.egypt));
        questions.add(new QuestionObject("is Bath college in Bristol?", false, R.drawable.college));
        questions.add(new QuestionObject("is the Royal crescent in Bath?", true, R.drawable.crescent));
        questions.add(new QuestionObject("is Rome in Italy?", true, R.drawable.rome));
        questions.add(new QuestionObject("is there a statue of liberty in France?", true, R.drawable.parisliberty));
        questions.add(new QuestionObject("is the tour eiffel in New York?", false, R.drawable.newyork));
        questions.add(new QuestionObject("is the Duomo in Milan?", true, R.drawable.duomo));
        questions.add(new QuestionObject("is the earth round?", true, R.drawable.earth));
        questions.add(new QuestionObject("is New Mexico a part of Mexico?", false, R.drawable.newmexico));
    }

    private void setUpQuestion(){

        if (index == questions.size())
        {
            Log.d("COURSE_APP","ended all questions");
            endGame();
            index = 0;
            score = 0;
        }
        else
        {
            currentQuestion = questions.get(index);
            question.setText(currentQuestion.getQuestion());
            picture.setImageResource(currentQuestion.getPicture());
            index++;
        }
    }

    private void determineButtonPress(boolean answer)
    {
        boolean expectedAnswer = currentQuestion.isAnswer();

        if(answer == expectedAnswer)
        {
            Toast.makeText(MainActivity.this, "Correct!",Toast.LENGTH_SHORT).show();
            score ++;
            scoreView.setText(String.format("Score = %d", score));
        }
        else
        {
            Toast.makeText(MainActivity.this,"Incorrect!",Toast.LENGTH_SHORT).show();
        }

        setUpQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_INDEX,index);
        savedInstanceState.putInt(SCORE_VALUE,score);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void endGame()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("Congratulations").setMessage("You scored " + score +" points this round!").setNeutralButton("ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which){}
        }).create();

        alertDialog.show();
    }
}

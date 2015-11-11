package com.example.nicholasesposito.courseapp;

import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esotericsoftware.kryo.io.Input;
import com.parse.Parse;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import io.paperdb.Paper;


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
    private String m_Text = "";

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
        questions.add(new QuestionObject("is the capital of England London?", true, "http://pictures.solardestinations.com/images/packages/unitedkingdom/London-HousesoftheParliament.jpg"));
        questions.add(new QuestionObject("is Egypt in France?", false, "http://www.hdwallpapersnew.net/wp-content/uploads/2015/10/awesome-france-wide-hd-wallpaper-images-full-free-200x200.jpg"));
        questions.add(new QuestionObject("is Bath college in Bristol?", false, "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xft1/v/t1.0-1/p200x200/11150424_10152805581131334_4537754421540710647_n.png?oh=2265c9efe8d526186373b58d907b9077&oe=56419969&__gda__=1447372100_56b31d9a4413ba33071de6c949836f56"));
        questions.add(new QuestionObject("is the Royal crescent in Bath?", true, "http://www.cappuccinocards.com/media/catalog/product/cache/1/small_image/200x/9df78eab33525d08d6e5fb8d27136e95/b/a/bath_royal_crescent_sq.jpg"));
        questions.add(new QuestionObject("is Rome in Italy?", true, "http://www.flightcentre.co.nz/global-images/product-images/holidays/rome1.jpg"));
        questions.add(new QuestionObject("is there a statue of liberty in France?", true, "http://1.bp.blogspot.com/-ZKzJmyoxwm8/VaPZ1Xf6coI/AAAAAAAAAM0/jFfkXlLpqZ4/s200-c/130th%2BAnniversary%2Bof%2BFrance%2Bdelivering%2Bthe%2BStatue%2Bof%2BLiberty%2Bto%2Bthe%2BUnited%2BStates.jpg"));
        questions.add(new QuestionObject("is the tour eiffel in New York?", false, "http://cache.graphicslib.viator.com/graphicslib/2312/SITours/new-york-lights-night-tour-in-new-york-city-1.jpg"));
        questions.add(new QuestionObject("is the Duomo in Milan?", true, "http://jto.s3.amazonaws.com/wp-content/uploads/2015/05/z8-sp-expomilano1-b-20150501-200x200.jpg"));
        questions.add(new QuestionObject("is the earth round?", true, "http://rationalwiki.org/w/images/thumb/2/2f/Flat_earth.png/200px-Flat_earth.png"));
        questions.add(new QuestionObject("is New Mexico a part of Mexico?", false, "http://www.mainstreetroswell.org/wpdocs/wp-content/uploads/2015/04/NMMS_Logo-Converted-.png"));
    }

    private void setUpQuestion(){

        if (index == questions.size())
        {
            Log.d("COURSE_APP", "ended all questions");
            endGame();
            index = 0;
        }
        else
        {
            currentQuestion = questions.get(index);
            question.setText(currentQuestion.getQuestion());
            Picasso.with(this)
                    .load(currentQuestion.getPicture())
                    .into(picture);
            index++;
        }
    }

    private void determineButtonPress(boolean answer)
    {
        boolean expectedAnswer = currentQuestion.isAnswer();
        final Toast toast = Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT);
        final Toast toast1 = Toast.makeText(MainActivity.this, "Incorrect!",Toast.LENGTH_SHORT);

        if(answer == expectedAnswer)
        {
            toast.show();
            score ++;
            scoreView.setText(String.format("Score = %d", score));
        }
        else
        {

            toast1.show();

        }

        setUpQuestion();
        toast.cancel();
        toast1.cancel();
        Paper.init(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_INDEX,index);
        savedInstanceState.putInt(SCORE_VALUE, score);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void endGame() {
        final EditText input = new EditText(MainActivity.this);
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Congratulations")
                .setMessage("You scored " + score + " points this round!")
                .setView(input)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                m_Text = input.getText().toString();

                                //new high score

                                HighScoreObject highScore = new HighScoreObject(score,m_Text, new Date().getTime());
                                //get user prefs

                                List<HighScoreObject> highScores = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

                                //add item
                                highScores.add(highScore);

                                Paper.book().write("highscores", highScores);


                                Toast.makeText(MainActivity.this, "Score " + score + " - " + m_Text, Toast.LENGTH_SHORT).show();

                                score = 0;


                                finish();
                            }
                        }

                )
               .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int whichButton) {
                               finish();
                           }
                       }
               )
                .create();

        alertDialog.show();
    }

}

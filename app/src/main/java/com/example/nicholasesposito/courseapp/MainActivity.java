package com.example.nicholasesposito.courseapp;

import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import com.parse.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import io.paperdb.Paper;


public class MainActivity extends FragmentActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private TextView question;
    private TextView scoreView;
    private Button buttonFalse;
    private Button buttonTrue;
    private ImageView picture;
    private RoundImage roundedImage;
    private int index;
    private List questions;
    //private List<QuestionObject> questions;
    //private QuestionObject currentQuestion;
    private String currentQuestion;
    private boolean ans;
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
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
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

        questions = new ArrayList();

        questions.add("zVTQfQbUsC");
        questions.add("zjpFZYbdY8");
        questions.add("Dh6KKCphzq");
        questions.add("6JtqfvI87y");
        questions.add("fZsp8lj7t0");
        questions.add("LGqQb7gYV6");
        questions.add("3vD4tUQAJO");
        questions.add("zyqjNJ88eT");
        questions.add("rJYl00zMia");
        questions.add("aOhQkPTgqc");


    }

    private void setUpQuestion(){

        if (index == questions.size())
        {
            endGame();
            index = 0;
        }
        else
        {
            currentQuestion = (String) questions.get(index);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionObject");
            query.getInBackground(currentQuestion, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        String questionText = object.getString("question");
                        ans = object.getBoolean("answ");
                        question.setText(questionText);


                        ParseFile fileObject = (ParseFile) object
                                .get("picture");
                        fileObject
                                .getDataInBackground(new GetDataCallback() {

                                    public void done(byte[] data,
                                                     ParseException e) {
                                        if (e == null) {
                                            Log.d("test",
                                                    "We've got data in data.");
                                            // Decode the Byte[] into
                                            // Bitmap
                                            Bitmap bmp = BitmapFactory
                                                    .decodeByteArray(
                                                            data, 0,
                                                            data.length);

                                            // Get the ImageView from
                                            // main.xml

                                            // Set the Bitmap into the
                                            // ImageView
                                            roundedImage = new RoundImage(bmp);
                                            picture.setImageDrawable(roundedImage);
                                            picture.setVisibility(View.VISIBLE);
                                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);;


                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                    }});
                    } else {
                        // something went wrong
                        Log.d("Parse","Error!");
                    }
                }
            });


            index++;
        }
    }

    private void determineButtonPress(boolean answer)
    {
        final boolean Answer = answer;
        final boolean expectedAnswer = ans; //currentQuestion.isAnswer();
        final Toast toast = Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT);
        final Toast toast1 = Toast.makeText(MainActivity.this, "Incorrect!",Toast.LENGTH_SHORT);

        new CountDownTimer(1100, 1000) { // 5000 = 5 sec

            public void onTick(long millisUntilFinished) {
                buttonFalse.setVisibility(View.INVISIBLE);
                buttonTrue.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
                if(Answer == expectedAnswer)
                {
                   // toast.show();
                    picture.setImageResource(R.drawable.ok);
                    score ++;
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                    mp.start();
                    scoreView.setText(String.format("Yeah!"));
                }
                else
                {
                    picture.setImageResource(R.drawable.facepalm);
                    scoreView.setText(String.format("BOOOOO!"));
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.bo);
                    mp.start();
                    //toast1.show();

                }

                //toast.cancel();
                //toast1.cancel();
                //Paper.init(this);
            }

            public void onFinish() {
                buttonFalse.setVisibility(View.VISIBLE);
                buttonTrue.setVisibility(View.VISIBLE);
                question.setVisibility(View.VISIBLE);
                scoreView.setText(String.format("Score = %d", score));
                setUpQuestion();
            }
        }.start();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_INDEX,index);
        savedInstanceState.putInt(SCORE_VALUE, score);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
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

                                score = 0;


                                finish();
                            }
                        }

                )
               .setNegativeButton("share", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int whichButton) {
                               if (ShareDialog.canShow(ShareLinkContent.class)) {
                                   ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                           .setContentTitle("Hello Facebook")
                                           .setContentDescription(
                                                   "The 'Hello Facebook' sample  showcases simple Facebook integration")
                                           .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                                           .build();

                                   shareDialog.show(linkContent);
                                   finish();
                               }
                           }
                       }

               )
                .create();

        alertDialog.show();
    }

}

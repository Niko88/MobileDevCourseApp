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
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import com.parse.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private boolean ans;
    private int score;
    private static final String KEY_INDEX = "index";
    private static final String SCORE_VALUE = "score";
    private String m_Text = "";
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        category = bundle.getString("CATEGORY");//The String argument passed as extra in the intent gets retrieved and saved in the String variable category
        if (savedInstanceState != null)//If the device change orientation, this is going to preserve data
        {index = savedInstanceState.getInt(KEY_INDEX);
            score = savedInstanceState.getInt(SCORE_VALUE);
            index --;
        }
        else
        {
            index = 0;
            score = 0;
        }
        //Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        //Facebook
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

                determineButtonPress(true);//based on the pressed button the determineButtonPress method is called passing a boolean argument
            }
        });
        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                determineButtonPress(false);
            }
        });
        setUpQuestion();
    }
    private void setUpQuestion() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("QuestionObject");//The online parse database gets queried on the QuestionObject table
        query.whereEqualTo("category", category);//The Question object table gets queried to retrive objects from the selected category
        //the retrieved objects are put in a list of objects questionlist
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> questionlist, ParseException e) {
                if (e == null) {
                    if (index == questionlist.size())//the game keeps going untill the last question in the list has been reached
                    {  endGame();}
                    else
                    {
                    String questionText = questionlist.get(index).get("question").toString();//The question is retrieved from the given index of the list and put in a String variable
                    ans = (boolean) questionlist.get(index).get("answ");//The answer is retrieved from the given index of the list and put in a boolean variable
                    question.setText(questionText);//question is put in the TextView
                        question.setVisibility(View.VISIBLE);
                        buttonFalse.setVisibility(View.VISIBLE);
                        buttonTrue.setVisibility(View.VISIBLE);
                        scoreView.setText(String.format("Score = %d", score));//Score gets put in TextView
                    ParseFile fileObject = (ParseFile) questionlist.get(index).get("picture");//A picture is retrieved from the database
                    fileObject.getDataInBackground(new GetDataCallback()
                    {
                        public void done(byte[] data, ParseException e)
                        {
                            if (e == null)
                            {
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                roundedImage = new RoundImage(bmp);//image gets rounded
                                picture.setImageDrawable(roundedImage);//and put in the ImageView
                                picture.setVisibility(View.VISIBLE);
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);//LoadingPanel is made invisible
                            }
                            else
                            {
                                Log.d("test", "There was a problem downloading the data.");
                            }
                        }
                    });
                    index++;}
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


    }
    private void determineButtonPress(boolean answer)
    {
        final boolean Answer = answer;
        final boolean expectedAnswer = ans;

        new CountDownTimer(1100, 1000) { // a 1100 milliseconds timer is set

            public void onTick(long millisUntilFinished) {//timer starts
                buttonFalse.setVisibility(View.INVISIBLE);
                buttonTrue.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
                if(Answer == expectedAnswer)//the answer gets evaluated
                {
                    // toast.show();
                    picture.setImageResource(R.drawable.ok);//an image is presented accordingly
                    score ++;//if nswer is correct scoe is increased
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);//a sound is played
                    mp.start();
                    scoreView.setText(String.format("Yeah!"));
                }
                else
                {
                    picture.setImageResource(R.drawable.facepalm);
                    scoreView.setText(String.format("BOOOOO!"));
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.bo);
                    mp.start();

                }

            }

            public void onFinish() {
                setUpQuestion();
            }//when timer finishes everything goes back to normal and the next question is presented
        }.start();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_INDEX,index);//Data is saved if device gets turned
        savedInstanceState.putInt(SCORE_VALUE, score);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume(); //Facebook analytics
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);//Facebook analytics
    }
    private void endGame() {
        final EditText input = new EditText(MainActivity.this);
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)//An alert dialog is created at the end of the game
                .setTitle("Congratulations")
                .setMessage("You scored " + score + " points this round!")
                .setView(input)//user can input name
                .setPositiveButton("save score", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                m_Text = input.getText().toString();
                                saveScore(m_Text);//score is saved in the local paper db and activitty ends
                                finish();
                            }
                        }

                )
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();//score is not saved and activity ends
                            }
                        }
                )
                .setNeutralButton("share", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (ShareDialog.canShow(ShareLinkContent.class)) {
                                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                            .setContentTitle("My Score")//User can share score on Facebook
                                            .setContentDescription(
                                                    "I scored " + score + " points in Nik's question App!")
                                            .setContentUrl(Uri.parse("https://github.com/Niko88/MobileDevCourseApp"))
                                            .build();
                                    shareDialog.show(linkContent);
                                    m_Text = input.getText().toString();
                                    saveScore(m_Text);//score is saved locally and activity ends
                                    finish();
                                }
                            }
                        }
                )
                .create();
        alertDialog.show();
    }
    public void saveScore(String m_Text)//scores get saved in local paper db
    {
        if(m_Text.length() == 0)//if no name has been inputted anon player gets saved as name
        {m_Text = "anon player";}
        HighScoreObject highScore = new HighScoreObject(score,m_Text, new Date().getTime());
        List<HighScoreObject> highScores = Paper.book().read("highscores", new ArrayList<HighScoreObject>());
        highScores.add(highScore);
        Collections.sort(highScores, new Comparator<HighScoreObject>()
         {
            public int compare(HighScoreObject a, HighScoreObject b)
            {
                if (a.getScore() > b.getScore())
                {
                    return -1;
                }
                else if (a.getScore() < b.getScore())
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        });

        Paper.book().write("highscores", highScores);//scores are ordered in descending order and saved in local paper db

        score = 0;
    }

}
package com.example.nicholasesposito.courseapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;



public class IntroductionActivity extends AppCompatActivity {

    private Button btnAbout;
    private Button btnPlay;
    private Button btnScores;
    private TextView txtHighScores;
    private RoundImage roundedImage;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        /*

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            Toast toast = Toast.makeText(IntroductionActivity.this, "Internet Connected", Toast.LENGTH_LONG);
            toast.show();
        }else{
            //no connection
            Toast toast = Toast.makeText(IntroductionActivity.this, "No Internet Connection", Toast.LENGTH_LONG);
            toast.show();
        }*/
        ///////////////VARIABLE INITIALIZED////////////////////
        imageView = (ImageView) findViewById(R.id.introImage);
        btnAbout = (Button) findViewById(R.id.aboutButton);
        btnScores = (Button) findViewById(R.id.highScoresButton);
        btnPlay = (Button) findViewById(R.id.playButton);
        txtHighScores = (TextView) findViewById(R.id.scoreLabel);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.imageq);
        roundedImage = new RoundImage(bm);

        //////////////DATABASES CONNECTION AND INITIALIZATION/////////////////////////
        Paper.init(this); //Paper library is initialized to save infomation on a local database in the phone
        Log.d("Paper", "Paper initialized");  // Enable Local Datastore.
        Parse.enableLocalDatastore(this); //Parse is initialized to provide connectivity to the online database
        Log.d("Parse", "enabled local datastore");
        Parse.initialize(this, "jA008oxhWY8RuXW4tkAGwGHieRwx9A9RbDGbZIM9", "FBMpKpbSEGtbdqWlZdlRzE5pbVivix41m1OLjyl3");
        Log.d("Parse", "Parse initialized");

        imageView.setImageDrawable(roundedImage); //The imageview is rounded by the RoundImage java class and added to the layout

        btnAbout.setOnClickListener(new View.OnClickListener() {//Action when user click "about" button
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, profileActivity.class);//a new intent is created to connect this view  to profile activity
                startActivity(i); // intent gets started
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {//Action when user click "Play" button
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, category_picker.class);//a new intent is created to connect this view  to the main activity
                startActivity(i);//intent gets started
            }
        });


        btnScores.setOnClickListener(new View.OnClickListener() { //Action when user click score button
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, HighScoreActivity.class);//a new intent is created to connect this view  to high score activity
                startActivity(i);//intent gets started
            }
        });

        setHighScore();//set high score method call
    }

    @Override
    protected void onRestart() {//when the app restart the view is going to show the highscore again
        super.onRestart();
        setHighScore();
    }
    public void setHighScore() //This method set the highscore label to the highest score
    {

        List<HighScoreObject> highScrs = Paper.book().read("highscores", new ArrayList<HighScoreObject>());//The app pulls the list of highscores from the local paper database
        int maxScore = 0;
        for (HighScoreObject h : highScrs){

            if (h.getScore() > maxScore)
            {
                maxScore = h.getScore();}

        }//a for loop finds the highest of all the scores
        txtHighScores.setText("High Score = "+maxScore); //the highest score is set to the high score label
    }
}

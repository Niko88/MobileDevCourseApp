package com.example.nicholasesposito.courseapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("APP", "STARTED");
        setContentView(R.layout.activity_introduction);

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
        }






        Paper.init(this);
        Log.d("Paper","Paper initialized");
        // Enable Local Datastore.
       /* Parse.enableLocalDatastore(this);
        Log.d("Parse", "enabled local datastore");
        Parse.initialize(this, "jA008oxhWY8RuXW4tkAGwGHieRwx9A9RbDGbZIM9", "FBMpKpbSEGtbdqWlZdlRzE5pbVivix41m1OLjyl3");
        Log.d("Parse", "Parse initialized");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        Log.d("Parse", "Object sent");
        testObject.saveInBackground();
        Log.d("Parse", "Object saved");*/

        btnAbout = (Button) findViewById(R.id.aboutButton);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, profileActivity.class);
                startActivity(i);
            }
        });
        btnPlay = (Button) findViewById(R.id.playButton);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnScores = (Button) findViewById(R.id.highScoresButton);
        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroductionActivity.this, HighScoreActivity.class);
                startActivity(i);
            }
        });
        List<HighScoreObject> highScrs = Paper.book().read("highscores", new ArrayList<HighScoreObject>());
        int maxScore = 0;
        for (HighScoreObject h : highScrs){

                if (h.getScore() > maxScore)
                {
                maxScore = h.getScore();}

       }


        txtHighScores = (TextView) findViewById(R.id.scoreLabel);
        txtHighScores.setText("High Score = "+maxScore);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int maxScore = 0;
        List<HighScoreObject> highScrs = Paper.book().read("highscores", new ArrayList<HighScoreObject>());
        for (HighScoreObject h : highScrs){

            if (h.getScore() > maxScore)
            {
                maxScore = h.getScore();}

        }

        txtHighScores = (TextView) findViewById(R.id.scoreLabel);
        txtHighScores.setText("High Score = " + maxScore);
    }
}

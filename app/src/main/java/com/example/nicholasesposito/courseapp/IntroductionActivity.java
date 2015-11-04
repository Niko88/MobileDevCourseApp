package com.example.nicholasesposito.courseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        setContentView(R.layout.activity_introduction);

        Paper.init(this);

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

        //Toast.makeText(IntroductionActivity.this, maxScore,Toast.LENGTH_SHORT).show();
        txtHighScores = (TextView) findViewById(R.id.scoreLabel);
        txtHighScores.setText("High Score = "+maxScore);
    }
}

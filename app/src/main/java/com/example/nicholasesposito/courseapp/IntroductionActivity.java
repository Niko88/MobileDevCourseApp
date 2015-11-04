package com.example.nicholasesposito.courseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroductionActivity extends AppCompatActivity {

    private Button btnAbout;
    private Button btnPlay;
    private Button btnScores;
    private TextView txtHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

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

        txtHighScores = (TextView) findViewById(R.id.scoreLabel);
        txtHighScores.setText("High score = %d");
    }
}

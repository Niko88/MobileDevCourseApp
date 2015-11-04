package com.example.nicholasesposito.courseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class HighScoreActivity extends AppCompatActivity {

    public TextView totView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        //Paper.init(this);
        List<HighScoreObject> ScoreList = Paper.book().read("highscores", new ArrayList<HighScoreObject>());
        totView = (TextView) findViewById(R.id.totView);
        int total = ScoreList.size();
        totView.setText("you've got "+ total + " scores saved");

    }
}

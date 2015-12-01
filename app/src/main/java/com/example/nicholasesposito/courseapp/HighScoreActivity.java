package com.example.nicholasesposito.courseapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class HighScoreActivity extends AppCompatActivity {

    private  List<HighScoreObject> highscores;

    private ListView listView;
    private Button rButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Paper.init(this);
        highscores = Paper.book().read("highscores", new ArrayList<HighScoreObject>());
        listView = (ListView) findViewById(R.id.listView);
        HighscoreAdapter adapter = new HighscoreAdapter(highscores);
        listView.setAdapter(adapter);
    }


    private class HighscoreAdapter extends ArrayAdapter<HighScoreObject> {

        public HighscoreAdapter(List<HighScoreObject> items) {
            super(HighScoreActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.raw_layout, null);
            }

            //get the highscore object for the row we're looking at
            HighScoreObject highscore = highscores.get(position);

            Date date = new Date(highscore.getTimestamp());
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

            TextView lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
            lblTitle.setText(highscore.getName() + " scored " + highscore.getScore() + " the " + fmtOut.format(date));


            rButton = (Button) findViewById(R.id.resbutton);
            rButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog alertDialog = new AlertDialog.Builder(HighScoreActivity.this)
                            .setMessage("Are you sure you want to erase all data?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            Paper.book().destroy();
                                            Toast.makeText(HighScoreActivity.this, "Data Erased", Toast.LENGTH_SHORT).show();
                                            setContentView(R.layout.activity_high_score);

                                        }
                                    }

                            )
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    }
                            )
                            .create();

                    alertDialog.show();

                }
            });



            return convertView;
        }// end get view

    }// end adapter class
}

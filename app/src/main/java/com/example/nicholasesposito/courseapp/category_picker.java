package com.example.nicholasesposito.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class category_picker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_picker);
        Button World = (Button) findViewById(R.id.worldButton);
        Button Flags = (Button) findViewById(R.id.flagsButton);
        Button back = (Button) findViewById(R.id.backButton);

        World.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StartIntent("world");//The app based on the clicked button is going to call the StartIntent method passing a String argument
            }
        });
        Flags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StartIntent("flags");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//If the user press go back the activity gets stopped
            }
        });




    }

    private void StartIntent(String category)
    {
        Intent i = new Intent(category_picker.this, MainActivity.class);//a new intent is created to connect this view  to the main activity
        i.putExtra("CATEGORY", category);//an intent to start main activity is created with an extra String argument
        startActivity(i);
    }

}

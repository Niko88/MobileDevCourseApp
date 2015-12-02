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
        Button World;
        Button Flags;
        World = (Button) findViewById(R.id.worldButton);
        Flags = (Button) findViewById(R.id.flagsButton);

        World.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StartIntent("world");
            }
        });
        Flags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            StartIntent("flags");
            }
        });




    }

    private void StartIntent(String category)
    {
        Intent i = new Intent(category_picker.this, MainActivity.class);//a new intent is created to connect this view  to the main activity
        i.putExtra("CATEGORY", category);
        startActivity(i);
    }

}

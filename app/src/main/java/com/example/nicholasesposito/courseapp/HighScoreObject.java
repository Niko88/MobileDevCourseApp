package com.example.nicholasesposito.courseapp;

/**
 * Created by nicholasesposito on 21/10/2015.
 */
public class HighScoreObject
{
    private String name;
    private Integer score;
    private long timestamp;

    public HighScoreObject(Integer scor, String nam , long timestam)
    {
        name = nam;
        score = scor;
        timestamp = timestam;

    }
}

package com.example.nicholasesposito.courseapp;

/**
 * Created by nicholasesposito on 21/10/2015.
 */
public class HighScoreObject
{
    private String name;
    private Integer score;
    private long timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public HighScoreObject(Integer scor, String nam , long timestam)
    {
        name = nam;
        score = scor;
        timestamp = timestam;

    }

    public HighScoreObject()
    {}
}

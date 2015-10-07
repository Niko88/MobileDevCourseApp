package com.example.nicholasesposito.courseapp;

/**
 * Created by nicholasesposito on 02/10/2015.
 */
public class QuestionObject {

    private String question;
    private int picture;
    private boolean answer;

    public QuestionObject(String question, boolean answer, int picture)
    {
        this.answer = answer;
        this.question = question;
        this.picture = picture;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }


}

package com.example.nicholasesposito.courseapp;

/**
 * Created by nicholasesposito on 02/10/2015.
 */
public class QuestionObject {

    private String question;
    private String picture;
    private boolean answer;

    public QuestionObject(String question, boolean answer, String picture)
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }


}

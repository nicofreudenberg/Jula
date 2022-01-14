package com.example.jula;

import android.os.Bundle;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Poll implements Serializable  {
    String title,  text;
    //Map<String, Map<String, Integer>> answerOptions;
    List<String>  answerOptions= new ArrayList<>();


    long timestamp;



    Map<String, Integer> answers;



    public Poll(String title, String text, List<String> answerOptions, Map<String, Integer> answers, long timestamp) {

        this.title=title;
        this.answers = answers;
        this.answerOptions=answerOptions;
        this.text=text;
        this.timestamp = timestamp;
    }
public Poll(){}
    public Map<String, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Integer> answers) {
        this.answers = answers;
    }
    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }
    public String getTitle() {
        return title;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}

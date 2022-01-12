package com.example.jula;

import android.os.Bundle;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Poll implements Serializable  {
    String title,  text;
    //Map<String, Map<String, Integer>> answerOptions;
    List<String>  answerOptions= new ArrayList<>();



    Map<String, Integer> answers;


  //  List answerOptions;
    //String [] answerOptions;

    public Poll(String title, String text, List<String> answerOptions, Map<String, Integer> answers) {

        this.title=title;
        this.answers = answers;
        this.answerOptions=answerOptions;
        this.text=text;
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

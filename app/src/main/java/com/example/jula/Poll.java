package com.example.jula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Poll implements Serializable  { //Modelklasse der Umfragen, implementiert Serializable um diese an weitere Fragmente zu übergeben

    String title,  text; //Titel und Beschreibung der Umfrage
    List<String>  answerOptions= new ArrayList<>(); //Liste der Antwortmöglichkeiten
    long timestamp; //timeStamp, zum Sortieren der Umfragen
    Map<String, Integer> answers; //Map um die Antworten zu den jeweiligen Möglichkeiten zu speichern



    public Poll(String title, String text, List<String> answerOptions, Map<String, Integer> answers, long timestamp) { //Konstruktur

        this.title=title;
        this.answers = answers;
        this.answerOptions=answerOptions;
        this.text=text;
        this.timestamp = timestamp;
    }

public Poll(){} //Mapping-Methode braucht einen Standardkonstruktur


    //Getter & Setter
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
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

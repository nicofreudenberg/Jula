package com.example.jula;

public class Post {
    String title,  text, link;

    public Post (String title, String text) {

        this.title=title;
        this.text=text;

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

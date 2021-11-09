package com.example.jula;

public class Post {
    String title,  text, link;

    public Post (String username, String text, String link) {

        this.title=username;
        this.text=text;
        this.link=link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tittöe) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

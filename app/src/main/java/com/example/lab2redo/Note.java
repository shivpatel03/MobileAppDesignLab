package com.example.lab2redo;


public class Note {
    private String title;
    private String subtitle;
    private String content;
    private int id;

    public Note(int id, String title, String subtitle, String content) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getContent() { return content; }
    public int getId() { return id; }
}

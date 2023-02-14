package com.techelevator.model;

public class Report {

    private int id;
    private String content;
    private String type;
    private String date;

    public Report(int id, String content, String type, String date) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.date = date;
    }

    public Report(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
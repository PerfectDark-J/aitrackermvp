package com.techelevator.model;

import java.sql.Timestamp;

public class Report {

    private int id;

    private int taskid;

    private int bounty;

    private int earnedpoints;

    private String title;

    private int userid;
    private String content;
    private String type;
    private Timestamp date;

    private String description;

    private String exercise;

    private int reps;

    private int weight;

    private int minutes;


    public Report(int id, int userid, String content, String type, Timestamp date, String description, String exercise, int reps, int weight, int minutes, String title, int taskid, int earnedpoints, int bounty) {
        this.id = id;
        this.userid = userid;
        this.content = content;
        this.type = type;
        this.date = date;
        this.description = description;
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this.minutes = minutes;
        this.title = title;
        this.taskid = taskid;
        this.earnedpoints = earnedpoints;
        this.bounty = bounty;
    }

    public Report(){

    }

    public int getBounty() {
        return bounty;
    }

    public void setBounty(int bounty) {
        this.bounty = bounty;
    }

    public int getEarnedpoints() {
        return earnedpoints;
    }

    public void setEarnedpoints(int earnedpoints) {
        this.earnedpoints = earnedpoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getuserid() {
        return userid;
    }

    public void setuserid(int userid) {
        this.userid = userid;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
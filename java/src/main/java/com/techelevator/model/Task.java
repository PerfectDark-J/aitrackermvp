package com.techelevator.model;

import java.util.Date;
import java.util.List;

public class Task {
    private int id;

    private boolean isroutine;
    private String tasktitle;
    private String taskdescription;
    private boolean taskiscompleted;

    private String taskcompletiondate;


    private boolean taskisrecurring;

    private int userid;

    private String comment;



    // Constructors

    public Task() {

    }

    public Task(int id, String tasktitle, String taskdescription, boolean taskiscompleted, String taskcompletiondate, int userid, boolean taskisrecurring, String comment, boolean isroutine) {
        this.id = id;
        this.tasktitle = tasktitle;
        this.taskdescription = taskdescription;
        this.taskiscompleted = taskiscompleted;
        this.taskcompletiondate = taskcompletiondate;
        this.userid = userid;
        this.taskisrecurring = taskisrecurring;
        this.comment = comment;
        this.isroutine = isroutine;
    }

    public String getComment() {
        return comment;
    }



    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isIsroutine() {
        return isroutine;
    }

    public void setIsroutine(boolean isroutine) {
        this.isroutine = isroutine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public boolean isTaskisrecurring() {
        return taskisrecurring;
    }

    public void setTaskisrecurring(boolean taskisrecurring) {
        this.taskisrecurring = taskisrecurring;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTasktitle() {
        return tasktitle;
    }

    public void setTasktitle(String tasktitle) {
        this.tasktitle = tasktitle;
    }

    public String getTaskdescription() {
        return taskdescription;
    }

    public void setTaskdescription(String taskdescription) {
        this.taskdescription = taskdescription;
    }

    public boolean isTaskisCompleted() {
        return taskiscompleted;
    }

    public void setTaskisCompleted(boolean taskiscompleted) {
        this.taskiscompleted = taskiscompleted;
    }

    public String getTaskcompletiondate() {
        return taskcompletiondate;
    }

    public void setTaskcompletiondate(String taskcompletiondate) {
        this.taskcompletiondate = taskcompletiondate;
    }
}

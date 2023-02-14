package com.techelevator.model;

import java.util.Date;

public class Task {
    private int id;
    private String tasktitle;
    private String taskdescription;
    private boolean tasksscompleted;
    private String taskduedate;
    private String taskcompletiondate;
    private int projectid;

    private boolean taskisrecurring;

    private int userid;

    // Constructors

    public Task() {

    }

    public Task(int id, String tasktitle, String taskdescription, boolean tasksscompleted, String taskcompletiondate, int userid, boolean taskisrecurring) {
        this.id = id;
        this.tasktitle = tasktitle;
        this.taskdescription = taskdescription;
        this.tasksscompleted = tasksscompleted;
        this.taskcompletiondate = taskcompletiondate;
        this.userid = userid;
        this.taskisrecurring = taskisrecurring;
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

    public boolean isTasksscompleted() {
        return tasksscompleted;
    }

    public void setTasksscompleted(boolean tasksscompleted) {
        this.tasksscompleted = tasksscompleted;
    }

    public String getTaskduedate() {
        return taskduedate;
    }

    public void setTaskduedate(String taskduedate) {
        this.taskduedate = taskduedate;
    }

    public String getTaskcompletiondate() {
        return taskcompletiondate;
    }

    public void setTaskcompletiondate(String taskcompletiondate) {
        this.taskcompletiondate = taskcompletiondate;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}

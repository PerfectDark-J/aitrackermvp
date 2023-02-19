package com.techelevator.model;

public class Subtask {
    private int subtaskid;
    private int taskid;
    private String subtitle;
    private boolean iscompleted;

    public Subtask(){

    }
    public Subtask(int subtaskid, int taskid, String subtitle, boolean iscompleted) {
        this.subtaskid = subtaskid;
        this.taskid = taskid;
        this.subtitle = subtitle;
        this.iscompleted = iscompleted;
    }

    public int getSubtaskid() {
        return subtaskid;
    }

    public void setSubtaskid(int subtaskid) {
        this.subtaskid = subtaskid;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }
}

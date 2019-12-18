package com.tomato830.note_fjm.note;

import java.util.GregorianCalendar;
import java.util.HashSet;

public class note {
    private int id;
    private String title;
    private String content;
    private HashSet<String> tag ;
    private GregorianCalendar creationTime;
    private GregorianCalendar deadline;
    private boolean isDone;
    private boolean isNotice;

    //构造函数
    public note(String title){

        setTitle(title);

    }
    public note(){

    }

    //setter和getter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashSet<String> getTag() {
        return tag;
    }

    public void setTag(HashSet<String> tag) {
        this.tag = tag;
    }

    public GregorianCalendar getDeadline() {
        return deadline;
    }

    public void setDeadline(GregorianCalendar deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public GregorianCalendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(GregorianCalendar creationTime) {
        this.creationTime = creationTime;
    }
    public void setNotice(boolean notice){
        this.isNotice = notice;

    }
    public boolean isNotice(){return isNotice;}
}

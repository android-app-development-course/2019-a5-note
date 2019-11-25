package com.tomato830.note_fjm.note;

import java.util.GregorianCalendar;

public class note {
    private String title;
    private String content;
    private String tag;
    private GregorianCalendar creationTime;
    private GregorianCalendar deadline;
    private boolean isDone;

    //构造函数
    public note(String title){

        setTitle(title);

    }

    //setter和getter方法
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
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
}

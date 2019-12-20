package com.tomato830.note_fjm.note;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class note {
    //8个字段
    private int id;
    private String title;
    private String content;
    private HashSet<String> tag ;
    private GregorianCalendar creationTime;
    private GregorianCalendar deadline;
    private boolean isDone;
    private boolean isNotice;

    //构造函数
    public note(){
        //id自动生成不赋值
        //其余属性初始化为默认值,保证不空,便于SQLite读取
        setTitle("空");
        setContent("空");
        setTag(null);
        setCreationTime(new GregorianCalendar());//无参数直接构造,则为当前生成时间
        GregorianCalendar deadlineCalendar=new GregorianCalendar();
        deadlineCalendar.set(Calendar.DATE,deadlineCalendar.get(Calendar.DATE)+7);
        //↑的另一种方法
        //deadlineCalendar.add(Calendar.DATE,7);
        setDeadline(deadlineCalendar);//默认的截至日期为创建日期后7天
        setDone(false);
        setNotice(false);
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

    //将string装换为hashSet
    static public HashSet<String> string2HashSet(String s){
        //一般hashSet转为string后为这样
        //hashSet{1,2,3}->String"[1,2,3]"
        //第一步去除中括号
        s=s.substring(1,s.length()-1);
        //第二步以,分隔开来
        HashSet<String> hashSet;
        if (s.matches(","))
            hashSet=new HashSet<>(Arrays.asList(s.split(",")));//英文逗号
        else hashSet=new HashSet<>(Arrays.asList(s.split("，")));//中文逗号
        return hashSet;
    }
}

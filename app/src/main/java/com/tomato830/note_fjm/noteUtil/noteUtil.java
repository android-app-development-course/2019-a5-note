package com.tomato830.note_fjm.noteUtil;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.tomato830.note_fjm.note.note;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Locale;

public class noteUtil {
    private static final String CREATIOINTIME="CREATIONTIME";
    private static final String TITLE="TITLE";
    private static final String DEADLINE="DEADLINE";
    MySQLiteHelper mySQLiteHelper;

    //默认构造函数
    noteUtil(Context context,int version){
        mySQLiteHelper=new MySQLiteHelper(context,version);
    }


    static void addNote(ArrayList<note> notes,String title, String content, String tag, GregorianCalendar deadline, boolean isDone){
        note nt=new note(title);
        nt.setContent(content);
        //nt.setTag(tag);此处有错,string无法转换为hashset
        nt.setDeadline(deadline);
        nt.setDone(isDone);
        notes.add(nt);
    }

    static boolean delNote(ArrayList<note> notes,note nt){
        for (int i=0;i<notes.size();++i){
            if (notes.get(i)==nt){
                notes.remove(i);
                break;
            }
        }
        return true;
    }

    static ArrayList<note> queryNote(ArrayList<note> notes,String tag){
        ArrayList<note> nts=null;
        for (int i=0;i<notes.size();++i){
            if (notes.get(i).getTag().equals(tag)) {
                nts.add(notes.get(i));
            }
        }
        return nts;
    }

    static void updataNote(ArrayList<note> notes, int i,note nt){
        notes.get(i).setTitle(nt.getTitle());
        notes.get(i).setContent(nt.getContent());
        notes.get(i).setTag(nt.getTag());
        notes.get(i).setDone(nt.isDone());
        notes.get(i).setDeadline(nt.getDeadline());
        notes.get(i).setCreationTime(nt.getCreationTime());
    }

    //对note进行排序,创建时间,标题,截止时间排序
    static void sortNote(ArrayList<note> notes,String method){
        switch (method){
            case CREATIOINTIME:
                Collections.sort(notes, new Comparator<note>() {
                    @Override
                    public int compare(note o1, note o2) {
                        return o1.getCreationTime().compareTo(o2.getCreationTime());
                    }
                });
                break;
            case TITLE:
                Collections.sort(notes, new Comparator<note>() {
                    @Override
                    public int compare(note o1, note o2) {
                        return Collator.getInstance(Locale.CHINESE).compare(o1.getTitle(),o2.getTitle());
                    }
                });
                break;
            case DEADLINE:
                Collections.sort(notes, new Comparator<note>() {
                    @Override
                    public int compare(note o1, note o2) {
                        return o1.getDeadline().compareTo(o2.getDeadline());
                    }
                });
                break;
        }
    }
}

package com.tomato830.note_fjm.noteUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.tomato830.note_fjm.note.note;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;

public class noteUtil {
    private static final String CREATIOINTIME="CREATIONTIME";
    private static final String TITLE="TITLE";
    private static final String DEADLINE="DEADLINE";
    MySQLiteHelper mySQLiteHelper;
    Context context;

    //默认构造函数
    noteUtil(Context context,int version){
        this.context=context;
        mySQLiteHelper=new MySQLiteHelper(context,version);
    }


    void addNote(ArrayList<note> notes, String title, String content, HashSet<String> tag, GregorianCalendar creationTime, GregorianCalendar deadline, boolean isDone, boolean isNotice){
        /*
        note nt=new note(title);
        nt.setContent(content);
        nt.setTag(tag);
        nt.setDeadline(deadline);
        nt.setDone(isDone);
        notes.add(nt);

         */
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("content",content);
        values.put("tag",tag.toString());
        values.put("creationTime",new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(creationTime.getTime()));
        values.put("deadline",new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(creationTime.getTime()));
        if (isDone) values.put("isDone",1);
        else values.put("isDone",0);
        if (isNotice) values.put("isNotice",1);
        else values.put("isNotice",0);

        SQLiteDatabase db= mySQLiteHelper.getWritableDatabase();
        db.insert(MySQLiteHelper.DATABASENAME,null,values);
        db.close();
    }

    boolean delNote(ArrayList<note> notes,note nt){
        for (int i=0;i<notes.size();++i){
            if (notes.get(i)==nt){
                notes.remove(i);
                break;
            }
        }
        return true;
    }

    ArrayList<note> queryAllNote() throws ParseException {
        SQLiteDatabase db=mySQLiteHelper.getReadableDatabase();
        Cursor cursor=db.query(MySQLiteHelper.DATABASENAME,null,null,null,null,null,null);
        if (cursor.getCount()==0){
            Toast.makeText(context,"数据库中没有数据",Toast.LENGTH_SHORT).show();
            return null;
        } else {
            cursor.moveToFirst();
            ArrayList<note> nts=new ArrayList<>();
            note n=new note();
            n.setTitle(cursor.getString(1));
            n.setContent(cursor.getString(2));
            //string转set
            String tag=cursor.getString(3);
            tag=tag.substring(1,tag.length()-1);
            HashSet<String> s=new HashSet<>(Arrays.asList(tag.split(",")));
            Log.v("s",s.toString());
            n.setTag(s);
            //
            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(cursor.getString(4)));
            n.setCreationTime(calendar);
            //

            while (cursor.moveToNext()){

            }
            //关闭
            cursor.close();
            db.close();

            return nts;
        }


    }

    void updataNote(ArrayList<note> notes, int i,note nt){
        notes.get(i).setTitle(nt.getTitle());
        notes.get(i).setContent(nt.getContent());
        notes.get(i).setTag(nt.getTag());
        notes.get(i).setDone(nt.isDone());
        notes.get(i).setDeadline(nt.getDeadline());
        notes.get(i).setCreationTime(nt.getCreationTime());
    }

    //对note进行排序,创建时间,标题,截止时间排序
    void sortNote(ArrayList<note> notes,String method){
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

package com.tomato830.note_fjm.noteUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.tomato830.note_fjm.note.note;

public class MySQLiteHelper extends SQLiteOpenHelper {

    static final String DATABASENAME="DailyNote.db";


    //默认构造函数
    //游标工厂一般为空
    //数据库名为DailyNote.db
    //初始版本号为1
    //默认四个参数,此处减少两个
    //@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version
    public MySQLiteHelper(@Nullable Context context, int version) {
        super(context, DATABASENAME, null, version);
    }

    @Override
    //数据库创建时调用
    public void onCreate(SQLiteDatabase db) {
        //text类型不限制长度
        //哈希表转字符串
        //日期转化为字符串
        //8个字段对应note8个属性
        db.execSQL("CREATE TABLE todolist(id integer primary key autoincrement, title text, content text, " +
                "tag text, creationTime text, deadline text,  isDone tinyint,isNotice tinyint)");

    }

    @Override
    //数据库结构改变后,用新的版本号调用,修改数据库结构
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

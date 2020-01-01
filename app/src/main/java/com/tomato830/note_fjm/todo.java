package com.tomato830.note_fjm;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tomato830.note_fjm.note.note;
import com.tomato830.note_fjm.noteUtil.MySQLiteHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

//import com.tomato830.note_fjm.noteUtil.NormalRecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */





public class todo extends Fragment {


    public todo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    RecyclerView recyclerView;
    FloatingActionButton fab;
    HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
    RelativeLayout todo_sort_relativelayout;
    MySQLiteHelper mySQLiteHelper;
    ArrayList<note> note_list;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //每个记录之间的间隔
        int d=10;

        super.onActivityCreated(savedInstanceState);

        //初始化recyclerView
        recyclerView = getActivity().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //读取全部note
        note_list = new ArrayList<>();
        HashSet<String> tag_set = new HashSet<>();
        mySQLiteHelper = new MySQLiteHelper(getContext(),1);
        SQLiteDatabase sqLiteDatabase = mySQLiteHelper.getReadableDatabase();

        //取出所有未完成的note
        Cursor cursor = sqLiteDatabase.query("todolist",null,"isDone=0",null,null,null,null);
        //找到了有未完成的note
        if(cursor.moveToFirst()){

            note nt;
            while (!cursor.isAfterLast()){
                nt = new note();
                //取出id,标题,内容
                nt.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
                nt.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                nt.setContent(cursor.getString(cursor.getColumnIndex("content")));

                //取出tag,将string转为hashSet
                tag_set=note.string2HashSet(cursor.getString(cursor.getColumnIndex("tag")));
                nt.setTag(tag_set);

                //取出creationTime
                DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");//取出年月日时分
                Date date1 = null;
                try {
                    date1 = dateFormat1.parse(cursor.getString(cursor.getColumnIndex("creationTime")));
                    Log.v("creationTime",cursor.getString(cursor.getColumnIndex("creationTime")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                GregorianCalendar calendar1 = new GregorianCalendar();
                calendar1.setTime(date1);
                nt.setCreationTime(calendar1);

                //取出deadline
                DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                Date date2 = null;
                try {
                    date2 = dateFormat2.parse(cursor.getString(cursor.getColumnIndex("deadline")));
                    Log.v("deadline",cursor.getString(cursor.getColumnIndex("deadline")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                GregorianCalendar calendar2 = new GregorianCalendar();
                calendar2.setTime(date2);
                nt.setDeadline(calendar2);

                //取出isDone,此处不用判断
                /*
                if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("isDone"))) == 1){
                    nt.setDone(true);
                }
                else {
                    nt.setDone(false);
                }

                 */
                nt.setDone(false);

                //取出isNotice
                if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("isNotice"))) == 1){
                    nt.setNotice(true);
                }
                else {
                    nt.setNotice(false);
                }
                note_list.add(nt);

                //游标移到下一个
                cursor.moveToNext();
            }
        }
        //查看读取了多少个note
        Log.v("todo","读取note"+note_list.size()+"个");

        //完成操作,关闭游标和数据库
        cursor.close();
        sqLiteDatabase.close();

        //为recyclerView设置适配器
        recyclerView.setAdapter(new myRVAdapter(getContext(),note_list));

        //设置recycleView的item之间间距
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION,d);
        recyclerView.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        stringIntegerHashMap.put(SpacesItemDecoration.BOTTOM_DECORATION,d);
        recyclerView.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));

        //设置排序选择菜单
        todo_sort_relativelayout = getActivity().findViewById(R.id.todo_sort_relativelayout);
        todo_sort_relativelayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                    showSortMenu(todo_sort_relativelayout);
            }
        });

        fab = (FloatingActionButton) getActivity().findViewById(R.id.Btn_fab);
        //绑定fab与activity?
        //跳转新增待办事项add_todo
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(),add_todo.class);
               startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showSortMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.getMenuInflater().inflate(R.menu.todo_sort_menu,popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ddlsort:
                        ddlsort();
                        break;
                    case R.id.tagsort:
                        tagsort();
                        break;
                }
                return false;
            }
        });

        popupMenu.show();

    }

    private void ddlsort(){
        //按截止日期排序
    }

    private void tagsort(){
        //按标签排序
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

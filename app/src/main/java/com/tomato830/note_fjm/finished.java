package com.tomato830.note_fjm;


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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class finished extends Fragment {


    public finished() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finished, container, false);
    }

    ArrayList<note> note_list;
    RecyclerView recyclerone;
    HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
    RelativeLayout todo_sort_relativelayout;
    CheckBox checkBox;
    TextView textView;
    TextView number;
    MySQLiteHelper mySQLiteHelper;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //获取并显示日期，顺带应用于隔壁check_in
        textView = getActivity().findViewById(R.id.today_date);
        Calendar calendar = Calendar.getInstance();
        String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);        //获取日期的月
        String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));      //获取日期的日
        textView.setText(mMonth+"月"+mDay+"日");

        //初始化recyclerview
        recyclerone = getActivity().findViewById(R.id.recycler_one);
        recyclerone.setLayoutManager(new LinearLayoutManager(getContext()));

        //查询语句从SQLite中获取被勾选的item。
        //加入到note_list列表中.
        note_list = new ArrayList<>();
        mySQLiteHelper = new MySQLiteHelper(getContext(),1);
        HashSet<String> tag_set = new HashSet<>();
        SQLiteDatabase sqLiteDatabase = mySQLiteHelper.getReadableDatabase();  //获取只读SQLiteDatabase对象



        //设置状态变化监听将被选中的item的属性中的isDone改为1
        //不知道是不是需要新建一个文件，总之先在这里试试
        checkBox = getActivity().findViewById(R.id.finished_to_check);


        //取出已完成的note
        Cursor cursor = sqLiteDatabase.query("todolist",null,"isDone=1",null,null,null,null);
        //找到已完成的note
        if (cursor.moveToFirst()){

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

                //取出isDone
                if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("isDone"))) == 1){
                    nt.setDone(true);
                }
                else {
                    nt.setDone(false);
                }

                //取出isNotice
                if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("isNotice"))) == 1){
                    nt.setDone(true);
                }
                else {
                    nt.setDone(false);
                }
                note_list.add(nt);

                //游标移到下一个
                cursor.moveToNext();
            }
        }
        //查看读取了多少个note
        Log.v("finished","读取note"+note_list.size()+"个");

        //在狗牌中展示数字
        number = getActivity().findViewById(R.id.deadline);
        number.setText("今日完成事件："+note_list.size());

        //完成操作,关闭游标和数据库
        cursor.close();
        sqLiteDatabase.close();

        //为recyclerView设置适配器
        recyclerone.setAdapter(new myRVAdapter(getContext(),note_list));

        //向todo统一标准
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION,15);
        recyclerone.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        stringIntegerHashMap.put(SpacesItemDecoration.BOTTOM_DECORATION,15);
        recyclerone.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        todo_sort_relativelayout = getActivity().findViewById(R.id.todo_sort_relativelayout);
    }


    //排序未实装
    private void ddlsort(){
        //按截止日期排序
    }

    private void fshsort(){
        //按完成顺序
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}

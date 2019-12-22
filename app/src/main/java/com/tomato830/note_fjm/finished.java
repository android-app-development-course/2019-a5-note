package com.tomato830.note_fjm;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tomato830.note_fjm.note.note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


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
    Button refresh;
    TextView textView;
    TextView number;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //获取并显示日期，顺带应用于check_in
        textView = getActivity().findViewById(R.id.today_date);
        Calendar calendar = Calendar.getInstance();
        String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);        //获取日期的月
        String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));      //获取日期的日
        textView.setText(mMonth+"月"+mDay+"日");

        //初始化recyclerview
        recyclerone = getActivity().findViewById(R.id.recycler_one);
        recyclerone.setLayoutManager(new LinearLayoutManager(getContext()));

        //找到控件
        checkBox = getActivity().findViewById(R.id.finished_to_check);
        //设置监听器
        setListener();


        //读取被选中的item，点击刷新按钮后，置于recyclerview中
        //同时读取fragment_todo中的事项个数作为分母，被选中的事项个数作为分子
        refresh = getActivity().findViewById(R.id.finished_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取item数目

                //读取被选中的item数目

                //将被选中的item在recyclerview中显示

            }
        });

        number = getActivity().findViewById(R.id.deadline);
        number.setText("今日完成事件："+note_list.size());


        //未完成
        note_list = new ArrayList<>();

        //
        recyclerone.setAdapter(new myRVAdapter(getContext(),note_list));


        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION,15);
        recyclerone.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        stringIntegerHashMap.put(SpacesItemDecoration.BOTTOM_DECORATION,15);
        recyclerone.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        todo_sort_relativelayout = getActivity().findViewById(R.id.todo_sort_relativelayout);
    }

    //设置监听器
    private void setListener(){
        Onclick onclick = new Onclick();

        refresh.setOnClickListener(onclick);
    }
    //实现点击事件监听器接口
    class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //检测到被选中的item，将之加入到列表中并计数
            if(checkBox.isChecked()){
                note_list.size();
            }
        }
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

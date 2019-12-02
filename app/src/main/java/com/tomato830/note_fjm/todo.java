package com.tomato830.note_fjm;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;

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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new myRVAdapter(getContext()));
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION,10);
        recyclerView.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        stringIntegerHashMap.put(SpacesItemDecoration.BOTTOM_DECORATION,10);
        recyclerView.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));

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

package com.tomato830.note_fjm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tomato830.note_fjm.noteUtil.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //组件
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Fragment todoFragment,finishedFragment,checkinFragment;
    TabLayout tabLayout;
    LinearLayout menu_todo,menu_finished,menu_checkin;
    MySQLiteHelper mySQLiteHelper;
    ViewPager viewPager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        tabLayout=(TabLayout) findViewById(R.id.tab);
        todoFragment=new todo();
        getSupportFragmentManager().beginTransaction().replace(R.id.tabFragment,todoFragment).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                switch (pos){
                    case 0:
                        todoFragment=new todo();
                        getSupportFragmentManager().beginTransaction().replace(R.id.tabFragment,todoFragment).commit();
                        break;
                    case 1:
                        finishedFragment=new finished();
                        getSupportFragmentManager().beginTransaction().replace(R.id.tabFragment,finishedFragment).commit();
                        break;
                    case 2:
                        checkinFragment=new checkIN();
                        getSupportFragmentManager().beginTransaction().replace(R.id.tabFragment,checkinFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //抽屉层初始化
        menu_todo=(LinearLayout) findViewById(R.id.menu_todo);
        menu_finished=(LinearLayout) findViewById(R.id.menu_finished);
        menu_checkin=(LinearLayout) findViewById(R.id.menu_checkin);

        //设置监听器
        menu_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.getTabAt(0).select(); //tab计数从0开始
            }
        });
        menu_finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.getTabAt(1).select();
            }

        });
        menu_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                tabLayout.getTabAt(2).select();
            }
        });

        //初始化数据库
        mySQLiteHelper=new MySQLiteHelper(this,1);

        //测试,插入数据
        SQLiteDatabase db=mySQLiteHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title","今天天气很好");
        Long id=db.insert("todolist",null,values);
        Log.v("创建成功","id为"+Long.toString(id));

        db.close();

        //加载fragemnt
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view1=layoutInflater.inflate(R.layout.fragment_todo,null);
        View view2=layoutInflater.inflate(R.layout.fragment_finished,null);
        View view3=layoutInflater.inflate(R.layout.fragment_check_in,null);

        List<View> viewList=new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        //加载viewpager
        NoteViewPagerAdapter pagerAdapter=new NoteViewPagerAdapter(viewList);
        viewPager=(ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                break;
        }
        return true;
    }


}

package com.tomato830.note_fjm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.tomato830.note_fjm.ContributionChart.GridViewAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Fragment todoFragment,finishedFragment,checkinFragment;
    TabLayout tabLayout;

    //Create a gridviewAdapter object
    public GridViewAdapter gridAdapter;
    //create a gridview
    public GridView contibutionView;

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

        //checkinFragment=new checkIN();
        //getSupportFragmentManager().beginTransaction().replace(R.id.tabFragment,checkinFragment).commit();

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

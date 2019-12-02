package com.tomato830.note_fjm;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class add_todo extends AppCompatActivity {
    CalendarView calendarView;
    TextView date_text;
    Toolbar toolbar_back;
    Button todo_confrim;
    Button todo_cancel;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);

        calendarView = (CalendarView)findViewById(R.id.calendar);
        date_text = (TextView)findViewById(R.id.tx_todo_date);
        toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        todo_confrim = (Button)findViewById(R.id.Btn_todo_confrim);
        todo_cancel=(Button) findViewById(R.id.Btn_todo_cancel);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date_text.setText(Integer.toString(year) +"-"+Integer.toString(month)+"-"+Integer.toString(dayOfMonth));
            }
        });


        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(add_todo.this,MainActivity.class);
                startActivity(intent);
            }
        });
        /*
        todo_confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认按钮
            }
        });
    */
    }
}

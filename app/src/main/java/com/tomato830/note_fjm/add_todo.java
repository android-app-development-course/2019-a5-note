package com.tomato830.note_fjm;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tomato830.note_fjm.note.note;
import com.tomato830.note_fjm.noteUtil.MySQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class add_todo extends AppCompatActivity {
    CalendarView calendarView;
    TextView date_text;
    Toolbar toolbar_back;
    Button todo_confirm;
    Button todo_cancel;
    EditText title,content,tag;
    CheckBox notice;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);

        calendarView = (CalendarView)findViewById(R.id.calendar);
        date_text = (TextView)findViewById(R.id.tx_todo_date);
        toolbar_back = (Toolbar) findViewById(R.id.toolbar_back);
        todo_confirm = (Button)findViewById(R.id.Btn_todo_confirm);
        todo_cancel=(Button) findViewById(R.id.Btn_todo_cancel);
        title=(EditText) findViewById(R.id.ed_todo_title);
        content=(EditText) findViewById(R.id.ed_todo_task);
        tag=(EditText) findViewById(R.id.ed_todo_mark);
        notice=(CheckBox) findViewById(R.id.checkbox_isNotice);

        //日历控件选择后,textView改为相应的日期
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                date_text.setText(Integer.toString(year) +"-"+Integer.toString(month)+"-"+Integer.toString(dayOfMonth));
            }
        });


        toolbar_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent;
                intent = new Intent(add_todo.this,MainActivity.class);
                startActivity(intent);

                 */
                //另一种返回的方法
                finish();
            }
        });

        todo_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认按钮
                note nt=new note();
                nt.setTitle(title.getText().toString());
                nt.setContent(content.getText().toString());
                nt.setTag(note.string2HashSet("["+tag.getText().toString()+']'));
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date=simpleDateFormat.parse(date_text.getText().toString());
                    GregorianCalendar calendar=new GregorianCalendar();
                    calendar.setTime(date);
                    nt.setDeadline(calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                nt.setNotice(notice.isChecked());

                MySQLiteHelper mySQLiteHelper=new MySQLiteHelper(v.getContext(),1);
                SQLiteDatabase sqLiteDatabase=mySQLiteHelper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put("title",nt.getTitle());
                values.put("content",nt.getContent());
                values.put("tag",nt.getTag().toString());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                values.put("creationTime",sdf.format(nt.getCreationTime().getTime()));
                values.put("deadline",sdf.format(nt.getDeadline().getTime()));

                /*if (nt.isDone()){
                    values.put("isDone",1);
                } else {
                    values.put("isDone",0);
                }

                 */
                //刚创建的全部未完成
                values.put("isDone",0);

                if (nt.isNotice()){
                    values.put("isNotice",1);
                } else {
                    values.put("isNotice",0);
                }

                sqLiteDatabase.insert("todolist","空",values);
                sqLiteDatabase.close();

                //确认后退出
                finish();
            }
        });





    }
}

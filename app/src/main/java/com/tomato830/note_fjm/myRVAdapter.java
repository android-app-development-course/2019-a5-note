package com.tomato830.note_fjm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tomato830.note_fjm.note.note;
import com.tomato830.note_fjm.noteUtil.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class myRVAdapter extends RecyclerView.Adapter<myRVAdapter.myTVHolder> {

    private ArrayList<note> mArray;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public myRVAdapter(Context context,ArrayList<note> mArray){

        //mArray = context.getResources().getStringArray(R.array.testArray);
        this.mArray = mArray;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }


    @NonNull
    @Override
    public myRVAdapter.myTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new myTVHolder(mLayoutInflater.inflate(R.layout.item_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myTVHolder holder, final int position) {
        //holder.checkBox.setTag(position);
        //holder.item_titile.setText(mArray[position]);

        MySQLiteHelper helper = new MySQLiteHelper(mContext,1);;
        final SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        holder.item_titile.setText(mArray.get(position).getTitle());
        holder.item_body.setText(mArray.get(position).getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd|HH:mm");
        holder.item_time.setText(sdf.format(mArray.get(position).getCreationTime().getTime()));
        Log.v("note生成时间",sdf.format(mArray.get(position).getDeadline().getTime()));
        //救命！我懵逼了：在todo中勾选的item在下一次登入应用时时才能在finished中显示
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //令mArray的 属性 isDone 变为true
                mArray.get(position).setDone(true);
                //将影响记录入数据库中,修改属性isDone=1
                //获取 上述中 isDone变为true的 表项的id号

                ContentValues values = new ContentValues();
                values.put("isDone", "1");
                String[] whereArgs = {String.valueOf(mArray.get(position).getId())};//获取此表项的id

                sqLiteDatabase.update("todolist",values,"id=?",whereArgs);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mArray == null ? 0:mArray.size();
    }

    public class myTVHolder extends RecyclerView.ViewHolder{
        TextView item_titile;
        TextView item_body;
        TextView item_time;
        CheckBox checkBox;
        public myTVHolder(@NonNull View itemView) {
            super(itemView);
            item_titile = (TextView) itemView.findViewById(R.id.carditem_title);
            item_body = (TextView)itemView.findViewById(R.id.carditem_body);
            item_time = (TextView) itemView.findViewById(R.id.carditem_time);
            checkBox = (CheckBox) itemView.findViewById(R.id.finished_to_check);
        }


    }
}

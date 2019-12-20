package com.tomato830.note_fjm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tomato830.note_fjm.note.note;

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
    public void onBindViewHolder(@NonNull myTVHolder holder, int position) {
        //holder.item_titile.setText(mArray[position]);
        holder.item_titile.setText(mArray.get(position).getTitle());
        holder.item_body.setText(mArray.get(position).getContent());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd|HH:mm");
        holder.item_time.setText(sdf.format(mArray.get(position).getCreationTime().getTime()));
        Log.v("note生成时间",sdf.format(mArray.get(position).getDeadline().getTime()));
    }


    @Override
    public int getItemCount() {
        return mArray == null ? 0:mArray.size();
    }

    public class myTVHolder extends RecyclerView.ViewHolder{
        TextView item_titile;
        TextView item_body;
        TextView item_time;
        public myTVHolder(@NonNull View itemView) {
            super(itemView);
            item_titile = (TextView) itemView.findViewById(R.id.carditem_title);
            item_body = (TextView)itemView.findViewById(R.id.carditem_body);
            item_time = (TextView) itemView.findViewById(R.id.carditem_time);
        }



    }
}

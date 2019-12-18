package com.tomato830.note_fjm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myRVAdapter extends RecyclerView.Adapter<myRVAdapter.myTVHolder> {

    private String[] mArray;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public myRVAdapter(Context context){
        mArray = context.getResources().getStringArray(R.array.testArray);
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
        holder.mTextview.setText(mArray[position]);
    }


    @Override
    public int getItemCount() {
        return mArray == null ? 0:mArray.length;
    }

    public class myTVHolder extends RecyclerView.ViewHolder{
        TextView mTextview;

        public myTVHolder(@NonNull View itemView) {
            super(itemView);
            mTextview = (TextView) itemView.findViewById(R.id.carditem_title);
        }



    }
}

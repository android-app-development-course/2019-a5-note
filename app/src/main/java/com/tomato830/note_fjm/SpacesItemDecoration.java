package com.tomato830.note_fjm;

import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration  extends RecyclerView.ItemDecoration {
    private int space;
    public SpacesItemDecoration(int space){
        this.space = space;
    }

    public SpacesItemDecoration() {
        super();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
    }
}

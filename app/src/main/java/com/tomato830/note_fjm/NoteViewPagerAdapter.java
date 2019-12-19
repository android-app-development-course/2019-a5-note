package com.tomato830.note_fjm;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class NoteViewPagerAdapter extends PagerAdapter {
    private List<View> FragmentList;

    public NoteViewPagerAdapter(List<View> fragmentList) {
        FragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(FragmentList.get(position));
        return FragmentList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(FragmentList.get(position));
    }
}

package com.example.ecar;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecorationLinearLayout extends RecyclerView.ItemDecoration {

    private static final String TAG="SpacesItemDecoration";

    private int left;
    private int right;
    private int bottom;
    private int top;
    private int space;


    public SpacesItemDecorationLinearLayout(int space) {
        this.left = space;
        this.right = space;
        this.bottom = space;
        this.top = space;
    }

    public SpacesItemDecorationLinearLayout(int left,int top,int right,int bottom) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) != 0) {
            outRect.top = 0;
        }
    }
}
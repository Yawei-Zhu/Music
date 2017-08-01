package com.wind.music.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/5/9.
 */

public class DefaultDecoration extends RecyclerView.ItemDecoration {
    int dividerHeight = 2;
    private Paint paint = new Paint();

    public DefaultDecoration() {
        paint.setColor(Color.GRAY);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        int w = parent.getMeasuredWidth();
        int h = 0;
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            h = child.getBottom();
            c.drawRect(0, h, w, h + dividerHeight, paint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.indexOfChild(view) == parent.getChildCount() - 1) {
            outRect.bottom = 0;
        } else {
            outRect.bottom = dividerHeight;
        }
    }
}

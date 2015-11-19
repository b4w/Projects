package com.triangularlake.constantine.triangularlake.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class MyExpandableListView extends ExpandableListView {
    private android.view.ViewGroup.LayoutParams params;
    private int old_count = 0;

    public MyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = 0;
        if (getCount() != old_count) {
            old_count = getCount();
            params = getLayoutParams();
            params.height = getCount() * (old_count > 0 ? getChildAt(0).getHeight() : 0);
//            params.height = getCount() * 150;
            setLayoutParams(params);
        }
        super.onDraw(canvas);
//        for (int i = 0; i < getCount(); i++) {
//            iHeight = iHeight + (getChildAt(i).getHeight() + getDividerHeight());
//        }
//        params.height = iHeight - getDividerHeight();
//        setLayoutParams(params);

    }

}

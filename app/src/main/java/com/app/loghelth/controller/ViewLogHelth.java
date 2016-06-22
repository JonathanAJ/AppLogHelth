package com.app.loghelth.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ViewLogHelth extends View{


    public ViewLogHelth(Context context) {
        super(context);
    }

    public ViewLogHelth(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewLogHelth(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
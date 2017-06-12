package com.example.viktor.sensesmart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


/*
* This function class is used to delete the swiping animation for some classes.
* The fact that we were supposed to create a new class to delete this animation seems
* pretty wierd but here we are, and it works so why not.*/
public class NoSwipeViewPager extends android.support.v4.view.ViewPager{
    private boolean enabled = false;

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return enabled ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return enabled ? super.onInterceptTouchEvent(event) : false;
    }

    public void setPagingEnabled() {
        this.enabled = enabled;
    }

    public boolean isPagingEnabled() {
        return enabled;
    }

}
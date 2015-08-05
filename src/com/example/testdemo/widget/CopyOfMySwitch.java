
package com.example.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class CopyOfMySwitch extends MySwitch {

    public CopyOfMySwitch(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In CopyOfMySwitch(context)" + context);
    }

    public CopyOfMySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In CopyOfMySwitch(context, attrs)" + context + " " + attrs);
    }

    public CopyOfMySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In CopyOfMySwitch(context, attrs, defStyleAttr)" + context + " " + attrs + " "
                + defStyleAttr);
    }

    public CopyOfMySwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In CopyOfMySwitch(context, attrs, defStyleAttr, defStyleRes)" + context + " "
                + attrs + " " + defStyleAttr + " " + defStyleRes);
    }

}

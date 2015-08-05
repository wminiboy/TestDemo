
package com.example.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;

public class MySwitch extends Switch {

    public MySwitch(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In MySwitch(context)" + context);
    }

    public MySwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In MySwitch(context, attrs)" + context + " " + attrs);
        
        attrs.getAttributeCount();
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In MySwitch(context, attrs, defStyleAttr)" + context + " " + attrs + " "
                + defStyleAttr);
    }

    public MySwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // TODO Auto-generated constructor stub
        Log.d("yanjun", "In MySwitch(context, attrs, defStyleAttr, defStyleRes)" + context + " "
                + attrs + " " + defStyleAttr + " " + defStyleRes);
//        
//        final TypedArray a = context.obtainStyledAttributes(
//                attrs, com.android.internal.R.styleable.Switch, defStyleAttr, defStyleRes);
    }

}

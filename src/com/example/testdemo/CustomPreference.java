
package com.example.testdemo;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomPreference extends Preference {

    public CustomPreference(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    
    public CustomPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected View onCreateView(ViewGroup parent) {
        // TODO Auto-generated method stub

        View view = super.onCreateView(parent);
        Log.d("yanjun", "view . . ." + view.getPaddingLeft() + " " + 
        view.getPaddingTop() + " " +
        view.getPaddingRight() + " " +
        view.getPaddingBottom());
        
        Log.d("yanjun", "view . . ." + view.getLayoutParams().height);
        
        TextView tv = new TextView(getContext());
        tv.setText("haha");
        tv.setLayoutParams(view.getLayoutParams());
        Log.d("yanjun", "onCreateView . . .");
        
        
        return tv;
    }

}

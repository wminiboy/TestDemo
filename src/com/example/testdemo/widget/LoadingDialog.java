
package com.example.testdemo.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testdemo.R;

public class LoadingDialog extends AlertDialog {

    private TextView tips_loading_msg;
    private ProgressBar tips_loading_progress;

    private String message = null;

    public LoadingDialog(Context context) {
        super(context);
        this.setCanceledOnTouchOutside(false);
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.setCanceledOnTouchOutside(false);
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.widget_loading_dialog);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
        tips_loading_progress = (ProgressBar) findViewById(R.id.loading_progress);
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

    public void hideProgressAndSetText(String message){
        tips_loading_progress.setVisibility(View.GONE);
        tips_loading_msg.setText(message);
    }

}

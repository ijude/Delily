package com.sh.dilily.widget;

import com.sh.dilily.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class LoadingDialog extends AlertDialog {

    private TextView loading_msg;

    private String message = null;

    public LoadingDialog(Context context) {
        super(context);
        message = getContext().getResources().getString(R.string.loading);
    }

	public LoadingDialog(Context context, String message) {
		super(context);
		this.message = message;
//		this.setCancelable(false);
    }

	public LoadingDialog(Context context, int theme, String message) {
		super(context, theme);
		this.message = message;
//		this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.loading);
        loading_msg = (TextView) findViewById(R.id.loading_msg);
        loading_msg.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

}

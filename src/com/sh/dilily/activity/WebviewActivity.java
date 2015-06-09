package com.sh.dilily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.sh.dilily.R;

public class WebviewActivity extends DelilyActivity {

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		webview = (WebView) findViewById(R.id.webview);
		setTitle(null, null, "返回", null);

		Intent intent = getIntent();
//		WebSettings setting = webview.getSettings();
//		setting.setJavaScriptEnabled(true);

		String url = intent.getStringExtra("url");
		webview.loadUrl(url);			// "file:///android_asset/gangqin.html"
	}
}

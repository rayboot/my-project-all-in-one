package com.rayboot.hanzitingxie;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TipActivity extends Activity {

	String title;
	String url;

	WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);

		title = getIntent().getStringExtra("data_answer");
		url = getIntent().getStringExtra("data_tip");

		if (!url.contains("http")) {
			url = "http://baike.baidu.com/" + url;
		}

		mWebView = (WebView) findViewById(R.id.wvTip);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
		webSettings.setJavaScriptEnabled(true);// 设置支持javascript脚本
		webSettings.setBuiltInZoomControls(true);// 设置支持缩放
		mWebView.loadUrl(url);
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.equals("about:blank")) {
					return false;
				}
				view.loadUrl(url);// 点击超链接的时候重新在原来进程上加载URL
				return true;
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

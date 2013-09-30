package com.rayboot.hanzitingxie;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TipActivity extends MyBaseActivity {

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
			url = "http://wapbaike.baidu.com" + url;
		}

		mWebView = (WebView) findViewById(R.id.wvTip);
		mWebView.setVisibility(View.GONE);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
		webSettings.setJavaScriptEnabled(true);// 设置支持javascript脚本
		webSettings.setBuiltInZoomControls(true);// 设置支持缩放
		mWebView.loadUrl(url);
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				view.loadUrl("javascript:var orgHtml = document.getElementsByTagName('body')[0].innerHTML;document.getElementsByTagName('body')[0].innerHTML=orgHtml.replace(/"
						+ title + "/g,'*');");
			}

		});
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				if (newProgress >= 100) {
					mWebView.setVisibility(View.VISIBLE);
				}
			}

		});
		// HtmlParser parser = new HtmlParser(mWebView, url, title, this);
		// parser.execute((Void) null);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

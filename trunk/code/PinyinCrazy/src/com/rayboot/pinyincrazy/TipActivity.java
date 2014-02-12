package com.rayboot.pinyincrazy;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TipActivity extends MyBaseActivity {

	String title;
	String url;
	String pinyin;
	String keypinyin;
	String keychar;

	@InjectView(R.id.wvTip)
	WebView mWebView;

	String yinbiaoString = "āáǎàōóǒòēéěèīíǐìūúǔùǖǘǚǜ";
	String meiyinbiao = "aaaaooooeeeeiiiiuuuuüüüü";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ButterKnife.inject(this);

		keypinyin = getIntent().getStringExtra("data_key_pinyin");
		keychar = getIntent().getStringExtra("data_key_char");
		title = getIntent().getStringExtra("data_answer");
		url = getIntent().getStringExtra("data_tip");
		pinyin = getIntent().getStringExtra("data_pinyin");

		if (!url.contains("http")) {
			url = "http://wapbaike.baidu.com" + url;
		}

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
				StringBuffer jsString = new StringBuffer();
				String[] pinyinStrings = pinyin.split(" ");

				jsString.append("javascript:var searchbar = document.getElementById('toolbar-search');searchbar.parentNode.removeChild(searchbar);");
				jsString.append("javascript:var btnconfig = document.getElementById('btn-config');btnconfig.parentNode.removeChild(btnconfig);");
				for (int i = 0; i < title.length(); i++) {
					jsString.append("javascript:var orgHtml = document.getElementsByTagName('body')[0].innerHTML;document.getElementsByTagName('body')[0].innerHTML=orgHtml.replace(/");
					jsString.append(pinyinStrings[i]);
					jsString.append("/gi,'");
					jsString.append(title.substring(i, i + 1).trim());
					jsString.append("');");

				}
				view.loadUrl(jsString.toString());
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
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}
}

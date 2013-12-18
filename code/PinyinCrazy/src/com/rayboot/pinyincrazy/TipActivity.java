package com.rayboot.pinyincrazy;

import org.holoeverywhere.app.Dialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TipActivity extends Activity {

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip);
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
				String jsString = "";
				String[] pinyinStrings = pinyin.split(" ");

				for (int i = 0; i < title.length(); i++) {
					jsString += "javascript:var orgHtml = document.getElementsByTagName('body')[0].innerHTML;document.getElementsByTagName('body')[0].innerHTML=orgHtml.replace(/"
							+ pinyinStrings[i]
							+ "/gi,'"
							+ title.substring(i, i + 1).trim() + "');";
				}
				view.loadUrl(jsString);
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
}

package com.rayboot.mywebview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
	private WebView mWebView;
	private ValueCallback<Uri> mUploadMessage;
	final static int FILE_SELECTED = 4;
	long lastBackTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWebView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setAllowFileAccess(true);// ������������ļ�����
		webSettings.setJavaScriptEnabled(true);// ����֧��javascript�ű�
		webSettings.setBuiltInZoomControls(true);// ����֧������
		mWebView.loadUrl("http://wapah.189.cn");
		mWebView.setDownloadListener(new MyWebViewDownloader(this));
		mWebView.setWebChromeClient(new TestWebChromeClient(
				new WebChromeClient()) {

			@Override
			public void openFileChooser(ValueCallback<Uri> uploadFile) {
				// TODO Auto-generated method stub

				mUploadMessage = uploadFile;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				MainActivity.this.startActivityForResult(Intent.createChooser(
						i, getString(R.string.choose_upload)), FILE_SELECTED);
			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback uploadMsg,
					String acceptType) {
				openFileChooser(uploadMsg);
			}

			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
					String acceptType, String capture) {
				openFileChooser(uploadMsg);

			}
		});
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.equals("about:blank")) {
					return false;
				}
				view.loadUrl(url);// ��������ӵ�ʱ��������ԭ�������ϼ���URL
				return true;
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (mWebView.canGoBack()) {
				mWebView.goBack();
				return true;
			} else {

				if (lastBackTime == 0) {
					lastBackTime = System.currentTimeMillis();
					Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_LONG).show();
					return false;
				} else {
					if (System.currentTimeMillis() - lastBackTime <= 3000) {
						return super.onKeyDown(keyCode, event);
					} else {
						lastBackTime = System.currentTimeMillis();
						Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_LONG)
								.show();
						return false;
					}
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �����ļ�ѡ��
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == FILE_SELECTED) {
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;

		}
	}

}

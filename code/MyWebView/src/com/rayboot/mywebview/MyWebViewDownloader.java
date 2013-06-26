package com.rayboot.mywebview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

public class MyWebViewDownloader implements DownloadListener {
	private Context mContext;

	public MyWebViewDownloader(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public void onDownloadStart(String url, String userAgent,
			String contentDisposition, String mimetype, long contentLength) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		mContext.startActivity(intent);
	}

}

package com.rayboot.hanzitingxie;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

public class HtmlParser extends AsyncTask<Void, Void, String> {

	private String mUrl;
	private WebView webView;
	private static final String TAG = "HtmlParser";
	private Context mContext;
	public static String Js2JavaInterfaceName = "JsUseJava";
	public List<String> imgUrls = new ArrayList<String>();
	private String mTitle;

	public HtmlParser(WebView webView, String url, String title, Context context) {
		this.webView = webView;
		mUrl = url;
		mContext = context;
		this.mTitle = title;
	}

	@Override
	protected String doInBackground(Void... params) {

		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(mUrl), 15000);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (doc == null)
			return null;
		String htmlText = doc.html();

		int titleCount = mTitle.length();
		for (int i = 0; i < titleCount; i++) {
			htmlText = htmlText.replaceAll(mTitle.substring(i, i + 1), "*");
		}
		return htmlText;
	}


	@Override
	protected void onPostExecute(String result) {
		webView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
		super.onPostExecute(result);
	}
}

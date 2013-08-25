package com.rayboot.qchong;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil {

	private String SERVER = "http://61.191.44.170";
	public static String LOGIN = "/clt/clt/update.msp";
	public static String SENDMSM = "/clt/clt/sendQCoinSms.msp";
	public static String DOORDER = "/clt/clt/qCoinConfirm.msp";

	private static AsyncHttpClient asyncClient = new AsyncHttpClient();

	public static void get(String url, AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		asyncClient.get(url, params, responseHandler);
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		Log.d("HTTP GET", url + "?" + params.toString());
		asyncClient.get(url, params, responseHandler);
	}

	public static void get(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		Log.d("HTTP GET", url + "?" + params.toString());
		asyncClient.get(context, url, params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		Log.d("HTTP post", url + "?" + params.toString());
		asyncClient.post(url, params, responseHandler);
	}

	public static void post(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		Log.d("HTTP post", url + "?" + params.toString());
		asyncClient.post(context, url, params, responseHandler);
	}

}

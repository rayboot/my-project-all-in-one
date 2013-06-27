package com.example.testnetwork;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class NetWorkUtily {

	private static final int GSM_DATA_TYPE = 0;
	private static final int CDMA_DATA_TYPE = 1;
	private static final int TD_SCDMA_DATA_TYPE = 2;

	public static final Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");
	public static final Uri GSM_PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn2");
	public static final Uri CDMA_PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");
	public static final Uri APN_TABLE_URI = Uri
			.parse("content://telephony/carriers");

	public static String[] APN_NAME = {};

	public enum NetWorkCarrier {

		CARRIER_NONE, // 未知
		CARRIER_CM, // 中国移动
		CARRIER_CT, // 中国电信
		CARRIER_CU // 中国联通
	}

	public enum NetWorkType {

		TYPE_UK, // 未知
		TYPE_WIFI, // WIFI
		TYPE_WAP, // WAP
		TYPE_NET // NET
	}

	/**
	 * 获取默认网络制式
	 * 
	 * @param context
	 * @return
	 */
	public static int getDefaultDataNetwork(Context context) {
		int defaultNet = GSM_DATA_TYPE;

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int type = tm.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_CDMA)
			defaultNet = CDMA_DATA_TYPE;
		else
			defaultNet = GSM_DATA_TYPE;

		return defaultNet;
	}

	/**
	 * 获取网络运营商 //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
	 * 
	 * @param context
	 * @return
	 */
	public static NetWorkCarrier getNetWorkCarrier(Context context) {
		NetWorkCarrier curCarrier = NetWorkCarrier.CARRIER_NONE;
		curCarrier = getNetWorkCarrierFromImsi(context);
		if (curCarrier != NetWorkCarrier.CARRIER_NONE) {
			return curCarrier;
		}

		curCarrier = getNetWorkCarrierFromTelephony(context);
		return curCarrier;
	}

	public static NetWorkCarrier getNetWorkCarrierFromImsi(Context context) {

		String imsi = getIMSI(context);
		if (!TextUtils.isEmpty(imsi)) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")
					|| imsi.startsWith("46007")) {
				return NetWorkCarrier.CARRIER_CM;
			} else if (imsi.startsWith("46001")) {
				return NetWorkCarrier.CARRIER_CU;
			} else if (imsi.startsWith("46003")) {

				return NetWorkCarrier.CARRIER_CT;
			}
		}
		return NetWorkCarrier.CARRIER_NONE;
	}

	public static NetWorkCarrier getNetWorkCarrierFromTelephony(Context context) {
		ApnNode apnNode = getDefaultApnNode(context);
		String numeric = apnNode == null ? null : apnNode.numeric;
		if (!TextUtils.isEmpty(numeric)) {
			if (numeric.startsWith("46000") || numeric.startsWith("46002")
					|| numeric.startsWith("46007")) {

				return NetWorkCarrier.CARRIER_CM;
			} else if (numeric.startsWith("46001")) {

				return NetWorkCarrier.CARRIER_CU;
			} else if (numeric.startsWith("46003")) {

				return NetWorkCarrier.CARRIER_CT;
			}
		}
		return NetWorkCarrier.CARRIER_NONE;
	}

	/**
	 * 获取IMSI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int simState = telManager.getSimState();
		if (simState == TelephonyManager.SIM_STATE_READY) {
			return telManager.getSubscriberId();
		}
		return null;
	}

	/**
	 * 获取缺省的网络类型
	 * 
	 * @param context
	 * @return
	 */
	public static ApnNode getDefaultApnNode(Context context) {

		ApnNode sDefaultApnNode = null;
		Uri tmpUri = GSM_PREFERRED_APN_URI;
		int dataType = getDefaultDataNetwork(context);
		if (dataType == GSM_DATA_TYPE) {
			tmpUri = PREFERRED_APN_URI;
		} else if (dataType == CDMA_DATA_TYPE) {
			tmpUri = CDMA_PREFERRED_APN_URI;
		} else {
			tmpUri = GSM_PREFERRED_APN_URI;
		}

		sDefaultApnNode = null;
		ContentResolver cResolver = context.getContentResolver();
		Cursor cr = cResolver.query(tmpUri, null, null, null, null);
		if (cr != null && cr.moveToFirst()) {
			sDefaultApnNode = new ApnNode();
			try {
				String[] columnNames = cr.getColumnNames();
				for (String columnIndex : columnNames) {
					int i = cr.getColumnIndex(columnIndex);
					if ("port".equals(columnIndex)) {
						sDefaultApnNode.port = cr.getString(i);
					} else if ("user".equals(columnIndex)) {
						sDefaultApnNode.user = cr.getString(i);
					} else if ("server".equals(columnIndex)) {
						sDefaultApnNode.server = cr.getString(i);
					} else if ("proxy".equals(columnIndex)) {
						sDefaultApnNode.proxy = cr.getString(i);
					} else if ("numeric".equals(columnIndex)) {
						sDefaultApnNode.numeric = cr.getString(i);
					} else if ("apn".equals(columnIndex)) {
						sDefaultApnNode.apn = cr.getString(i);
					} else if ("name".equals(columnIndex)) {
						sDefaultApnNode.name = cr.getString(i);
					} else if ("_id".equals(columnIndex)) {
						sDefaultApnNode._id = cr.getString(i);
					} else if ("type".equals(columnIndex)) {
						sDefaultApnNode.type = cr.getString(i);
					} else if ("current".equals(columnIndex)) {
						sDefaultApnNode.current = cr.getString(i);
					} else if ("password".equals(columnIndex)) {
						sDefaultApnNode.password = cr.getString(i);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return sDefaultApnNode;
	}

	/**
	 * 获取联网类型
	 * 
	 * @return
	 */
	public static NetWorkType getNetWorkType(Context context) {
		if (!isNetworkConnected(context)) {
			return NetWorkType.TYPE_UK;
		}
		if (isWifiConnected(context)) {
			return NetWorkType.TYPE_WIFI;
		} else if (!isMobileConnected(context)) {
			return NetWorkType.TYPE_UK;
		}

		ApnNode apnNode = getDefaultApnNode(context);

		if (TextUtils.isEmpty(apnNode.proxy) || TextUtils.isEmpty(apnNode.port)) {
			return NetWorkType.TYPE_NET;
		} else if (!TextUtils.isEmpty(apnNode.proxy)
				&& !TextUtils.isEmpty(apnNode.port)) {
			return NetWorkType.TYPE_WAP;
		} else {
			return NetWorkType.TYPE_UK;
		}
	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断WIFI网络是否可用
	 * 
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 利用ContentProvider将添加的APN数据添加进入数据库
	 * 
	 * @return
	 */
	public static int insertAPN(Context context, NetWorkCarrier carrier,
			NetWorkType type) throws SQLException {
		int apnId = -1;
		if (carrier == NetWorkCarrier.CARRIER_NONE) {
			return -1;
		}
		if (type != NetWorkType.TYPE_NET && type != NetWorkType.TYPE_WAP) {
			return -1;
		}

		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();

		ApnNode apnNode = new ApnNode(carrier, type, getIMSI(context));

		values.put("name", apnNode.name);
		values.put("apn", apnNode.apn);
		values.put("type", apnNode.type);
		values.put("numeric", apnNode.numeric);
		values.put("mcc", apnNode.numeric.substring(0, 3));
		values.put("mnc",
				apnNode.numeric.substring(3, apnNode.numeric.length()));
		values.put("proxy", apnNode.proxy);
		values.put("port", apnNode.port);
		values.put("mmsproxy", "");
		values.put("mmsport", "");
		values.put("user", apnNode.user);
		values.put("server", apnNode.server);
		values.put("password", apnNode.password);
		values.put("type", apnNode.type);
		values.put("mmsc", "");
		if (carrier == NetWorkCarrier.CARRIER_CT) {
			values.put("ppppwd", apnNode.ppppwd);
			values.put("visible", "1");
		}

		Cursor c = null;

		Uri newRow = resolver.insert(APN_TABLE_URI, values);
		if (newRow != null) {
			c = resolver.query(newRow, null, null, null, null);
			int idindex = c.getColumnIndex("_id");
			c.moveToFirst();
			apnId = c.getShort(idindex);
		}

		if (c != null) {
			c.close();
		}

		return apnId;
	}

	/**
	 * 根据apnName查找apn是否存在
	 * 
	 * @param apnId
	 * @return
	 */
	public static int isAPNExist(Context context, NetWorkCarrier carrier,
			NetWorkType type) {
		ApnNode apnNode = new ApnNode(carrier, type, getIMSI(context));
		ContentResolver resolver = context.getContentResolver();
		Cursor c = resolver.query(APN_TABLE_URI, new String[] { "_id", "name",
				"apn" }, "apn like '" + apnNode.name + "'", null, null); // 从apn列表中查找apn名称为myapn的apn信息
		if (c != null && c.moveToNext()) {
			return c.getShort(c.getColumnIndex("_id")); // 获取该apn的id信息
			// System.out.println("APN已经存在");
		} else {
			// System.out.println("APN不存在");
			return -1;
		}
	}

	/**
	 * 根据apnId将设置的APN选中
	 * 
	 * @param apnId
	 * @return
	 */
	public static boolean setDefaultApn(Context context, int apnId)
			throws SQLException {
		boolean res = false;
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put("apn_id", apnId);

		resolver.update(PREFERRED_APN_URI, values, null, null);
		Cursor c = resolver.query(PREFERRED_APN_URI, new String[] { "name",
				"apn" }, "_id=" + apnId, null, null);
		if (c != null) {
			res = true;
			c.close();
		}

		return res;
	}

	/**
	 * 弹出设置apn的窗口
	 * 
	 * @param apnId
	 * @return
	 */
	public static void showAPNSetting(Context context) {
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.android.settings",
				"com.android.settings.ApnSettings");
		intent.setComponent(comp);
		intent.setAction("android.intent.action.VIEW");
		context.startActivity(intent);
	}

	/**
	 * 设置wifi使用状态
	 * 
	 * @param true 为可用 false为 不可用
	 * @return
	 */
	public static void setWifiDataEnabled(Context context, boolean flag) {
		// 取得WifiManager对象
		WifiManager mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		if (flag) {
			if (!mWifiManager.isWifiEnabled()) {
				mWifiManager.setWifiEnabled(true);
			}
		} else {
			if (mWifiManager.isWifiEnabled()) {
				mWifiManager.setWifiEnabled(false);
			}
		}
	}

	/**
	 * 设置数据使用状态 注：该功能只支持2.3+版本系统
	 * 
	 * @param true 为可用 false为 不可用
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static int setMobileDataEnabled(Context context, boolean enabled)
			throws ClassNotFoundException, SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {
		try {
			final ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			final Class connectivityManager = Class.forName(cm.getClass()
					.getName());
			final Method[] methods = connectivityManager.getDeclaredMethods();
			for (final Method method : methods) {

				if (method.getName().contains("setMobileDataEnabled")) {
					method.invoke(cm, true);
				}
			}
		} catch (final ClassNotFoundException e) {
			Log.e("Class", e.getMessage());
		} catch (final IllegalArgumentException e) {
			Log.e("Class", e.getMessage());
		}
		return 0;
	}
}

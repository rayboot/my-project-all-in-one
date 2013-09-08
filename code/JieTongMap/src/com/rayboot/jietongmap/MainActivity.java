package com.rayboot.jietongmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.rayboot.jietongmap.obj.POIObj;
import com.rayboot.jietongmap.util.BMapUtil;
import com.rayboot.jietongmap.util.Global;
import com.rayboot.jietongmap.util.Util;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends BaseActivity {

	public MainActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}

	// final int MORE_GET_POINT = 1;
	final int MORE_FEEBACK = 2;
	final int MORE_ABOUT = 3;
	final int MORE_SHARE = 4;
	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myLocationListener = new MyLocationListenner();
	// 定位图层
	MyLocationOverlay myLocationOverlay = null;
	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位

	@InjectView(R.id.bmapView)
	MapView mMapView = null; // 地图View
	private MapController mMapController = null;

	@InjectView(R.id.btnInputActivity)
	Button btnInputActivity;
	@InjectView(R.id.ivLoc)
	ImageView ivLoc;
	@InjectView(R.id.llDetail)
	LinearLayout llDetail;
	@InjectView(R.id.tvTitle)
	TextView tvTitle;
	@InjectView(R.id.btnNav)
	Button btnNav;
	@InjectView(R.id.btnTel)
	Button btnTel;

	private MyOverlay mOverlay = null;
	private PopupOverlay pop = null;
	private ArrayList<OverlayItem> mItems = null;
	private MapView.LayoutParams layoutParam = null;
	private OverlayItem mCurItem = null;
	private View viewCache = null;
	private View popupInfo = null;
	private Button button = null;

	private List<POIObj> allPOIObjs;
	FeedbackAgent agent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setSlidingActionBarEnabled(true);
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		MyApplication app = (MyApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(MyApplication.strKey,
					new MyApplication.MyGeneralListener());
		}
		setContentView(R.layout.activity_main);
		Views.inject(this);
		getSlidingMenu().addIgnoredView(mMapView);
		getSlidingMenu().setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				changePoi();
			}
		});

		btnInputActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						SearchMapActivity.class));
			}
		});
		ivLoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestLocClick();
			}
		});

		allPOIObjs = POIObj.getAllData();

		initMap();
		initLoc();
		initMyLocOverlay();
		initOverlay();

		initUMeng();
	}

	private void initUMeng() {
		// 友盟意见反馈检索
		agent = new FeedbackAgent(this);
		agent.sync();
		// 友盟检测更新
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent.showUpdateDialog(MainActivity.this,
							updateInfo);
					break;
				}
			}
		});
	}

	public void initMap() {
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);
	}

	public void initLoc() {
		// 定位初始化
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	public void initMyLocOverlay() {
		// 定位图层初始化
		myLocationOverlay = new MyLocationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// 修改定位数据后刷新图层生效
		mMapView.refresh();
	}

	public void initOverlay() {
		/**
		 * 创建自定义overlay
		 */
		mOverlay = new MyOverlay(getResources().getDrawable(
				R.drawable.icon_gcoding), mMapView);

		for (POIObj poiInfo : allPOIObjs) {
			mOverlay.addItem(new OverlayItem(new GeoPoint(poiInfo.la,
					poiInfo.lo), poiInfo.name, poiInfo.tel));
		}

		/**
		 * 保存所有item，以便overlay在reset后重新添加
		 */
		mItems = new ArrayList<OverlayItem>();
		mItems.addAll(mOverlay.getAllItem());
		/**
		 * 将overlay 添加至MapView中
		 */
		mMapView.getOverlays().add(mOverlay);
		/**
		 * 刷新地图
		 */
		mMapView.refresh();

		/**
		 * 向地图添加自定义View.
		 */

		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);

		button = new Button(this);
		button.setBackgroundResource(R.drawable.icon_loc_sel);

		/**
		 * 创建一个popupoverlay
		 */
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				if (index == 0) {
					// 更新item位置
					showPop(false);
					GeoPoint p = new GeoPoint(mCurItem.getPoint()
							.getLatitudeE6() + 5000, mCurItem.getPoint()
							.getLongitudeE6() + 5000);
					mCurItem.setGeoPoint(p);
					mOverlay.updateItem(mCurItem);
					mMapView.refresh();
				} else if (index == 2) {
					// 更新图标
				}
			}
		};
		pop = new PopupOverlay(mMapView, popListener);

	}

	/**
	 * 手动触发一次定位请求
	 */
	public void requestLocClick() {
		isRequest = true;
		mLocClient.requestLocation();
		Toast.makeText(this, "正在定位......", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			if (isRequest || isFirstLoc) {
				// 移动地图到定位点
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
				isRequest = false;
			}
			// 首次定位完成
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocClient != null) {
			mLocClient.stop();
		}
		mMapView.destroy();
		super.onDestroy();
	}

	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			mCurItem = getItem(index);
			showPop(true);
			mMapController.animateTo(mCurItem.getPoint());
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			if (pop != null) {
				showPop(false);
				mMapView.removeView(button);
			}
			return false;
		}

	}

	public void changePoi() {
		allPOIObjs.clear();
		mOverlay.removeAll();
		showPop(false);
		mMapView.removeView(button);
		mMapView.refresh();

		Iterator<Entry<String, Boolean>> it = Global.selTypeMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<String, Boolean> entry = (java.util.Map.Entry<String, Boolean>) it
					.next();
			if (entry.getValue()) {
				allPOIObjs.addAll(POIObj.getAllDataByType(entry.getKey()));
			}
		}
		initOverlay();
	}

	public void showPop(boolean isShow) {
		if (isShow) {
			Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo) };
			pop.showPopup(bitMaps, mCurItem.getPoint(), 0);
			tvTitle.setText(mCurItem.getTitle());
			btnNav.setTag(mCurItem.getPoint());
			btnTel.setTag(mCurItem.getSnippet());
			btnTel.setEnabled(!TextUtils.isEmpty(mCurItem.getSnippet().trim()));
			llDetail.setVisibility(View.VISIBLE);
		} else {
			if (pop != null) {
				pop.hidePop();
			}
			llDetail.setVisibility(View.GONE);
		}
	}

	public void onNavClick(View view) {
		if (locData == null) {
			Toast.makeText(this, "未获取当前位置", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(MainActivity.this, NavActivity.class);
		intent.putExtra("start_la", (int) (locData.latitude * 1e6));
		intent.putExtra("start_lo", (int) (locData.longitude * 1e6));
		intent.putExtra("end_la", mCurItem.getPoint().getLatitudeE6());
		intent.putExtra("end_lo", mCurItem.getPoint().getLongitudeE6());
		this.startActivity(intent);
	}

	public void onTelClick(View view) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ view.getTag()));
		MainActivity.this.startActivity(intent);// 内部类
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
		// sub.add(0, MORE_GET_POINT, 0, "获取积分");
		sub.add(0, MORE_FEEBACK, 0, "意见反馈");
		sub.add(0, MORE_SHARE, 0, "分享");
		sub.add(0, MORE_ABOUT, 0, "关于");
		MenuItem subMenu1Item = sub.getItem();
		subMenu1Item.setIcon(R.drawable.align_just_icon);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case MORE_GET_POINT:
		// AppConnect.getInstance(this).showOffers(this);
		// break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case MORE_FEEBACK:
			agent.startFeedbackActivity();
			break;
		case MORE_SHARE:
			Util.shareSomethingText(MainActivity.this, "分享",
					"我使用  #捷通卡地图#  知道你也有几张捷通卡，知道能使用捷通卡的商户在哪吗？");
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}

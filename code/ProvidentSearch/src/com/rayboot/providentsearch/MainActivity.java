package com.rayboot.providentsearch;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;
import cn.waps.AdView;
import cn.waps.AppConnect;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.NotificationType;
import com.umeng.fb.UMFeedbackService;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends SherlockActivity {
	final int MORE_CLEAR_CACHE = 1;
	final int MORE_FEEBACK = 2;
	final int MORE_ABOUT = 3;
	final int MORE_SHARE = 4;
	final String encode = "f47b2f2029d7bd2cfce18aed1fbb25f2ec5ec7b20c95f56be20be49a0e99b51bfb6ec815e3fc02f276d5765048ba378f9e6108a7be020baa";

	@InjectView(R.id.etIDCard)
	EditText etIDCard;
	@InjectView(R.id.etPsd)
	EditText etPsd;
	@InjectView(R.id.btnSearch)
	Button btnSearch;
	@InjectView(R.id.listHistory)
	ListView listHistory;
	@InjectView(R.id.tvHistory)
	TextView tvHistory;
	@InjectView(R.id.AdLinearLayout)
	LinearLayout AdLinearLayout;

	List<ProvidentObj> historyDatas;
	HistoryAdapter historyAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);
		historyDatas = ProvidentObj.getAllFromDB(this);

		if (historyDatas == null || historyDatas.size() == 0) {
			tvHistory.setVisibility(View.GONE);
			listHistory.setVisibility(View.GONE);
		} else {
			fillHistory();
		}

		initUMeng();

		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String card = etIDCard.getText().toString();
				String psd = etPsd.getText().toString();
				if (TextUtils.isEmpty(card)) {
					Toast.makeText(MainActivity.this, "身份证号码为空。 ",
							Toast.LENGTH_SHORT).show();
					return;
				}
				doSearch(card, psd);
			}
		});
		initWAPS();
	}

	private void initWAPS() {
		AppConnect.getInstance(this);
		// 禁用错误报告
		AppConnect.getInstance(this).setCrashReport(false);
		new AdView(this, AdLinearLayout).DisplayAd();
	}

	private void initUMeng() {
		// 友盟意见反馈检索
		UMFeedbackService.enableNewReplyNotification(this,
				NotificationType.AlertDialog);
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
				case 1: // has no update
				case 2: // none wifi
				case 3: // time out
					break;
				}
			}
		});
	}

	private void saveData() {
		String card = etIDCard.getText().toString();
		String psd = etPsd.getText().toString();

		if (TextUtils.isEmpty(psd)) {
			psd = "123456";
		}

		if (ProvidentObj.getFromDB(this, card) != null) {
			ProvidentObj.deleteFromDB(this, card);
		}
		new ProvidentObj(card, psd).save();
	}

	private void fillHistory() {
		historyAdapter = new HistoryAdapter(this,
				ProvidentObj.getAllFromDB(this));
		listHistory.setAdapter(historyAdapter);
		listHistory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				etIDCard.setText(historyDatas.get(arg2).idCard);
				etPsd.setText(historyDatas.get(arg2).psd);
				doSearch(historyDatas.get(arg2).idCard,
						historyDatas.get(arg2).psd);
			}
		});
	}

	private void doSearch(String idCard, String psd) {
		if (TextUtils.isEmpty(psd)) {
			psd = "123456";
		}

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("hm", idCard);
		params.put("mm", psd);
		params.put("appID", "100100-0000202-000");

		client.get(Encrypt.decrypt(encode, "raybooter.work"), params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						super.onSuccess(response);
						Gson gson = new Gson();
						SearchDataObj sdo = gson.fromJson(response,
								SearchDataObj.class);
						if (sdo.resultCode.equals("0000")) {
							if (TextUtils.isEmpty(sdo.zgxm)) {
								Toast.makeText(MainActivity.this, sdo.tip,
										Toast.LENGTH_SHORT).show();
								return;
							}
							saveData();
							Intent intent = new Intent(MainActivity.this,
									DetailActivity.class);
							intent.putExtra("data_string", response);
							startActivity(intent);
							overridePendingTransition(
									R.anim.base_slide_right_in,
									R.anim.stay_anim);
						}
					}

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
		sub.add(0, MORE_CLEAR_CACHE, 0, "清空搜索历史");
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
		case android.R.id.home:
		case 0:
			return false;
		case MORE_CLEAR_CACHE:
			ProvidentObj.deleteAllFromDB(this);
			tvHistory.setVisibility(View.GONE);
			listHistory.setVisibility(View.GONE);
			break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.base_slide_right_in,
					R.anim.stay_anim);
			break;
		case MORE_FEEBACK:
			UMFeedbackService.openUmengFeedbackSDK(this);
			break;
		case MORE_SHARE:
			shareSomethingText(this, "合肥公积金查询",
					getResources().getString(R.string.share_main_content));
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public boolean shareSomethingText(Context context, String title,
			String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType("text/plain"); // 分享发送到数据类型
		intent.putExtra(Intent.EXTRA_SUBJECT, title); // 分享的主题
		intent.putExtra(Intent.EXTRA_TEXT, content); // 附带的说明信息
		context.startActivity(Intent.createChooser(intent, "分享"));
		return true;
	}
}

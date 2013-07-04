package com.rayboot.housecalculator;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;

public class MainActivity extends SherlockActivity {
	final int MORE_FEEBACK = 2;
	final int MORE_ABOUT = 3;
	final int MORE_SHARE = 4;
	final String rateUrl = "http://wxcs.ahhouse.com/yiDong/oldhouse.php/Index/index/type/rate/ps/100/isXml/1/";
	List<RateObj> rateList;
	String[] mRateStrings = null;
	final String[] yearArrays = new String[] { "2年（24期）", "3年（36期）", "4年（48期）",
			"5年（60期）", "6年（72期）", "7年（84期）", "8年（96期）", "9年（108期）",
			"10年（120期）", "15年（180期）", "20年（240期）", "25年（300期）", "30年（360期）" };
	@InjectView(R.id.spType)
	Spinner spType;
	@InjectView(R.id.llCustomRate)
	LinearLayout llCustomRate;
	@InjectView(R.id.spRate)
	Spinner spRate;
	@InjectView(R.id.tvRateDetail)
	TextView tvRateDetail;
	@InjectView(R.id.tvSelectYearGJJ)
	TextView tvSelectYearGJJ;
	@InjectView(R.id.tvSelectYearSY)
	TextView tvSelectYearSY;
	@InjectView(R.id.etYearGJJ)
	EditText etYearGJJ;
	@InjectView(R.id.etYearSY)
	EditText etYearSY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.MyTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);
		Utilly.initUMeng(this);
		getRateData();

		tvSelectYearGJJ.setOnClickListener(onClickListener);
		tvSelectYearSY.setOnClickListener(onClickListener);

		spType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					llCustomRate.setVisibility(View.GONE);
					spRate.setVisibility(View.VISIBLE);
					tvRateDetail.setVisibility(View.VISIBLE);
					break;
				case 1:
					llCustomRate.setVisibility(View.VISIBLE);
					spRate.setVisibility(View.GONE);
					tvRateDetail.setVisibility(View.GONE);
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		spRate.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				tvRateDetail.setText(rateList.get(position).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tvSelectYearGJJ:
			case R.id.tvSelectYearSY:
				showSelectYearDialog(v);
				break;

			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
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
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.base_slide_right_in,
					R.anim.stay_anim);
			break;
		case MORE_FEEBACK:
			UMFeedbackService.openUmengFeedbackSDK(this);
			break;
		case MORE_SHARE:
			Utilly.shareSomethingText(this,
					getResources().getString(R.string.app_name), getResources()
							.getString(R.string.share_main_content));
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

	private void showSelectYearDialog(final View v) {
		if (v.getTag() == null) {
			v.setTag(0);
		}
		Dialog alertDialog = new AlertDialog.Builder(this)
				.setTitle("请选择贷款年限")
				.setIcon(R.drawable.ic_launcher)
				.setSingleChoiceItems(yearArrays, (Integer) v.getTag(),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								v.setTag(which);
								dialog.dismiss();
								String temp = yearArrays[which];
								temp = temp.substring(temp.indexOf("（") + 1,
										temp.indexOf("期"));
								if (v.getId() == R.id.tvSelectYearGJJ) {
									etYearGJJ.setText(temp);
								} else if (v.getId() == R.id.tvSelectYearSY) {
									etYearSY.setText(temp);
								}
							}
						}).create();
		alertDialog.show();
	}

	private void getRateData() {
		// 没连接网络
		if (rateList == null) {
			rateList = parse(Utilly.getFromAssets(this, "rate"));
			initRateSelect();
		}
		if (Utilly.IsHaveInternet(this)) {
			// 连接网络
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(rateUrl, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
				}

				@Override
				public void onSuccess(String arg0) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0);

					rateList = parse(arg0);
					initRateSelect();
				}

			});
		}
	}

	private void initRateSelect() {
		Collections.reverse(rateList);
		if (rateList != null && rateList.size() > 0) {
			mRateStrings = new String[rateList.size()];
			for (int i = 0; i < rateList.size(); i++) {
				if (rateList.get(i).name.contains("selected>")) {
					rateList.get(i).name = rateList.get(i).name.replace(
							"selected>", "");
				}
				mRateStrings[i] = rateList.get(i).name;
			}
		}
		ArrayAdapter<String> rateAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mRateStrings);
		rateAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRate.setAdapter(rateAdapter);
		tvRateDetail.setText(rateList.get(spRate.getSelectedItemPosition())
				.toString());
	}

	public List<RateObj> parse(String xmlString) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			// 获取事件源
			XMLReader xmlReader = parser.getXMLReader();
			// 设置处理器
			RateHandler handler = new RateHandler();
			xmlReader.setContentHandler(handler);
			// 解析xml文档
			// xmlReader.parse(new InputSource(new URL(xmlPath).openStream()));
			xmlReader.parse(new InputSource(new StringReader(xmlString)));
			// xmlReader.parse(new InputSource(getAssets().open("rate")));
			return handler.getRates();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}

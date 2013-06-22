package com.rayboot.housecalculator;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
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
	Spinner spType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.MyTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Utilly.initUMeng(this);
		getRateData();
		
		spType = (Spinner) findViewById(R.id.spType);
		
		spType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String str = parent.getItemAtPosition(position).toString();
				Toast.makeText(MainActivity.this, "你点击的是:" + str + "  position " + position + "  id  " + id, 2000)
						.show();
				if (position == 0) {
					
				}else {
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}

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

	private void getRateData() {
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
				}

			});
		} else {
			// 没连接网络
			rateList = parse(Utilly.getFromAssets(this, "rate"));
		}
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

package com.rayboot.letsball.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.R;
import com.rayboot.letsball.adapter.ShooterInfoAdapter;
import com.rayboot.letsball.adapter.TableInfoAdapter;
import com.rayboot.utility.Global;
import com.rayboot.utility.JsoupUtility;
import com.umeng.analytics.MobclickAgent;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class TableAndShooterActivity extends SherlockActivity {

	final int TYPE_TABLE = 1;
	final int TYPE_SHOOTER = 2;
	int curType = TYPE_TABLE;
	ListView lvShow;
	BaseAdapter adaShooter = null;
	BaseAdapter adaTable = null;
	String loadUrl = Global.CUR_TABLE_URL;
	Button btnTypeMode;
	RelativeLayout rlTableTitle;
	RelativeLayout rlShooterTitle;
	List<String> emptyListArray = new ArrayList<String>();
	LinearLayout llContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_and_shooter);

		curType = getIntent().getIntExtra("TYPE", 1);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lvShow = (ListView) findViewById(R.id.lvShow);
		rlTableTitle = (RelativeLayout) findViewById(R.id.rlTableTitle);
		rlShooterTitle = (RelativeLayout) findViewById(R.id.rlShooterTitle);
		llContent = (LinearLayout) findViewById(R.id.llContent);
		setListTitle(curType);
		llContent.setVisibility(View.VISIBLE);
		switch (curType) {
		case TYPE_TABLE:
			new LoadContentTask().execute(curType, Global.CUR_TABLE_URL);
			break;
		case TYPE_SHOOTER:
			new LoadContentTask().execute(curType, Global.CUR_SHOOTER_URL);
			break;
		}
	}

	private void setListTitle(int type) {
		rlTableTitle.setVisibility(type == TYPE_TABLE ? View.VISIBLE
				: View.GONE);
		rlShooterTitle.setVisibility(type == TYPE_SHOOTER ? View.VISIBLE
				: View.GONE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.stay_anim,
					R.anim.base_slide_right_out);
			break;
		case 1:
			emptyListArray.clear();
			lvShow.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1, emptyListArray));
			llContent.setVisibility(View.VISIBLE);
			if (curType == TYPE_SHOOTER) {
				item.setTitle("射手榜");
				item.setIcon(R.drawable.ic_s);
				setTitle("积分榜");
				curType = TYPE_TABLE;
				new LoadContentTask().execute(curType, Global.CUR_TABLE_URL);
			} else if (curType == TYPE_TABLE) {
				item.setTitle("积分榜");
				item.setIcon(R.drawable.ic_t);
				setTitle("射手榜");
				curType = TYPE_SHOOTER;
				new LoadContentTask().execute(curType, Global.CUR_SHOOTER_URL);
			}
			setListTitle(curType);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (curType == TYPE_SHOOTER) {
			menu.add(0, 1, 0, "积分榜")
					.setIcon(R.drawable.ic_t)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_IF_ROOM
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			setTitle("射手榜");
		} else {
			menu.add(0, 1, 0, "射手榜")
					.setIcon(R.drawable.ic_s)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_IF_ROOM
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			setTitle("积分榜");
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {

		private String urlPath;
		private int type;

		@Override
		protected Object doInBackground(Object... arg) {
			type = (Integer) arg[0];
			urlPath = (String) arg[1];
			BaseAdapter adapter = null;
			try {
				switch (type) {
				case TYPE_TABLE:
					if (adaTable == null) {
						adaTable = new TableInfoAdapter(
								TableAndShooterActivity.this,
								JsoupUtility.parseTableUrl(
										TableAndShooterActivity.this, urlPath,
										false));
					}
					adapter = adaTable;
					break;
				case TYPE_SHOOTER:
					if (adaShooter == null) {
						adaShooter = new ShooterInfoAdapter(
								TableAndShooterActivity.this,
								JsoupUtility.parseShooterUrl(
										TableAndShooterActivity.this, urlPath,
										false));
					}
					adapter = adaShooter;
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return adapter;
		}

		protected void onPostExecute(Object result) {
			// process result
			llContent.setVisibility(View.GONE);
			if (result != null) {
				lvShow.setAdapter((BaseAdapter) result);
				((BaseAdapter) result).notifyDataSetChanged();
			} else {
				Crouton.makeText(TableAndShooterActivity.this, "获取数据错误",
						Style.ALERT).show();
			}
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
}

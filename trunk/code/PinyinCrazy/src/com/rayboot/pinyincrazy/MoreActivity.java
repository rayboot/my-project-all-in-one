package com.rayboot.pinyincrazy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rayboot.pinyincrazy.utils.Util;

public class MoreActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
		tvVersion.setText("当前版本：v" + Util.getVersionName(this));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
        case 0:
            this.startActivity(new Intent(this, DisclaimerActivity.class));
            break;
		default:
			break;
		}
		return true;
	}

	public void onShareClick(View view) {
		Util.shareSomethingText(MoreActivity.this, "分享",
				"我使用  #拼音达人#  确实，这些字在你脑海里怎么读呢？");
	}

	public void onFeebackClick(View view) {
		Util.openFeedbackActivity(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Used to put dark icons on light action bar
        menu.add("免责声明")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }
}

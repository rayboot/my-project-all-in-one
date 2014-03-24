package com.rayboot.hanzitingxie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rayboot.hanzitingxie.util.Util;

public class MoreActivity extends MyBaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvVersion.setText("当前版本：v" + Util.getVersionName(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int ids = item.getItemId();
        switch (item.getItemId())
        {
        case android.R.id.home:
            finish();
            break;
        case 0:
            this.startActivity(new Intent(this, DisclaimerActivity.class));
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onShareClick(View view)
    {
        Util.shareSomethingText(MoreActivity.this, "分享",
                "我使用  #汉字听写#  确实，汉字我们还会写多少呢？ 你试试？");
    }

    public void onFeebackClick(View view)
    {
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

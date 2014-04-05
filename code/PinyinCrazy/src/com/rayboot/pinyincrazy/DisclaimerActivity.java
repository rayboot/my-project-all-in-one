package com.rayboot.pinyincrazy;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.fb.FeedbackAgent;

/**
 * @author rayboot
 * @from 14-3-21 15:34
 * @TODO
 */
public class DisclaimerActivity extends MyBaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            FeedbackAgent agent = new FeedbackAgent(this);
            agent.startFeedbackActivity();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Used to put dark icons on light action bar
        menu.add("联系我们")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
                        | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }
}

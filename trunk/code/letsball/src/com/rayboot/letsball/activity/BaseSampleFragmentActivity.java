package com.rayboot.letsball.activity;

import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.rayboot.cus.widget.viewpager.PageIndicator;
import com.rayboot.letsball.adapter.SampleFragmentAdapter;

public class BaseSampleFragmentActivity extends SherlockFragmentActivity {
	SampleFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;

}

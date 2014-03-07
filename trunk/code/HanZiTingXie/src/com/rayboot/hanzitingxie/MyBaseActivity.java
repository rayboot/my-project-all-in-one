package com.rayboot.hanzitingxie;

import cn.waps.UpdatePointsNotifier;

import com.actionbarsherlock.app.SherlockActivity;
import com.rayboot.hanzitingxie.util.DataUtil;
import com.umeng.analytics.MobclickAgent;

public class MyBaseActivity extends SherlockActivity {

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public UpdatePointsNotifier updatePointsNotifier = new UpdatePointsNotifier() {

		@Override
		public void getUpdatePointsFailed(String arg0) {
			// TODO Auto-generated method stub
//			Toast.makeText(MyBaseActivity.this, "获取文字币失败", Toast.LENGTH_SHORT)
//					.show();
		}

		@Override
		public void getUpdatePoints(String arg0, int arg1) {
			// TODO Auto-generated method stub
			DataUtil.g_wenzibi = arg1;
		}
	};

}

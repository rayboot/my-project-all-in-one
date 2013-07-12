package com.rayboot.housecalculator;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;

public class AboutActivity extends SherlockActivity {
	@InjectView(R.id.tvAbout)
	TextView tvAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.MyTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		Views.inject(this);

		String str = "欢迎使用 " + getString(R.string.app_name);
		try {
			str = String.format(this.getString(R.string.about_content),
					getString(R.string.app_name), getPackageManager()
							.getPackageInfo(getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tvAbout.setText(str);
	}

}

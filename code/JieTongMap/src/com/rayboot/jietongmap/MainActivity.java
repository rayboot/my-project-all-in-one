package com.rayboot.jietongmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import butterknife.InjectView;
import butterknife.Views;

public class MainActivity extends Activity {
	@InjectView(R.id.btnInputActivity)
	Button btnInputActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);

		btnInputActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.startActivity(new Intent(MainActivity.this, SearchMapActivity.class));
			}
		});
	}

}

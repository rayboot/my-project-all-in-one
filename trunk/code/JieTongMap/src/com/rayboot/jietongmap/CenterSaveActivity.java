package com.rayboot.jietongmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

import com.rayboot.jietongmap.obj.POIObj;

public class CenterSaveActivity extends Activity {
	@InjectView(R.id.etName)
	EditText etName;
	@InjectView(R.id.etType)
	EditText etType;
	@InjectView(R.id.etAddress)
	EditText etAddress;
	@InjectView(R.id.etTel)
	EditText etTel;
	@InjectView(R.id.tvPo)
	TextView tvPo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_center_save);
		Views.inject(this);

		tvPo.setText(getIntent().getIntExtra("la", 0) + "    "
				+ getIntent().getIntExtra("lo", 0));
	}

	public void saveToDB(View v) {
		new POIObj(etName.getText().toString(), etType.getText().toString(),
				etAddress.getText().toString(), etTel.getText().toString(),
				getIntent().getIntExtra("la", 0), getIntent().getIntExtra("lo",
						0)).save();
		Toast.makeText(this, "已保存", Toast.LENGTH_LONG).show();
	}
}

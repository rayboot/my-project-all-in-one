package com.example.testnetwork;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.testnetwork.NetWorkUtily.NetWorkCarrier;
import com.example.testnetwork.NetWorkUtily.NetWorkType;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (NetWorkUtily.isWifiConnected(this)) {
			NetWorkUtily.setWifiDataEnabled(this, false);
		}

		try {
			NetWorkUtily.setMobileDataEnabled(this, true);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NetWorkType netWorkType = NetWorkUtily.getNetWorkType(this);

		if (netWorkType == NetWorkType.TYPE_WAP
				&& (NetWorkUtily.getNetWorkCarrier(this) == NetWorkCarrier.CARRIER_CM || NetWorkUtily
						.getNetWorkCarrier(this) == NetWorkCarrier.CARRIER_CT)) {
			Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
			// success
		} else if (netWorkType == NetWorkType.TYPE_NET
				&& NetWorkUtily.getNetWorkCarrier(this) == NetWorkCarrier.CARRIER_CU) {
			Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
			// success
		} else {
			try {
				int _id = NetWorkUtily.isAPNExist(this,
						NetWorkUtily.getNetWorkCarrier(this),
						NetWorkType.TYPE_WAP);
				if (_id > 0) {
					NetWorkUtily.setDefaultApn(this, _id);
					Toast.makeText(this, "select apn", Toast.LENGTH_LONG)
							.show();
				} else {
					NetWorkUtily.setDefaultApn(this, NetWorkUtily.insertAPN(
							this, NetWorkUtily.getNetWorkCarrier(this),
							NetWorkType.TYPE_WAP));
					Toast.makeText(this, "add apn", Toast.LENGTH_LONG).show();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

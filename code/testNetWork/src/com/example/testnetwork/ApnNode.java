package com.example.testnetwork;

import android.text.TextUtils;

import com.example.testnetwork.NetWorkUtily.NetWorkCarrier;
import com.example.testnetwork.NetWorkUtily.NetWorkType;

public class ApnNode {

	public String name;
	public String numeric;
	public String apn;
	public String user;
	public String server;
	public String password;
	public String proxy;
	public String port;
	public String _id;
	public String type;
	public String current;
	public String ppppwd;

	public ApnNode(NetWorkCarrier carrier, NetWorkType netWorkType, String imsi) {
		super();
		if (netWorkType == NetWorkType.TYPE_NET) {
			setAsNet(carrier, imsi);
		} else if (netWorkType == NetWorkType.TYPE_WAP) {
			setAsWap(carrier, imsi);
		}
	}

	public ApnNode() {
		// TODO Auto-generated constructor stub
	}

	private void setAsNet(NetWorkCarrier carrier, String imsi) {
		if (NetWorkCarrier.CARRIER_CM == carrier) {
			this.name = "cmnet";
			if (TextUtils.isEmpty(imsi)) {
				this.numeric = "46000";
			} else {
				this.numeric = imsi.substring(0, 5);
			}
			this.apn = "cmnet";
			this.user = null;
			this.server = null;
			this.password = null;
			this.proxy = null;
			this.port = null;
			this.type = "default,supl";
			this.current = "1";
		} else if (NetWorkCarrier.CARRIER_CT == carrier) {
			this.name = "ctnet";
			this.numeric = "46003";
			this.apn = "ctnet";
			this.user = "ctnet@mycdma.cn";
			this.server = null;
			this.password = "vnet.mobi";
			this.proxy = null;
			this.port = null;
			this.type = "default";
			this.current = "1";
			this.ppppwd = "#777";
		} else if (NetWorkCarrier.CARRIER_CU == carrier) {
			this.name = "cunet";
			this.numeric = "46001";
			this.apn = "3gnet";
			this.user = null;
			this.server = null;
			this.password = null;
			this.proxy = null;
			this.port = null;
			this.type = "default,supl";
			this.current = "1";
		}
	}

	private void setAsWap(NetWorkCarrier carrier, String imsi) {
		if (NetWorkCarrier.CARRIER_CM == carrier) {
			this.name = "cmwap";
			if (TextUtils.isEmpty(imsi)) {
				this.numeric = "46000";
			} else {
				this.numeric = imsi.substring(0, 5);
			}
			this.apn = "cmwap";
			this.user = null;
			this.server = "http://wap.monternet.com";
			this.password = null;
			this.proxy = "10.0.0.172";
			this.port = "80";
			this.type = "default";
			this.current = "1";
		} else if (NetWorkCarrier.CARRIER_CT == carrier) {
			this.name = "ctwap";
			this.numeric = "46003";
			this.apn = "ctwap";
			this.user = "ctwap@mycdma.cn";
			this.server = null;
			this.password = "vnet.mobi";
			this.proxy = "10.0.0.200";
			this.port = "80";
			this.type = "default,supl,hipri";
			this.current = "1";
			this.ppppwd = "#777";
		} else if (NetWorkCarrier.CARRIER_CU == carrier) {
			this.name = "cuwap";
			this.numeric = "46001";
			this.apn = "3gwap";
			this.user = null;
			this.server = "http://www.wo.com.cn";
			this.password = null;
			this.proxy = "10.0.0.172";
			this.port = "80";
			this.type = "default";
			this.current = "1";
		}
	}
}

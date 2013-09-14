package com.rayboot.ahjiaotong;

import java.util.List;

public class ResultObj {
	public String result;
	public String msg;
	public String license;
	public String name;
	public String home;
	public List<ViolateObj> violate;

	public class ViolateObj {
		public String time;
		public String address;
		public String action;
	}

}

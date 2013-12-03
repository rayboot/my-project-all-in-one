package com.rayboot.pinyincrazy.obj;

public class MainDataObj {
	public static final int MAIN_GAME = 0;
	public static final int MAIN_RANK= 1;
	public static final int MAIN_SETTING = 2;
	public static final int MAIN_MORE = 3;

	public String name;
	public int key;
	
	public MainDataObj( int key,String name) {
		super();
		this.name = name;
		this.key = key;
	}
}

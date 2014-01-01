package com.rayboot.pinyincrazy.obj;

import com.rayboot.pinyincrazy.R;

public class MainDataObj {
	public static final int MAIN_GAME = 0;
	public static final int MAIN_RANK= 1;
	public static final int MAIN_SETTING = 2;
	public static final int MAIN_MORE = 3;
	public static final int MAIN_WUJIN = 4;

	public String name;
	public int key;
	public int imageId;
	
	public MainDataObj( int key,String name) {
		super();
		this.name = name;
		this.key = key;
		switch (key) {
		case MAIN_GAME:
			imageId = R.drawable.ic_game;
			break;
		case MAIN_WUJIN:
			imageId = R.drawable.ic_game;
			break;
		case MAIN_RANK:
			imageId = R.drawable.ic_rank;
			break;
		case MAIN_SETTING:
			imageId = R.drawable.ic_setting;
			break;
		case MAIN_MORE:
			imageId = R.drawable.ic_more;
			break;

		default:
			break;
		}
	}
}

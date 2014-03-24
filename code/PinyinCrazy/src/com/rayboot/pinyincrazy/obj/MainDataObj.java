package com.rayboot.pinyincrazy.obj;

import com.rayboot.pinyincrazy.R;

public class MainDataObj {
	public static final int MAIN_GAME = 0;
	public static final int MAIN_RANK= 1;
	public static final int MAIN_MORE = 2;

	public int key;
    public String name;
    public int imageId;
    public int miniImageId;
    public String pinyin;
    public String subName;
    public int mainBg;
    public int rightBg;
    public int textColor;
    public int pinyinColor;
    public int subNameColor;
	
	public MainDataObj( int key) {
		super();
		this.key = key;
		switch (key) {
		case MAIN_GAME:
            name = "开始闯关";
            imageId = R.drawable.ic_game;
            miniImageId = R.drawable.ic_mini_game;
            pinyin = "chuang guan";
            subName = "Ready Go";
            mainBg = R.color.main_bg_1;
            rightBg = R.color.main_bg_2;
            textColor = android.R.color.white;
            pinyinColor = R.color.main_pinyin_color1;
            subNameColor = R.color.main_subname_color1;
			break;
		case MAIN_RANK:
            name = "历史数据";
			imageId = R.drawable.ic_rank;
            miniImageId = R.drawable.ic_mini_rank;
            pinyin = "shu ju";
            subName = "History";
            mainBg = android.R.color.white;
            rightBg = R.color.main_bg_3;
            textColor = android.R.color.black;
            pinyinColor = R.color.main_pinyin_color2;
            subNameColor = R.color.main_subname_color2;
			break;
		case MAIN_MORE:
            name = "更多";
			imageId = R.drawable.ic_more;
            miniImageId = R.drawable.ic_mini_more;
            pinyin = "geng duo";
            subName = "More";
            mainBg = android.R.color.white;
            rightBg = R.color.main_bg_4;
            textColor = android.R.color.black;
            pinyinColor = R.color.main_pinyin_color2;
            subNameColor = R.color.main_subname_color3;
			break;

		default:
			break;
		}
	}
}

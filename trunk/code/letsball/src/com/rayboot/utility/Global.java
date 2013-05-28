package com.rayboot.utility;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.rayboot.letsball.R;
import com.rayboot.letsball.model.TeamMapInfo;

public class Global {
	public static String Domain = "http://soccer.sports.sohu.com/";
	public static String CUR_TABLE_URL = "http://soccer.sports.sohu.com/league/1/23/table/";
	public static String CUR_SHOOTER_URL = "http://soccer.sports.sohu.com/league/1/23/shooter/";

	public static String[] getTeamParaArray(Context context, int res1,
			String res2) {
		String params[] = { context.getResources().getString(res1),
				"assets://" + res2 };
		return params;
	}

	public static void initTeamPara(Context context) {
		Map<String, String[]> mapTeams = new HashMap<String, String[]>();
		mapTeams.put(
				"阿森纳",
				getTeamParaArray(context, R.string.eng_team_1,
						"eng_team_logo_1.png"));
		mapTeams.put(
				"桑德兰",
				getTeamParaArray(context, R.string.eng_team_2,
						"eng_team_logo_2.png"));
		mapTeams.put(
				"西汉姆联",
				getTeamParaArray(context, R.string.eng_team_3,
						"eng_team_logo_3.png"));
		mapTeams.put(
				"维拉",
				getTeamParaArray(context, R.string.eng_team_4,
						"eng_team_logo_4.png"));
		mapTeams.put(
				"阿斯顿维拉",
				getTeamParaArray(context, R.string.eng_team_4,
						"eng_team_logo_4.png"));
		mapTeams.put(
				"西布朗",
				getTeamParaArray(context, R.string.eng_team_5,
						"eng_team_logo_5.png"));
		mapTeams.put(
				"西布罗姆维奇",
				getTeamParaArray(context, R.string.eng_team_5,
						"eng_team_logo_5.png"));
		mapTeams.put(
				"利物浦",
				getTeamParaArray(context, R.string.eng_team_6,
						"eng_team_logo_6.png"));
		mapTeams.put(
				"雷丁",
				getTeamParaArray(context, R.string.eng_team_7,
						"eng_team_logo_7.png"));
		mapTeams.put(
				"斯托克城",
				getTeamParaArray(context, R.string.eng_team_8,
						"eng_team_logo_8.png"));
		mapTeams.put(
				"女王公园",
				getTeamParaArray(context, R.string.eng_team_9,
						"eng_team_logo_9.png"));
		mapTeams.put(
				"女王公园巡游者",
				getTeamParaArray(context, R.string.eng_team_9,
						"eng_team_logo_9.png"));
		mapTeams.put(
				"斯旺西",
				getTeamParaArray(context, R.string.eng_team_10,
						"eng_team_logo_10.png"));
		mapTeams.put(
				"斯旺西城",
				getTeamParaArray(context, R.string.eng_team_10,
						"eng_team_logo_10.png"));
		mapTeams.put(
				"富勒姆",
				getTeamParaArray(context, R.string.eng_team_11,
						"eng_team_logo_11.png"));
		mapTeams.put(
				"诺维奇",
				getTeamParaArray(context, R.string.eng_team_12,
						"eng_team_logo_12.png"));
		mapTeams.put(
				"诺维奇城",
				getTeamParaArray(context, R.string.eng_team_12,
						"eng_team_logo_12.png"));
		mapTeams.put(
				"纽卡斯尔联",
				getTeamParaArray(context, R.string.eng_team_13,
						"eng_team_logo_13.png"));
		mapTeams.put(
				"热刺",
				getTeamParaArray(context, R.string.eng_team_14,
						"eng_team_logo_14.png"));
		mapTeams.put(
				"托特纳姆热刺",
				getTeamParaArray(context, R.string.eng_team_14,
						"eng_team_logo_14.png"));
		mapTeams.put(
				"维冈竞技",
				getTeamParaArray(context, R.string.eng_team_15,
						"eng_team_logo_15.png"));
		mapTeams.put(
				"切尔西",
				getTeamParaArray(context, R.string.eng_team_16,
						"eng_team_logo_16.png"));
		mapTeams.put(
				"曼城",
				getTeamParaArray(context, R.string.eng_team_17,
						"eng_team_logo_17.png"));
		mapTeams.put(
				"曼彻斯特城",
				getTeamParaArray(context, R.string.eng_team_17,
						"eng_team_logo_17.png"));
		mapTeams.put(
				"南安普顿",
				getTeamParaArray(context, R.string.eng_team_18,
						"eng_team_logo_18.png"));
		mapTeams.put(
				"埃弗顿",
				getTeamParaArray(context, R.string.eng_team_19,
						"eng_team_logo_19.png"));
		mapTeams.put(
				"曼联",
				getTeamParaArray(context, R.string.eng_team_20,
						"eng_team_logo_20.png"));
		mapTeams.put(
				"曼彻斯特联",
				getTeamParaArray(context, R.string.eng_team_20,
						"eng_team_logo_20.png"));
		mapTeams.put(
				"朴茨茅斯",
				getTeamParaArray(context, R.string.eng_team_21,
						"eng_team_logo_21.png"));
		mapTeams.put(
				"布莱顿",
				getTeamParaArray(context, R.string.eng_team_22,
						"eng_team_logo_22.png"));
		mapTeams.put(
				"水晶宫",
				getTeamParaArray(context, R.string.eng_team_23,
						"eng_team_logo_23.png"));
		mapTeams.put(
				"沃特福德",
				getTeamParaArray(context, R.string.eng_team_24,
						"eng_team_logo_24.png"));
		mapTeams.put(
				"巴恩斯利",
				getTeamParaArray(context, R.string.eng_team_25,
						"eng_team_logo_25.png"));
		mapTeams.put(
				"米尔沃尔",
				getTeamParaArray(context, R.string.eng_team_26,
						"eng_team_logo_26.png"));
		mapTeams.put(
				"德比郡",
				getTeamParaArray(context, R.string.eng_team_27,
						"eng_team_logo_27.png"));
		mapTeams.put(
				"伯明翰",
				getTeamParaArray(context, R.string.eng_team_28,
						"eng_team_logo_28.png"));
		mapTeams.put(
				"狼队",
				getTeamParaArray(context, R.string.eng_team_29,
						"eng_team_logo_29.png"));
		mapTeams.put(
				"赫尔城",
				getTeamParaArray(context, R.string.eng_team_30,
						"eng_team_logo_30.png"));
		mapTeams.put(
				"米德尔斯堡",
				getTeamParaArray(context, R.string.eng_team_31,
						"eng_team_logo_31.png"));
		mapTeams.put(
				"伊普斯维奇",
				getTeamParaArray(context, R.string.eng_team_32,
						"eng_team_logo_32.png"));
		mapTeams.put(
				"诺丁汉森林",
				getTeamParaArray(context, R.string.eng_team_33,
						"eng_team_logo_33.png"));
		mapTeams.put(
				"彼得堡联",
				getTeamParaArray(context, R.string.eng_team_34,
						"eng_team_logo_34.png"));
		mapTeams.put(
				"布莱克本",
				getTeamParaArray(context, R.string.eng_team_35,
						"eng_team_logo_35.png"));
		mapTeams.put(
				"布里斯托城",
				getTeamParaArray(context, R.string.eng_team_36,
						"eng_team_logo_36.png"));
		mapTeams.put(
				"布莱克浦",
				getTeamParaArray(context, R.string.eng_team_37,
						"eng_team_logo_37.png"));
		mapTeams.put(
				"博尔顿",
				getTeamParaArray(context, R.string.eng_team_38,
						"eng_team_logo_38.png"));
		mapTeams.put(
				"莱切斯特城",
				getTeamParaArray(context, R.string.eng_team_39,
						"eng_team_logo_39.png"));

		// 意甲
		mapTeams.put(
				"佛罗伦萨",
				getTeamParaArray(context, R.string.ita_team_1,
						"ita_team_logo_1.png"));
		mapTeams.put(
				"乌迪内斯",
				getTeamParaArray(context, R.string.ita_team_2,
						"ita_team_logo_2.png"));
		mapTeams.put(
				"尤文图斯",
				getTeamParaArray(context, R.string.ita_team_3,
						"ita_team_logo_3.png"));
		mapTeams.put(
				"帕尔马",
				getTeamParaArray(context, R.string.ita_team_4,
						"ita_team_logo_4.png"));
		mapTeams.put(
				"AC米兰",
				getTeamParaArray(context, R.string.ita_team_5,
						"ita_team_logo_5.png"));
		mapTeams.put(
				"桑普多利亚",
				getTeamParaArray(context, R.string.ita_team_6,
						"ita_team_logo_6.png"));
		mapTeams.put(
				"锡耶纳",
				getTeamParaArray(context, R.string.ita_team_7,
						"ita_team_logo_7.png"));
		mapTeams.put(
				"都灵",
				getTeamParaArray(context, R.string.ita_team_8,
						"ita_team_logo_8.png"));
		mapTeams.put(
				"罗马",
				getTeamParaArray(context, R.string.ita_team_9,
						"ita_team_logo_9.png"));
		mapTeams.put(
				"卡塔尼亚",
				getTeamParaArray(context, R.string.ita_team_10,
						"ita_team_logo_10.png"));
		mapTeams.put(
				"巴勒莫",
				getTeamParaArray(context, R.string.ita_team_11,
						"ita_team_logo_11.png"));
		mapTeams.put(
				"那不勒斯",
				getTeamParaArray(context, R.string.ita_team_12,
						"ita_team_logo_12.png"));
		mapTeams.put(
				"佩斯卡拉",
				getTeamParaArray(context, R.string.ita_team_13,
						"ita_team_logo_13.png"));
		mapTeams.put(
				"国际米兰",
				getTeamParaArray(context, R.string.ita_team_14,
						"ita_team_logo_14.png"));
		mapTeams.put(
				"亚特兰大",
				getTeamParaArray(context, R.string.ita_team_15,
						"ita_team_logo_15.png"));
		mapTeams.put(
				"拉齐奥",
				getTeamParaArray(context, R.string.ita_team_16,
						"ita_team_logo_16.png"));
		mapTeams.put(
				"热那亚",
				getTeamParaArray(context, R.string.ita_team_17,
						"ita_team_logo_17.png"));
		mapTeams.put(
				"卡利亚里",
				getTeamParaArray(context, R.string.ita_team_18,
						"ita_team_logo_18.png"));
		mapTeams.put(
				"切沃",
				getTeamParaArray(context, R.string.ita_team_19,
						"ita_team_logo_19.png"));
		mapTeams.put(
				"博洛尼亚",
				getTeamParaArray(context, R.string.ita_team_20,
						"ita_team_logo_20.png"));
		mapTeams.put(
				"诺斯里纳",
				getTeamParaArray(context, R.string.ita_team_21,
						"ita_team_logo_21.png"));
		mapTeams.put(
				"阿尔比诺勒菲",
				getTeamParaArray(context, R.string.ita_team_22,
						"ita_team_logo_22.png"));
		mapTeams.put(
				"维琴察",
				getTeamParaArray(context, R.string.ita_team_23,
						"ita_team_logo_23.png"));
		mapTeams.put(
				"圣马利诺",
				getTeamParaArray(context, R.string.ita_team_24,
						"ita_team_logo_24.png"));
		mapTeams.put(
				"古比奥",
				getTeamParaArray(context, R.string.ita_team_25,
						"ita_team_logo_25.png"));
		mapTeams.put(
				"摩德纳",
				getTeamParaArray(context, R.string.ita_team_26,
						"ita_team_logo_26.png"));
		mapTeams.put(
				"切塞纳",
				getTeamParaArray(context, R.string.ita_team_27,
						"ita_team_logo_27.png"));
		mapTeams.put(
				"希塔德拉",
				getTeamParaArray(context, R.string.ita_team_28,
						"ita_team_logo_28.png"));
		mapTeams.put(
				"莎索罗",
				getTeamParaArray(context, R.string.ita_team_29,
						"ita_team_logo_29.png"));
		mapTeams.put(
				"利沃诺",
				getTeamParaArray(context, R.string.ita_team_30,
						"ita_team_logo_30.png"));
		mapTeams.put(
				"诺瓦拉",
				getTeamParaArray(context, R.string.ita_team_31,
						"ita_team_logo_31.png"));
		mapTeams.put(
				"帕多瓦",
				getTeamParaArray(context, R.string.ita_team_32,
						"ita_team_logo_32.png"));
		mapTeams.put(
				"阿斯科利",
				getTeamParaArray(context, R.string.ita_team_33,
						"ita_team_logo_33.png"));
		mapTeams.put(
				"巴里",
				getTeamParaArray(context, R.string.ita_team_34,
						"ita_team_logo_34.png"));
		mapTeams.put(
				"布雷西亚",
				getTeamParaArray(context, R.string.ita_team_35,
						"ita_team_logo_35.png"));
		mapTeams.put(
				"维罗纳",
				getTeamParaArray(context, R.string.ita_team_36,
						"ita_team_logo_36.png"));
		mapTeams.put(
				"雷吉纳",
				getTeamParaArray(context, R.string.ita_team_37,
						"ita_team_logo_37.png"));
		mapTeams.put(
				"克罗托内",
				getTeamParaArray(context, R.string.ita_team_38,
						"ita_team_logo_38.png"));
		mapTeams.put(
				"瓦雷斯",
				getTeamParaArray(context, R.string.ita_team_39,
						"ita_team_logo_39.png"));

		// 西甲
		mapTeams.put(
				"塞尔塔",
				getTeamParaArray(context, R.string.span_team_1,
						"span_team_logo_1.png"));
		mapTeams.put(
				"维戈塞尔塔",
				getTeamParaArray(context, R.string.span_team_1,
						"span_team_logo_1.png"));
		mapTeams.put(
				"马拉加",
				getTeamParaArray(context, R.string.span_team_2,
						"span_team_logo_2.png"));
		mapTeams.put(
				"塞维利亚",
				getTeamParaArray(context, R.string.span_team_3,
						"span_team_logo_3.png"));
		mapTeams.put(
				"赫塔菲",
				getTeamParaArray(context, R.string.span_team_4,
						"span_team_logo_4.png"));
		mapTeams.put(
				"马洛卡",
				getTeamParaArray(context, R.string.span_team_5,
						"span_team_logo_5.png"));
		mapTeams.put(
				"皇家马洛卡",
				getTeamParaArray(context, R.string.span_team_5,
						"span_team_logo_5.png"));
		mapTeams.put(
				"西班牙人",
				getTeamParaArray(context, R.string.span_team_6,
						"span_team_logo_6.png"));
		mapTeams.put(
				"毕尔巴鄂",
				getTeamParaArray(context, R.string.span_team_7,
						"span_team_logo_7.png"));
		mapTeams.put(
				"毕尔巴鄂竞技",
				getTeamParaArray(context, R.string.span_team_7,
						"span_team_logo_7.png"));
		mapTeams.put(
				"皇家贝蒂斯",
				getTeamParaArray(context, R.string.span_team_8,
						"span_team_logo_8.png"));
		mapTeams.put(
				"皇家马德里",
				getTeamParaArray(context, R.string.span_team_9,
						"span_team_logo_9.png"));
		mapTeams.put(
				"巴伦西亚",
				getTeamParaArray(context, R.string.span_team_10,
						"span_team_logo_10.png"));
		mapTeams.put(
				"巴塞罗那",
				getTeamParaArray(context, R.string.span_team_11,
						"span_team_logo_11.png"));
		mapTeams.put(
				"皇家社会",
				getTeamParaArray(context, R.string.span_team_12,
						"span_team_logo_12.png"));
		mapTeams.put(
				"莱万特",
				getTeamParaArray(context, R.string.span_team_13,
						"span_team_logo_13.png"));
		mapTeams.put(
				"马德里竞技",
				getTeamParaArray(context, R.string.span_team_14,
						"span_team_logo_14.png"));
		mapTeams.put(
				"拉科鲁尼亚",
				getTeamParaArray(context, R.string.span_team_15,
						"span_team_logo_15.png"));
		mapTeams.put(
				"奥萨苏纳",
				getTeamParaArray(context, R.string.span_team_16,
						"span_team_logo_16.png"));
		mapTeams.put(
				"巴列卡诺",
				getTeamParaArray(context, R.string.span_team_17,
						"span_team_logo_17.png"));
		mapTeams.put(
				"格兰纳达",
				getTeamParaArray(context, R.string.span_team_18,
						"span_team_logo_18.png"));
		mapTeams.put(
				"萨拉戈萨",
				getTeamParaArray(context, R.string.span_team_19,
						"span_team_logo_19.png"));
		mapTeams.put(
				"皇家萨拉戈萨",
				getTeamParaArray(context, R.string.span_team_19,
						"span_team_logo_19.png"));
		mapTeams.put(
				"瓦拉多利德",
				getTeamParaArray(context, R.string.span_team_20,
						"span_team_logo_20.png"));
		mapTeams.put(
				"塔拉戈纳",
				getTeamParaArray(context, R.string.span_team_21,
						"span_team_logo_21.png"));
		mapTeams.put(
				"艾高亚诺",
				getTeamParaArray(context, R.string.span_team_22,
						"span_team_logo_22.png"));
		mapTeams.put(
				"卡塔根那",
				getTeamParaArray(context, R.string.span_team_23,
						"span_team_logo_23.png"));
		mapTeams.put(
				"沙巴度尔",
				getTeamParaArray(context, R.string.span_team_24,
						"span_team_logo_24.png"));
		mapTeams.put(
				"大力神",
				getTeamParaArray(context, R.string.span_team_25,
						"span_team_logo_25.png"));
		mapTeams.put(
				"阿尔梅里亚",
				getTeamParaArray(context, R.string.span_team_26,
						"span_team_logo_26.png"));
		mapTeams.put(
				"穆尔西亚",
				getTeamParaArray(context, R.string.span_team_27,
						"span_team_logo_27.png"));
		mapTeams.put(
				"韦尔瓦",
				getTeamParaArray(context, R.string.span_team_28,
						"span_team_logo_28.png"));
		mapTeams.put(
				"艾科坎",
				getTeamParaArray(context, R.string.span_team_29,
						"span_team_logo_29.png"));
		mapTeams.put(
				"努曼西亚",
				getTeamParaArray(context, R.string.span_team_30,
						"span_team_logo_30.png"));
		mapTeams.put(
				"科尔多瓦",
				getTeamParaArray(context, R.string.span_team_31,
						"span_team_logo_31.png"));
		mapTeams.put(
				"艾尔切",
				getTeamParaArray(context, R.string.span_team_32,
						"span_team_logo_32.png"));
		mapTeams.put(
				"希洪竞技",
				getTeamParaArray(context, R.string.span_team_33,
						"span_team_logo_33.png"));
		mapTeams.put(
				"赫罗纳",
				getTeamParaArray(context, R.string.span_team_34,
						"span_team_logo_34.png"));
		mapTeams.put(
				"韦斯卡",
				getTeamParaArray(context, R.string.span_team_35,
						"span_team_logo_35.png"));
		mapTeams.put(
				"瓜达拉哈拉",
				getTeamParaArray(context, R.string.span_team_36,
						"span_team_logo_36.png"));
		mapTeams.put(
				"桑坦德",
				getTeamParaArray(context, R.string.span_team_37,
						"span_team_logo_37.png"));
		mapTeams.put(
				"萨雷斯",
				getTeamParaArray(context, R.string.span_team_38,
						"span_team_logo_38.png"));
		mapTeams.put(
				"拉斯帕尔马斯",
				getTeamParaArray(context, R.string.span_team_39,
						"span_team_logo_39.png"));
		mapTeams.put(
				"维拉利尔",
				getTeamParaArray(context, R.string.span_team_40,
						"span_team_logo_40.png"));

		// 德甲
		mapTeams.put(
				"多特蒙德",
				getTeamParaArray(context, R.string.de_team_1,
						"de_team_logo_1.png"));
		mapTeams.put(
				"不莱梅",
				getTeamParaArray(context, R.string.de_team_2,
						"de_team_logo_2.png"));
		mapTeams.put(
				"云达不莱梅",
				getTeamParaArray(context, R.string.de_team_2,
						"de_team_logo_2.png"));
		mapTeams.put(
				"菲尔特",
				getTeamParaArray(context, R.string.de_team_3,
						"de_team_logo_3.png"));
		mapTeams.put(
				"拜仁",
				getTeamParaArray(context, R.string.de_team_4,
						"de_team_logo_4.png"));
		mapTeams.put(
				"拜仁慕尼黑",
				getTeamParaArray(context, R.string.de_team_4,
						"de_team_logo_4.png"));
		mapTeams.put(
				"汉堡",
				getTeamParaArray(context, R.string.de_team_5,
						"de_team_logo_5.png"));
		mapTeams.put(
				"纽伦堡",
				getTeamParaArray(context, R.string.de_team_6,
						"de_team_logo_6.png"));
		mapTeams.put(
				"奥格斯堡",
				getTeamParaArray(context, R.string.de_team_7,
						"de_team_logo_7.png"));
		mapTeams.put(
				"杜塞尔多夫",
				getTeamParaArray(context, R.string.de_team_8,
						"de_team_logo_8.png"));
		mapTeams.put(
				"弗赖堡",
				getTeamParaArray(context, R.string.de_team_9,
						"de_team_logo_9.png"));
		mapTeams.put(
				"美因茨05",
				getTeamParaArray(context, R.string.de_team_10,
						"de_team_logo_10.png"));
		mapTeams.put(
				"门兴",
				getTeamParaArray(context, R.string.de_team_11,
						"de_team_logo_11.png"));
		mapTeams.put(
				"门兴格拉德巴赫",
				getTeamParaArray(context, R.string.de_team_11,
						"de_team_logo_11.png"));
		mapTeams.put(
				"霍芬海姆",
				getTeamParaArray(context, R.string.de_team_12,
						"de_team_logo_12.png"));
		mapTeams.put(
				"法兰克福",
				getTeamParaArray(context, R.string.de_team_13,
						"de_team_logo_13.png"));
		mapTeams.put(
				"勒沃库森",
				getTeamParaArray(context, R.string.de_team_14,
						"de_team_logo_14.png"));
		mapTeams.put(
				"斯图加特",
				getTeamParaArray(context, R.string.de_team_15,
						"de_team_logo_15.png"));
		mapTeams.put(
				"沃尔夫斯堡",
				getTeamParaArray(context, R.string.de_team_16,
						"de_team_logo_16.png"));
		mapTeams.put(
				"汉诺威96",
				getTeamParaArray(context, R.string.de_team_17,
						"de_team_logo_17.png"));
		mapTeams.put(
				"沙尔克04",
				getTeamParaArray(context, R.string.de_team_18,
						"de_team_logo_18.png"));

		mapTeams.put(
				"FSV法兰克福",
				getTeamParaArray(context, R.string.de_team_19,
						"de_team_logo_19.png"));
		mapTeams.put(
				"不伦瑞克",
				getTeamParaArray(context, R.string.de_team_20,
						"de_team_logo_20.png"));
		mapTeams.put(
				"杜伊斯堡",
				getTeamParaArray(context, R.string.de_team_21,
						"de_team_logo_21.png"));
		mapTeams.put(
				"科隆",
				getTeamParaArray(context, R.string.de_team_22,
						"de_team_logo_22.png"));
		mapTeams.put(
				"圣保利",
				getTeamParaArray(context, R.string.de_team_23,
						"de_team_logo_23.png"));
		mapTeams.put(
				"波鸿",
				getTeamParaArray(context, R.string.de_team_24,
						"de_team_logo_24.png"));
		mapTeams.put(
				"亚琛",
				getTeamParaArray(context, R.string.de_team_25,
						"de_team_logo_25.png"));
		mapTeams.put(
				"慕尼黑1860",
				getTeamParaArray(context, R.string.de_team_26,
						"de_team_logo_26.png"));
		mapTeams.put(
				"柏林赫塔",
				getTeamParaArray(context, R.string.de_team_27,
						"de_team_logo_27.png"));
		mapTeams.put(
				"帕德博恩",
				getTeamParaArray(context, R.string.de_team_28,
						"de_team_logo_28.png"));
		mapTeams.put(
				"凯泽斯劳滕",
				getTeamParaArray(context, R.string.de_team_29,
						"de_team_logo_29.png"));
		mapTeams.put(
				"因戈尔施塔特",
				getTeamParaArray(context, R.string.de_team_30,
						"de_team_logo_30.png"));
		mapTeams.put(
				"奥厄",
				getTeamParaArray(context, R.string.de_team_31,
						"de_team_logo_31.png"));
		mapTeams.put(
				"罗斯托克",
				getTeamParaArray(context, R.string.de_team_32,
						"de_team_logo_32.png"));
		mapTeams.put(
				"科特布斯",
				getTeamParaArray(context, R.string.de_team_33,
						"de_team_logo_33.png"));
		mapTeams.put(
				"柏林联盟",
				getTeamParaArray(context, R.string.de_team_34,
						"de_team_logo_34.png"));
		mapTeams.put(
				"德累斯顿",
				getTeamParaArray(context, R.string.de_team_35,
						"de_team_logo_35.png"));

		ActiveAndroid.beginTransaction();
		try {
			for (Map.Entry<String, String[]> m : mapTeams.entrySet()) {
				TeamMapInfo teamMapInfo = new TeamMapInfo(m.getKey(),
						m.getValue()[0], m.getValue()[1]);
				teamMapInfo.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
		mapTeams.clear();
		mapTeams = null;
		System.gc();
	}
}

package com.rayboot.letsball.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rayboot.letsball.R;
import com.rayboot.letsball.activity.MatchDetailActivity;
import com.rayboot.letsball.adapter.MatchInfoAdapter;
import com.rayboot.letsball.model.MatchInfo;
import com.rayboot.letsball.model.RoundInfo;
import com.rayboot.utility.Global;
import com.rayboot.utility.JsoupUtility;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RoundFragment extends Fragment {

	private static final String KEY_CONTENT = "TestFragment:Content";
	private View contentView = null;
	View mContent;
	ListView lvMatch;
	List<MatchInfo> matchList = new ArrayList<MatchInfo>();
	MatchInfoAdapter miAdapter;
	RelativeLayout rlListTitle;
	String leagueName = null;
	RoundInfo curRoundInfo = null;

	public static RoundFragment newInstance(String content, String leagueName) {
		RoundFragment fragment = new RoundFragment();
		fragment.curRoundInfo = RoundInfo.getRoundInfoFDBWkey(content);
		fragment.leagueName = leagueName;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			curRoundInfo = (RoundInfo) savedInstanceState
					.getSerializable(KEY_CONTENT);
		}

		// Inflate the layout for this fragment
		contentView = inflater.inflate(R.layout.league_view, container, false);

		mContent = (View) contentView.findViewById(R.id.content);
		lvMatch = (ListView) contentView.findViewById(R.id.lvMatch);
		rlListTitle = (RelativeLayout) contentView
				.findViewById(R.id.rlListTitle);
		rlListTitle.setVisibility(View.GONE);

		lvMatch.setOnItemClickListener(onItemClickListener);

		Global.CUR_SHOOTER_URL = curRoundInfo.shooterUrl;
		Global.CUR_TABLE_URL = curRoundInfo.tableUrl;

		loadMatchListContent(true);

		return contentView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (matchList.get(arg2).status == MatchInfo.MATCH_END) {
				Intent intent = new Intent(getActivity(),
						MatchDetailActivity.class);
				intent.putExtra("matchinfo", matchList.get(arg2));
				intent.putExtra("leagueName", leagueName);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.base_slide_right_in, R.anim.stay_anim);
			} else {
				Crouton.makeText(getActivity(), "比赛还没开始呦。。。", Style.INFO)
						.show();
			}
		}
	};

	public void loadMatchListContent(boolean isUseCache) {
		matchList = MatchInfo.getAllMatchInfos(curRoundInfo.getId());

		if (matchList != null) {
			if (curRoundInfo.isFinished() || !isUseCache
					|| (!curRoundInfo.isFinished() && !isAnyStart(matchList))) {
				mContent.setVisibility(View.GONE);
				miAdapter = new MatchInfoAdapter(getActivity(), matchList);
				lvMatch.setAdapter(miAdapter);
				miAdapter.notifyDataSetChanged();
			} else {
				new LoadContentTask().execute(curRoundInfo, false);
				mContent.setVisibility(View.VISIBLE);
			}
		} else {
			if (isUseCache) {
				new LoadContentTask().execute(curRoundInfo, true);
				mContent.setVisibility(View.VISIBLE);
			} else {
				Crouton.makeText(getActivity(), "数据解析错误，请检测网络！", Style.ALERT)
						.show();
				mContent.setVisibility(View.GONE);
			}
		}
	}

	// 找到有一场比赛开始的
	private boolean isAnyStart(List<MatchInfo> datas) {
		for (MatchInfo matchInfo : datas) {
			long now = System.currentTimeMillis();
			if (now > matchInfo.time) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(KEY_CONTENT, curRoundInfo);
	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {

		private RoundInfo roundInfo;
		private boolean isUseCache;

		@Override
		protected Object doInBackground(Object... arg) {
			roundInfo = (RoundInfo) arg[0];
			isUseCache = (Boolean) arg[1];
			boolean result = false;

			try {
				JsoupUtility
						.parseRoundUrl(getActivity(), roundInfo, isUseCache);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		protected void onPostExecute(Object result) {
			// process result
			loadMatchListContent(isUseCache);
			contentView.postInvalidate();
		}

	}

}

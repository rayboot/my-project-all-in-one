package com.rayboot.letsball.fragment;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rayboot.letsball.R;
import com.rayboot.letsball.activity.MatchDetailActivity;
import com.rayboot.letsball.activity.TeamDetailActivity;
import com.rayboot.letsball.adapter.TeamMatchAdapter;
import com.rayboot.letsball.adapter.TeamPlayerAdapter;
import com.rayboot.letsball.model.MatchInfo;
import com.rayboot.letsball.model.TeamDetailInfo;
import com.rayboot.utility.DataUtility;
import com.rayboot.utility.JsoupUtility;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class TeamMatchFragment extends Fragment {

	private static final String KEY_CONTENT = "TeamMatchFragment:Content";
	private static final String KEY_INT = "TeamMatchFragment:Int";
	private final int ERROR_CODE = 9999;
	private String curUrl = null;
	private int type = 0;
	private View contentView = null;
	View mContent;
	ListView lvContent;
	Object object;
	ArrayList<Object> objList;
	BaseAdapter adapter;
	RelativeLayout rlListTitle;

	String area;
	String leader;
	String ground;
	String net;

	public static TeamMatchFragment newInstance(int type, String content) {
		TeamMatchFragment fragment = new TeamMatchFragment();
		fragment.type = type;
		fragment.curUrl = content;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)
				&& savedInstanceState.containsKey(KEY_INT)) {
			curUrl = savedInstanceState.getString(KEY_CONTENT);
			type = savedInstanceState.getInt(KEY_INT);
		}

		// Inflate the layout for this fragment
		contentView = inflater.inflate(R.layout.league_view, container, false);

		mContent = (View) contentView.findViewById(R.id.content);
		lvContent = (ListView) contentView.findViewById(R.id.lvMatch);
		rlListTitle = (RelativeLayout) contentView
				.findViewById(R.id.rlListTitle);

		lvContent.setOnItemClickListener(onItemClickListener);

		lvContent.setDivider(getResources().getDrawable(
				android.R.color.transparent));

		loadContent(type);

		return contentView;
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (type) {
			case 0:
				// 人员
				break;
			default:
				Intent intent = new Intent(getActivity(),
						MatchDetailActivity.class);
				intent.putExtra("matchinfo", (MatchInfo) objList.get(arg2));
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(
						R.anim.base_slide_right_in, R.anim.stay_anim);
				break;
			}
		}
	};

	@SuppressWarnings("unchecked")
	public void loadContent(int typeCode) {
		try {
			object = DataUtility.getObjectInfo(TeamDetailActivity.SP_NAME,
					curUrl);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object != null) {
			mContent.setVisibility(View.GONE);
			switch (typeCode) {
			case 0:
				rlListTitle.setVisibility(View.VISIBLE);
				objList = ((TeamDetailInfo) object).players;
				adapter = new TeamPlayerAdapter(getActivity(), objList);
				fillTeamDetail();
				break;
			default:
				objList = (ArrayList<Object>) object;
				rlListTitle.setVisibility(View.GONE);
				adapter = new TeamMatchAdapter(getActivity(), objList);
				break;
			}
			lvContent.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		} else {
			if (typeCode == ERROR_CODE) {
				Crouton.makeText(getActivity(), "数据解析错误，请检测网络！", Style.ALERT)
						.show();
				return;
			}
			new LoadContentTask().execute(typeCode, curUrl);
			mContent.setVisibility(View.VISIBLE);
		}
	}

	private void fillTeamDetail() {
		// TODO Auto-generated method stub
		((TextView) (getActivity().findViewById(R.id.tvArea)))
				.setText(((TeamDetailInfo) object).area);
		((TextView) (getActivity().findViewById(R.id.tvLeader)))
				.setText(((TeamDetailInfo) object).leader);
		((TextView) (getActivity().findViewById(R.id.tvGround)))
				.setText(((TeamDetailInfo) object).ground);
		((TextView) (getActivity().findViewById(R.id.tvNet)))
				.setText(((TeamDetailInfo) object).net);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, curUrl);
		outState.putInt(KEY_INT, type);
	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {

		private int typeCode;
		private String urlPath;

		@Override
		protected Object doInBackground(Object... arg) {
			typeCode = (Integer) arg[0];
			urlPath = (String) arg[1];

			try {
				switch (typeCode) {
				case 0:
					DataUtility.setObjectInfo(TeamDetailActivity.SP_NAME,
							urlPath, JsoupUtility.parseTeamDetailInfoUrl(
									getActivity(), urlPath));
					break;
				case 1:
					DataUtility.setObjectInfo(TeamDetailActivity.SP_NAME,
							urlPath, JsoupUtility
									.parseTeamMatchFutureHistoryUrl(
											getActivity(), urlPath, "已结束"));
					break;
				case 2:
					DataUtility.setObjectInfo(TeamDetailActivity.SP_NAME,
							urlPath, JsoupUtility
									.parseTeamMatchFutureHistoryUrl(
											getActivity(), urlPath, "未开始"));
					break;
				case 3:
					DataUtility.setObjectInfo(TeamDetailActivity.SP_NAME,
							urlPath, JsoupUtility.parseTeamMatchNextUrl(
									getActivity(), urlPath, "已结束"));
					break;

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				typeCode = ERROR_CODE;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				typeCode = ERROR_CODE;
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				e.printStackTrace();
				typeCode = ERROR_CODE;
			}

			return typeCode;
		}

		protected void onPostExecute(Object result) {
			// process result
			loadContent((Integer) result);
			contentView.postInvalidate();
		}

	}
}

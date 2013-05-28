package com.rayboot.letsball.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rayboot.letsball.fragment.RoundFragment;
import com.rayboot.letsball.model.LeagueTypeInfo;

public class RoundAdapter extends SampleFragmentAdapter {

	private LeagueTypeInfo leagueInfo;
	MatchInfoAdapter miAdapter;

	public RoundAdapter(FragmentManager fm) {
		super(fm);
	}

	public RoundAdapter(FragmentManager fm, LeagueTypeInfo leagueInfo) {
		super(fm);
		this.leagueInfo = leagueInfo;
	}

	@Override
	public Fragment getItem(int position) {

		return RoundFragment.newInstance(leagueInfo.getMatchUrls()
				.get(position).roundUrl, leagueInfo.name);
	}

	@Override
	public int getCount() {
		return leagueInfo.getMatchUrls().size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return String.format("第%s轮",
				leagueInfo.getMatchUrls().get(position).roundTitle);
	}
}

package com.vesit.praxis14;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter  {
	 private List<Fragment> fragments;


	public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		 this.fragments = fragments;

	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		 return this.fragments.get(position);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 return this.fragments.size();

	}

}

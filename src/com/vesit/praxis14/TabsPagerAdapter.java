package com.vesit.praxis14;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vesit.fragments.Electronics;
import com.vesit.fragments.FunEvents;
import com.vesit.fragments.Robotics;
import com.vesit.fragments.Software;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
		
			return new Electronics();
		case 1:
			
			return new Software();
		case 2:
			
			return new FunEvents();
		case 3:
			
			return new Robotics();


		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
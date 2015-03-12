package com.vesit.praxis14;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import com.vesit.fragments.Electronics;
import com.vesit.fragments.FunEvents;
import com.vesit.fragments.Gaming;
import com.vesit.fragments.Robotics;
import com.vesit.fragments.Software;
import com.vesit.praxis14.R;

public class SwipeableEvents extends ActionBarActivity {
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(SwipeableEvents.this,GridMain.class);
		startActivity(i);
		finish();
	}

	// ...
	FragmentPagerAdapter adapterViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayOptions(actionBar.getDisplayOptions()
	            | ActionBar.DISPLAY_SHOW_CUSTOM);
	   
	    ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
	            ActionBar.LayoutParams.WRAP_CONTENT,
	            ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
	                    | Gravity.CENTER_VERTICAL);
	   

	    LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.actionbar, null);

	    actionBar.setCustomView(v);
		
		setContentView(R.layout.swipeableevents);
		ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
		adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
		vpPager.setAdapter(adapterViewPager);
		vpPager.setOnPageChangeListener(new OnPageChangeListener() {

			// This method will be invoked when a new page becomes selected.
			@Override
			public void onPageSelected(int position) {

			}

			// This method will be invoked when the current page is scrolled
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// Code goes here
			}

			// Called when the scroll state changes:
			// SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
			@Override
			public void onPageScrollStateChanged(int state) {
				// Code goes here
			}
		});
	}

	public static class MyPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 5;

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0: // Fragment # 0 - This will show FirstFragment
				return Electronics.newInstance(0, "Electronics");
			case 1: // Fragment # 0 - This will show FirstFragment different
					// title
				return Software.newInstance(1, "Software");
			case 2: // Fragment # 1 - This will show SecondFragment
				return FunEvents.newInstance(2, "Miscellaneous");
			case 3:
				return Robotics.newInstance(3, "Robotics");
			case 4:
				return Gaming.newInstance(4, "Gaming");
			default:
				return null;
			}
		}

		// Returns the page title for the top indicator
		@Override
		public String getPageTitle(int position) {
		if(position == 0)
			return "Electronics";
		else if(position == 1)
			return "Software";
		else if(position == 2)
			return "Miscellaneous";
		else if(position == 3)
			return "Robotics";
		else if(position == 4)
			return "Gaming";
		return null;
		
		
		}

	}

}
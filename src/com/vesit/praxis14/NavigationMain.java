package com.vesit.praxis14;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.vesit.adapter.NavigationAdapter;
import com.vesit.fragments.About;
import com.vesit.fragments.CouponFragment;
import com.vesit.fragments.Credits;
import com.vesit.fragments.EventRegisterFragment;
import com.vesit.fragments.Sponsors;
import com.vesit.fragments.TopNews;
import com.vesit.fragments.Workshops;
import com.vesit.praxis14.R;
import com.vesit.utils.Constant;
import com.vesit.utils.Menus;
import com.vesit.utils.NavigationList;
import com.vesit.utils.Utils;

public class NavigationMain extends ActionBarActivity implements
		OnDismissCallback {

	private static int lastPosition;
	private ListView listDrawer;
	private int position, pos;

	private DrawerLayout layoutDrawer;
	private LinearLayout linearDrawer;

	private NavigationAdapter navigationAdapter;
	private ActionBarDrawerToggleCompat drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	getSupportActionBar().setIcon(R.drawable.ic_launcher);

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
		
		
		setContentView(R.layout.navigation_main);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		listDrawer = (ListView) findViewById(R.id.listDrawer);
		linearDrawer = (LinearLayout) findViewById(R.id.linearDrawer);
		layoutDrawer = (DrawerLayout) findViewById(R.id.layoutDrawer);

		if (listDrawer != null) {
			navigationAdapter = NavigationList.getNavigationAdapter(this);
		}

		listDrawer.setAdapter(navigationAdapter);
		listDrawer.setOnItemClickListener(new DrawerItemClickListener());

		drawerToggle = new ActionBarDrawerToggleCompat(this, layoutDrawer);
		layoutDrawer.setDrawerListener(drawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(NavigationMain.this);
		position = prefs.getInt("POSITION", 0);

		Log.d("two", "Goes to position " + String.valueOf(position));
		lastPosition = position;
		if (savedInstanceState != null) {
			setLastPosition(savedInstanceState.getInt(Constant.LAST_POSITION));

			navigationAdapter.resetarCheck();
			setTitleFragments(lastPosition);
			navigationAdapter.setChecked(lastPosition, true);

		} else {
			setLastPosition(lastPosition);
			setFragmentList(lastPosition);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(Constant.LAST_POSITION, lastPosition);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case Menus.HOME:
			if (layoutDrawer.isDrawerOpen(linearDrawer)) {
				layoutDrawer.closeDrawer(linearDrawer);
			} else {
				layoutDrawer.openDrawer(linearDrawer);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	public void setTitleActionBar(CharSequence informacao) {
		getSupportActionBar().setTitle(informacao);
	}

	public void setSubtitleActionBar(CharSequence informacao) {
		getSupportActionBar().setSubtitle(informacao);
	}

	public void setIconActionBar(int icon) {
		getSupportActionBar().setIcon(icon);
	}

	public static void setLastPosition(int posicao) {
		lastPosition = posicao;
	}

	private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle {

		public ActionBarDrawerToggleCompat(Activity mActivity,
				DrawerLayout mDrawerLayout) {
			super(mActivity, mDrawerLayout,
					R.drawable.ic_action_navigation_drawer,
					R.string.drawer_open, R.string.drawer_close);
		}

		@Override
		public void onDrawerClosed(View view) {
			supportInvalidateOptionsMenu();
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			navigationAdapter.notifyDataSetChanged();
			supportInvalidateOptionsMenu();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int posicao,
				long id) {

			setLastPosition(posicao);
			setFragmentList(lastPosition);
			layoutDrawer.closeDrawer(linearDrawer);
		}
	}

	private void setFragmentList(int posicao) {

		FragmentManager fragmentManager = getSupportFragmentManager();

		switch (posicao) {

		case 0:
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, new TopNews(), "topnews")
					.commit();

			break;
		case 1:
			Intent i = new Intent(NavigationMain.this, SwipeableEvents.class);
			startActivity(i);
			finish();

			break;
		case 2:
			Log.d("three", "Opens Register Fragment");

			 fragmentManager
			.beginTransaction()
		 .replace(R.id.content_frame,
		 new EventRegisterFragment(), "eventregister")
		 .commit();
		

			break;
		case 3:
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, new Workshops(), "workshops")
					.commit();
			break;
		case 4:
			fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame, new CouponFragment(), "coupon")
					.commit();
			break;
		case 5:

			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, new About(), "about").commit();
			break;
		case 6:
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, new Sponsors(), "sponsors")
					.commit();
			Intent i1 = new Intent(NavigationMain.this,GridMain.class);
			startActivity(i1);
			finish();

			break;
		case 7:
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, new Credits(), "creidts")
					.commit();
			break;

		}

		navigationAdapter.resetarCheck();
		setTitleFragments(lastPosition);
		navigationAdapter.setChecked(posicao, true);
	}

	static boolean active = false;

	@Override
	public void onStart() {
		super.onStart();
		active = true;
	}

	@Override
	public void onStop() {
		super.onStop();
		active = false;
	}

	public static boolean isActive() {
		return active;
	}

	private void setTitleFragments(int position) {

		setSubtitleActionBar(Utils.getTitleItem(NavigationMain.this, position));
	}

	public void addCoupon() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		CouponFragment fragment = new CouponFragment();
		fragmentTransaction.add(R.id.content_frame, fragment);
		fragmentTransaction.commit();
	}

	@Override
	public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
		// TODO Auto-generated method stub

	}

	public void onBackPressed() {
		Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(
				"topnews");
		Intent intent = new Intent(NavigationMain.this, GridMain.class);
		if (fragment1 instanceof TopNews) {
			startActivity(intent);
			finish();

		}
		Fragment fragment2 = getSupportFragmentManager().findFragmentByTag(
				"workshops");
		if (fragment2 instanceof Workshops) {
			startActivity(intent);
			finish();

		}
		Fragment fragment3 = getSupportFragmentManager().findFragmentByTag(
				"eventregister");
		if (fragment3 instanceof EventRegisterFragment) {
			startActivity(intent);
			finish();

		}
		Fragment fragment4 = getSupportFragmentManager().findFragmentByTag(
				"coupon");
		if (fragment4 instanceof CouponFragment) {
			startActivity(intent);
			finish();

		}
		Fragment fragment5 = getSupportFragmentManager().findFragmentByTag(
				"sponsors");
		if (fragment5 instanceof Sponsors) {
			startActivity(intent);
			finish();

		}
		Fragment fragment6 = getSupportFragmentManager().findFragmentByTag(
				"about");
		if (fragment6 instanceof About) {
			startActivity(intent);
			finish();

		}
		Fragment fragment7 = getSupportFragmentManager().findFragmentByTag(
				"creidts");
		if (fragment7 instanceof Credits) {
			startActivity(intent);
			finish();

		}

		super.onBackPressed();
	}
}

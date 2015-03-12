package com.vesit.utils;

import android.content.Context;

import com.vesit.adapter.NavigationAdapter;
import com.vesit.adapter.NavigationItemAdapter;
import com.vesit.praxis14.R;
import com.vesit.praxis14.R.array;

public class NavigationList {

	public static NavigationAdapter getNavigationAdapter(Context context) {

		NavigationAdapter navigationAdapter = new NavigationAdapter(context);
		String[] menuItems = context.getResources().getStringArray(
				R.array.nav_menu_items);
		for (int i = 0; i < menuItems.length; i++) {

			String title = menuItems[i];

			NavigationItemAdapter itemNavigation;

			itemNavigation = new NavigationItemAdapter(title);

			navigationAdapter.addItem(itemNavigation);

		}
		return navigationAdapter;
	}
}

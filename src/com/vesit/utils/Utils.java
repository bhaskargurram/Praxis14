package com.vesit.utils;

import com.vesit.praxis14.R;

import android.content.Context;

public class Utils {

	// get title of the item navigation
	public static String getTitleItem(Context context, int posicao) {
		String[] titulos = context.getResources().getStringArray(
				R.array.nav_menu_items);
		return titulos[posicao];
	}

}

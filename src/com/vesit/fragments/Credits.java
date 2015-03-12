package com.vesit.fragments;

import com.vesit.adapter.CreditAdapter;
import com.vesit.praxis14.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class Credits extends Fragment {

	ListView listview;
	CreditAdapter adapter;

	private ArrayList<HashMap<String, String>> arraylist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.creditlist, container, false);

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		int image[] = new int[] { R.drawable.principal,
				R.drawable.vpnew2,R.drawable.sabnissir2 };
		String name[] = new String[] { "Dr.(Mrs.) J.M.Nair", "Mrs. Vijayalaxmi M.","Prof. Manoj Sabnis" };
		String position[] = new String[] { "Principal, VESIT", "Vice-Principal, VESIT" ,"Dean of Student Affairs, VESIT"};
		arraylist = new ArrayList<HashMap<String, String>>();
		int core_image1[] = new int[] {};
		int core_image2[] = new int[] {};
		String core_name1[] = new String[] {};
		String core_name2[] = new String[] {};
		String core_post1[] = new String[] {};
		String core_post2[] = new String[] {};

		for (int i = 0; i < image.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("image", String.valueOf(image[i]));
			map.put("name", name[i]);
			map.put("position", position[i]);

			arraylist.add(map);

		}

		for (int i = 0; i < core_image1.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name1", core_name1[i]);
			map.put("name2", core_name2[i]);
			map.put("post1", core_post1[i]);
			map.put("post2", core_post2[i]);
			map.put("image1", String.valueOf(core_image1[i]));
			map.put("image2", String.valueOf(core_image2[i]));

			arraylist.add(map);
		}

		listview = (ListView) rootView.findViewById(R.id.listofcredits);

		adapter = new CreditAdapter(getActivity(), arraylist);
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				adapter);
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		swingBottomInAnimationAdapter.setAbsListView(listview);

		listview.setAdapter(swingBottomInAnimationAdapter);

		return rootView;

	}

}

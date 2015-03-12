package com.vesit.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.vesit.adapter.CouponOfflineAdapter;
import com.vesit.adapter.ListViewAdapter;
import com.vesit.praxis14.GetEmail;
import com.vesit.praxis14.JSONParser;
import com.vesit.praxis14.JSONfunctions;
import com.vesit.praxis14.R;
import com.vesit.utils.Coupons;
import com.vesit.utils.DatabaseHandler;

public class CouponFragment extends Fragment {

	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	ListViewAdapter adapter;
	private boolean hasVisisted;
	ArrayList<HashMap<String, String>> arraylist;
	private boolean something;
	private NetCheck netcheck;
	private DownloadJSON task;
	CouponOfflineAdapter offlineAdapter;
	private ArrayList<HashMap<String, String>> couponList;
	private LoadOffline offline;
	DatabaseHandler db;

	private int allCommon = 0;
	private DecideTask decidetask;
	private int count;
	private boolean topnews;
	JSONParser jsonParser = new JSONParser();
	SwipeRefreshLayout swipeRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.couponactivity, container,
				false);

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		setRetainInstance(true);
		SharedPreferences prefe = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		String e = prefe.getString("E-mail", "");
		if (e.matches("")) {
			
			Intent foo = new Intent(getActivity(), GetEmail.class);
			startActivity(foo);
			getActivity().finish();
		}
		// TODO Auto-generated method stub

		db = new DatabaseHandler(getActivity());
		swipeRefreshLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.container);
		swipeRefreshLayout.setRefreshing(true);
		swipeRefreshLayout.setColorScheme(R.color.blue_dark,
				R.color.green_dark, R.color.orange_light, R.color.red_light);
		netcheck = new NetCheck();
		task = new DownloadJSON();
		offline = new LoadOffline();
		decidetask = new DecideTask();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		topnews = preferences.getBoolean("COUPONS_FRAGMENT", false);
		if (!topnews) {

			preferences.edit().putBoolean("COUPONS_FRAGMENT", true).commit();

		}
		hasVisisted = preferences.getBoolean("HAS_VISISTED_COUPONS", false);

		if (!hasVisisted) {
			Toast.makeText(getActivity(), "Swipe down to refresh.",
					Toast.LENGTH_LONG).show();

			preferences.edit().putBoolean("HAS_VISISTED_COUPONS", true)
					.commit();

		}
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new DecideTask().execute();
				// TODO Auto-generated method stub
			}
		});

		new DecideTask().execute();

		return rootView;

	}

	@Override
	public void onPause() {
		super.onPause();
		// TODO Auto-generated method stub
		decidetask.cancel(true);
		netcheck.cancel(true);
		task.cancel(true);
		offline.cancel(true);

	}

	@Override
	public void onStop() {
		super.onStop();
		decidetask.cancel(true);
		netcheck.cancel(true);
		task.cancel(true);
		offline.cancel(true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		decidetask.cancel(true);
		netcheck.cancel(true);
		task.cancel(true);
		offline.cancel(true);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		decidetask.cancel(true);
		netcheck.cancel(true);
		task.cancel(true);
		offline.cancel(true);
	}

	class DecideTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			if (decidetask.isCancelled())
				return;
			Log.d("msg", "DecideTask-pre-execute");

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i <= 1; i++) {
				if (decidetask.isCancelled())
					break;
				count = db.getCouponsCount();
			}
			return count;
		}

		protected void onPostExecute(final Integer count) {
			if (decidetask.isCancelled())
				return;
			Log.d("msg", "DecideTask-post-execute");

			if (count == 0) {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				preferences.edit().putBoolean("mCoupons", false).commit();
				new NetCheck().execute();

			} else {
				Log.d("count", "not equal to one");
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				preferences.edit().putBoolean("mCoupons", true).commit();
				new LoadOffline().execute();
			}

		}

	}

	private class NetCheck extends AsyncTask<String, String, Boolean> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			if (netcheck.isCancelled())
				return;
			Log.d("msg", "NetChecking-preex");

		}

		@Override
		protected Boolean doInBackground(String... args) {
			Log.d("msg", "NetChecking-doinback");

			/**
			 * Gets current device state and checks for working internet
			 * connection by trying Google.
			 **/
			for (int i = 0; i <= 1; i++) {
				if (netcheck.isCancelled())
					break;
				ConnectivityManager cm = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);

				NetworkInfo netInfo = cm.getActiveNetworkInfo();
				if (netInfo != null && netInfo.isConnected()) {
					try {

						URL url = new URL("http://www.google.co.in/");
						HttpURLConnection urlc = (HttpURLConnection) url
								.openConnection();

						urlc.setConnectTimeout(1000);
						urlc.connect();

						if (urlc.getResponseCode() == 200) {
							return true;
						}
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			return false;

		}

		@Override
		protected void onPostExecute(Boolean th) {
			if (netcheck.isCancelled())
				return;
			Log.d("msg", "NetChecking-postexec");
			if (th == true) {

				new DownloadJSON().execute();
			} else {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				preferences.edit().putBoolean("COUPONS_FRAGMENT", false)
						.commit();

				Toast.makeText(getActivity(), "Check Internet Connection.",
						Toast.LENGTH_SHORT).show();

				swipeRefreshLayout.setRefreshing(false);

			}
		}
	}

	private class DownloadJSON extends AsyncTask<Void, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (task.isCancelled())
				return;

		}

		@Override
		protected Integer doInBackground(Void... params) {

			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
			String email = preferences.getString("E-mail", "");

			// Create an array

			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions
					.getJSONfromURL("http://praxis-14.vesit.edu/coupon.php?email="
							+ email);

			try {
				for (int j = 0; j <= 1; j++) {
					if (task.isCancelled())
						break;
					jsonarray = jsonobject.getJSONArray("coupons");

					for (int i = 0; i < jsonarray.length(); i++) {
						if (task.isCancelled())
							break;
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);

						map.put("regid", jsonobject.getString("regid"));
						map.put("eventname", jsonobject.getString("eventname"));
						map.put("uniqueid", jsonobject.getString("uniqueid"));
						map.put("status", jsonobject.getString("status"));

						arraylist.add(map);

						Log.d("Database", "Inserting");
						db.addCoupons(new Coupons(
								jsonobject.getString("regid"), jsonobject
										.getString("eventname"), jsonobject
										.getString("uniqueid"), jsonobject
										.getString("status")));

					}
					couponList = db.getAllCoupons();
					Collection<HashMap<String, String>> commonList = CollectionUtils
							.retainAll(arraylist, couponList);

					Log.d("common list size", String.valueOf(commonList.size()));
					Log.d("array list size", String.valueOf(arraylist.size()));

					if (commonList.size() == arraylist.size()) {
						allCommon = 1;
					}
				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return allCommon;

		}

		@Override
		protected void onPostExecute(final Integer allCommon) {
			if (task.isCancelled())
				return;

			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
			something = preferences.getBoolean("mCoupons", false);

			if (allCommon == 1 && topnews && something) {
				Log.d("msg", "DownloadJSON-post-execute- All common");
				swipeRefreshLayout.setRefreshing(false);
				return;
			} else {
				if (arraylist.isEmpty())
					getActivity().setContentView(R.layout.nocoupons);
				else {
					listview = (ListView) getView().findViewById(
							R.id.activity_googlecards_listview);

					adapter = new ListViewAdapter(getActivity(), arraylist);

					SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
							adapter);

					// Assign the ListView to the AnimationAdapter and
					// vice
					// versa
					swingBottomInAnimationAdapter.setAbsListView(listview);
					listview.setAdapter(swingBottomInAnimationAdapter);
				}
			}
			swipeRefreshLayout.setRefreshing(false);

		}
	}

	private class LoadOffline extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (offline.isCancelled())
				return;
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("Reading: ", "Reading Coupons Table - Database");

			couponList = db.getAllCoupons();

			return null;

		}

		@Override
		protected void onPostExecute(Void args) {
			if (offline.isCancelled())
				return;

			if (couponList.isEmpty())
				getActivity().setContentView(R.layout.nocoupons);
			else {
				Log.d("MESSAGE", "couponList IS NOT EMPTY");
				listview = (ListView) getView().findViewById(
						R.id.activity_googlecards_listview);

				offlineAdapter = new CouponOfflineAdapter(getActivity(),
						couponList);

				SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						offlineAdapter);

				// Assign the ListView to the AnimationAdapter and vice
				// versa
				swingBottomInAnimationAdapter.setAbsListView(listview);
				listview.setAdapter(swingBottomInAnimationAdapter);

			}

			new NetCheck().execute();
		}
	}

}

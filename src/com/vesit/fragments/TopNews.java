package com.vesit.fragments;

import java.io.File;
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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.koushikdutta.async.future.Future;
import com.koushikdutta.ion.Ion;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.vesit.adapter.OfflineAdapter;
import com.vesit.adapter.TopNewAdapter;
import com.vesit.praxis14.GridMain;
import com.vesit.praxis14.JSONfunctions;
import com.vesit.praxis14.NavigationMain;
import com.vesit.praxis14.R;
import com.vesit.utils.DatabaseHandler;
import com.vesit.utils.Stuff;

public class TopNews extends Fragment {
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	TopNewAdapter adapter;
	private String filename;
	private boolean hasVisisted;
	static String news = "topnews";
	static String image_url = "image";
	static String title = "title";
	private ArrayList<HashMap<String, String>> arraylist;
	SwipeRefreshLayout swipeRefreshLayout;
	private int count;
	private DownloadJSON task;
	private NetCheck netcheck;
	private boolean topnews;
	private String name;
	private Future<File> downloading;

	private int allCommon = 0;
	private DecideTask decidetask;
	private boolean something;
	OfflineAdapter offlineAdapter;
	private ArrayList<HashMap<String, String>> stuffList;
	private LoadOffline offline;
	DatabaseHandler db;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		// TODO Auto-generated method stub
		View rootView = inflater
				.inflate(R.layout.topnewslist, container, false);

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
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

		topnews = preferences.getBoolean("TOP_NEWS", false);
		if (!topnews) {

			preferences.edit().putBoolean("TOP_NEWS", true).commit();

		}
		hasVisisted = preferences.getBoolean("HAS_VISISTED_TOP_NEWS", false);

		if (!hasVisisted) {
			Toast.makeText(getActivity(), "Swipe down to refresh.",
					Toast.LENGTH_LONG).show();

			preferences.edit().putBoolean("HAS_VISISTED_TOP_NEWS", true)
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
				count = db.getStuffCount();
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
				preferences.edit().putBoolean("SOMETHING", false).commit();
				new NetCheck().execute();

			} else {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				preferences.edit().putBoolean("SOMETHING", true).commit();
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

						URL url = new URL("http://www.google.com");
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
				preferences.edit().putBoolean("TOP_NEWS", false).commit();

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
			Log.d("msg", "DownloadJSON-pre-execute");
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions
					.getJSONfromURL("http://praxis-14.vesit.edu/topnews.php");

			try {


				jsonarray = jsonobject.getJSONArray("tnews");

				for (int i = 0; i < jsonarray.length(); i++) {
					if (task.isCancelled())
						break;
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);

					map.put("news", jsonobject.getString("news"));

					map.put("image", jsonobject.getString("image"));
					map.put("title", jsonobject.getString("title"));

					arraylist.add(map);

					Log.d("File Writing", "Writing Files");
					String url = jsonobject.getString("image");

					if (url.length() > 34)
						filename = url.substring(34);
					else
						filename = "";

					File dir = new File("sdcard/.Praxis14/");

					dir.mkdirs();

					for (File f : dir.listFiles()) {
						if (f.isFile())
							name = f.getName();
						if (filename == name) {
							break;
						} else {
							downloading = Ion.with(TopNews.this).load(url)
									.write(new File(dir, filename));
						}
					}

					Log.d("Database", "Inserting");
					db.addStuff(new Stuff(jsonobject.getString("title"),
							jsonobject.getString("news"), jsonobject
									.getString("image")));

				}
				stuffList = db.getAllStuff();
				Log.d("something",String.valueOf(db.getStuffCount()));
				Collection<HashMap<String, String>> commonList = CollectionUtils
						.retainAll(arraylist, stuffList);

				Log.d("common list size", String.valueOf(commonList.size()));
				Log.d("array list size", String.valueOf(arraylist.size()));

				if (commonList.size() == arraylist.size()) {
					allCommon = 1;
				}
			
				else {
					db.deleteStuff();
				
				}
				// }
			} catch (JSONException e) {
				Log.d("Error", e.getMessage());

			}
			return allCommon;
		}

		@Override
		protected void onPostExecute(final Integer allCommon) {
			if (task.isCancelled())
				return;
			Log.d("msg", "DownloadJSON-post-execute");

			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
			something = preferences.getBoolean("SOMETHING", false);
			if (allCommon == 1 && topnews && something) {
				Log.d("msg", "DownloadJSON-post-execute- All common");
				swipeRefreshLayout.setRefreshing(false);
				return;
			} else {
				Log.d("msg", "DownloadJSON-post-execute- All not common");
				listview = (ListView) getView()
						.findViewById(R.id.listoftopnews);

				adapter = new TopNewAdapter(getActivity(), arraylist);

				SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
						adapter);

				// Assign the ListView to the AnimationAdapter and vice
				// versa
				swingBottomInAnimationAdapter.setAbsListView(listview);
				listview.setAdapter(swingBottomInAnimationAdapter);
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
			Log.d("msg", "LoadOffline-pre-execute");
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("Reading: ", "Reading Top News Table - Database");

			stuffList = db.getAllStuff();

			return null;

		}

		@Override
		protected void onPostExecute(Void args) {
			if (offline.isCancelled())
				return;
			Log.d("msg", "LoadOffline-post-execute");

			listview = (ListView) getView().findViewById(R.id.listoftopnews);

			offlineAdapter = new OfflineAdapter(getActivity(), stuffList);

			SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
					offlineAdapter);

			// Assign the ListView to the AnimationAdapter and vice
			// versa
			swingBottomInAnimationAdapter.setAbsListView(listview);
			listview.setAdapter(swingBottomInAnimationAdapter);

			new NetCheck().execute();
		}
	}

}

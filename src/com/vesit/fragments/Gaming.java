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

import android.content.Context;
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
import android.widget.ListView;



import com.koushikdutta.async.future.Future;
import com.koushikdutta.ion.Ion;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.vesit.adapter.EventAdapter;
import com.vesit.adapter.OfflineAdapterEvents;
import com.vesit.praxis14.JSONfunctions;
import com.vesit.praxis14.R;
import com.vesit.utils.DatabaseHandler;
import com.vesit.utils.Events;

public class Gaming extends Fragment {
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	EventAdapter adapter;
	SwipeRefreshLayout swipeRefreshLayout;
	private NetCheck netcheck;
	private DownloadJSON task;
	private boolean something;
	private String name;
	private Future<File> downloading;
	static String REG_ID = "regid";
	static String STATUS = "status";
	static String EVENT_NAME = "eventname";
	private ArrayList<HashMap<String, String>> arraylist;
	private boolean topnews;
	private int allCommon = 0;
	private DecideTask decidetask;
	private int count;
	OfflineAdapterEvents offlineAdapter;
	private ArrayList<HashMap<String, String>> gamingList;
	private LoadOffline offline;
	DatabaseHandler db;

	  public static Gaming newInstance(int page, String title) {
		  Gaming fragmentFifth = new Gaming();
	        Bundle args = new Bundle();
	        args.putInt("someInt", page);
	        args.putString("someTitle", title);
	        fragmentFifth.setArguments(args);
	        return fragmentFifth;
	    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.eventlist, container, false);

		setRetainInstance(true);
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

		topnews = preferences.getBoolean("GAMING", false);
		if (!topnews) {

			preferences.edit().putBoolean("GAMING", true).commit();

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
				count = db.getEventsCount();
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
				preferences.edit().putBoolean("mGaming", false).commit();
				new NetCheck().execute();

			} else {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getActivity());
				preferences.edit().putBoolean("mGaming", true).commit();
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
				preferences.edit().putBoolean("GAMING", false).commit();

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
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions
					.getJSONfromURL("http://praxis-14.vesit.edu/events.php?category=Gaming");

			try {
				for (int j = 0; j <= 1; j++) {
					if (task.isCancelled())
						break;
					jsonarray = jsonobject.getJSONArray("events");

					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);

						map.put("eventname", jsonobject.getString("eventname"));
						map.put("description",
								jsonobject.getString("description"));
						map.put("image", jsonobject.getString("image"));
						map.put("category", jsonobject.getString("category"));
						map.put("teams", jsonobject.getString("teams"));
						map.put("fees", jsonobject.getString("fees"));
						map.put("pm_1", jsonobject.getString("pm_1"));
						map.put("pm_2", jsonobject.getString("pm_2"));
						map.put("pm_3", jsonobject.getString("pm_3"));
						arraylist.add(map);
						Log.d("File Writing", "Writing Files");
						String url = jsonobject.getString("image");

						String filename;
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
								downloading = Ion.with(Gaming.this).load(url)
										.write(new File(dir, filename));
							}
						}

						downloading = Ion.with(Gaming.this).load(url)
								.write(new File(dir, filename));
						Log.d("Database", "Inserting");
						db.addEvents(new Events(jsonobject
		
								.getString("eventname"), jsonobject
								.getString("description"), jsonobject
								.getString("image"), jsonobject
								.getString("category"),jsonobject
								.getString("teams"),jsonobject
								.getString("fees"),jsonobject
								.getString("pm_1"),jsonobject
								.getString("pm_2"),jsonobject
								.getString("pm_3")));

					}
					gamingList = db.getAllEvents("Gaming");
					Collection<HashMap<String, String>> commonList = CollectionUtils
							.retainAll(arraylist, gamingList);

					Log.d("common list size", String.valueOf(commonList.size()));
					Log.d("array list size", String.valueOf(arraylist.size()));

					if (commonList.size() == arraylist.size()) {
						allCommon = 1;
					}
					else {
						db.deleteEvents("Gaming");
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
			something = preferences.getBoolean("mGaming", false);

			if (allCommon == 1 && topnews && something) {
				Log.d("msg", "DownloadJSON-post-execute- All common");
				swipeRefreshLayout.setRefreshing(false);
				return;
			} else {
				listview = (ListView) getView().findViewById(R.id.listofevents);

				adapter = new EventAdapter(getActivity(), arraylist);

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
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("Reading: ", "Reading Top News Table - Database");

			gamingList = db.getAllEvents("Gaming");

			return null;

		}

		@Override
		protected void onPostExecute(Void args) {
			if (offline.isCancelled())
				return;

			listview = (ListView) getView().findViewById(R.id.listofevents);

			offlineAdapter = new OfflineAdapterEvents(getActivity(), gamingList);

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

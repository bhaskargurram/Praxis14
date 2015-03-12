package com.vesit.fragments;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.vesit.praxis14.ConfirmEmail;
import com.vesit.praxis14.GetEmail;
import com.vesit.praxis14.JSONParser;
import com.vesit.praxis14.R;

public class EventRegisterFragment extends ListFragment {
	String eventname;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private boolean hasVisisted;
	private static String url_create_product = "http://praxis-14.vesit.edu/registerevent.php";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		String e = p.getString("E-mail", "");
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/OpenSans-Regular-webfont.ttf");
		Boolean proceed = p.getBoolean("isProceedDone", false);

		if (e.matches("")) {
			
			Intent intent2 = new Intent(getActivity(), GetEmail.class);
			startActivity(intent2);
			getActivity().finish();
		} else if (!proceed) {
			Intent i = new Intent(getActivity(), ConfirmEmail.class);
			startActivity(i);
			getActivity().finish();
		} else {

			// TODO Auto-generated method stub
			setRetainInstance(true);
			String[] events = new String[] { "Live Wire", "Circuit Work It",
					"C Link", "Cross Connect", "Weave the Web",
					"Counter Strike Team", "Fifa '11 Team", "Fifa '11 Indies",
					"Fifa '14 Console Team", "Fifa '14 Indies",
					"Need For Speed", "Road Rash", "Film It", "Snap That",
					"Technical Paper Presentation", "Tech-trics", "Brainwave",
					"Dalal Street", "The Executive Suite", "Egg Drop",
					"Nova Alley", "Bridge the Gap", "Crystal Maze",
					"Robo Soccer", "XLR8", "Clash of Steel", "AutoBot",
					"Touch Me Not", "Third Eye", "The Poseidon",
					"Photoshop Competition", "Technical Article Writing",
					"All Mumbai Treasure Hunt", "Robotics Combo",
					"Film It Snap That COMBO", "Egg Drop Bridge the Gap COMBO",
					"Electronics Combo", "Software Combo",
					"Nova Alley Tech trics Brainwave COMBO" };
			List aList = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < events.length; i++) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("txt", events[i]);
				aList.add(hm);
			}
		
			String[] from = { "txt" };
			int[] to = { R.id.eventname };
			SimpleAdapter adapter = new SimpleAdapter(getActivity()
					.getBaseContext(), aList, R.layout.list_item2, from, to);

			setListAdapter(adapter);
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getActivity());

			hasVisisted = preferences.getBoolean("HAS_VISISTED_BEFORE", false);

			if (!hasVisisted) {
				Toast.makeText(getActivity(), "Tap on an Event to Register.",
						Toast.LENGTH_LONG).show();

				preferences.edit().putBoolean("HAS_VISISTED_BEFORE", true)
						.commit();

			}

		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		eventname = l.getItemAtPosition(position).toString();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		alertDialogBuilder.setTitle("Confirm Registration");
		alertDialogBuilder
				.setMessage(
						"Are you sure you want to Register for this Event? Once registered, it cannot be undone.")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// if this button is clicked, close
								// current activity
								new NetCheck().execute();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			nDialog = new ProgressDialog(getActivity());
			nDialog.setMessage("Loading..");
			nDialog.setTitle("Checking Network");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

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
			return false;

		}

		@Override
		protected void onPostExecute(Boolean th) {

			if (th == true) {
				nDialog.dismiss();
				new RegisterEvent().execute();
			} else {
				nDialog.dismiss();
				Toast.makeText(getActivity(), "check internet connection",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class RegisterEvent extends AsyncTask<String, Integer, Integer> {
		int success;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Registering. Please Wait... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected Integer doInBackground(String... args) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(getActivity());

			String email = preferences.getString("E-mail", "");

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("eventname", eventname));
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);

			try {
				success = json.getInt(TAG_SUCCESS);
				return success;

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return -1;
		}

		protected void onPostExecute(final Integer success) {
			pDialog.dismiss();
			new Handler().post(new Runnable() {
				public void run() {
					if (success == 1) {

						Toast.makeText(
								getActivity(),
								"Registration successful. Check for the Coupon(s) in the Navigation Drawer.",
								Toast.LENGTH_LONG).show();

					} else if (success == 2) {
						Toast.makeText(getActivity(),
								"You can only Register once for an Event!",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(),
								"Registration unsuccessful.",
								Toast.LENGTH_SHORT).show();
					}

				}
			});

		}
	}
}
/**
 * 
 */
package com.vesit.praxis14;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.vesit.praxis14.R;

/**
 * @author ritesh
 * 
 */
public class ConfirmEmail extends ActionBarActivity {
	private ProgressDialog pDialog;
	boolean isProceedDone = false;

	Button proceed;
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static String confirmed_email = "http://praxis-14.vesit.edu/confirmedemails.php";
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(ConfirmEmail.this, GridMain.class);

		startActivity(intent);
		finish();
		super.onBackPressed();
	}
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

		setContentView(R.layout.confirmemail);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(ConfirmEmail.this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isProceedDone", isProceedDone);
		editor.commit();
		Button proceed = (Button) findViewById(R.id.proceed);
		proceed.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new NetCheck().execute();
			}
		});
	}

	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			nDialog = new ProgressDialog(ConfirmEmail.this);
			nDialog.setMessage("Loading..");
			nDialog.setTitle("Checking Network");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

			ConnectivityManager cm = (ConnectivityManager) ConfirmEmail.this
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
				new ConfirmEmailID().execute();
			} else {
				nDialog.dismiss();
				Toast.makeText(ConfirmEmail.this, "check internet connection",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class ConfirmEmailID extends AsyncTask<String, Integer, Integer> {
		int success;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(ConfirmEmail.this);
			pDialog.setMessage("Verifying. Please Wait... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected Integer doInBackground(String... args) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(ConfirmEmail.this);
			String email = preferences.getString("E-mail", "");

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("email", email));

			JSONObject json = jsonParser.makeHttpRequest(confirmed_email,
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
						isProceedDone = true;
						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(ConfirmEmail.this);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putBoolean("isProceedDone", isProceedDone);
						editor.commit();
						Toast.makeText(ConfirmEmail.this,
								"Email verification successful!",
								Toast.LENGTH_SHORT).show();
						Intent i = new Intent(ConfirmEmail.this, GetEmail.class);
						startActivity(i);
						finish();

					} else {
						Toast.makeText(ConfirmEmail.this,
								"Email verification failed!",
								Toast.LENGTH_SHORT).show();
					}

				}
			});

		}
	}
}

package com.vesit.praxis14;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.vesit.praxis14.R;

public class GetEmail extends ActionBarActivity {

	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;

	ProgressDialog pDialog;
	ArrayList<HashMap<String, String>> arraylist;
	static String TAG_SUCCESS = "success";
	static String STATUS = "status";
	private String possibleEmail;
	EditText inputEmail;
	EditText inputPassword;
	Button submit, signup;
	String email, password;
	JSONParser jsonParser = new JSONParser();
	private static String url_create_product = "http://praxis-14.vesit.edu/signin.php";

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

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.actionbar, null);

		actionBar.setCustomView(v);
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
				"fonts/OpenSans-Light-webfont.ttf");
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(GetEmail.this);
		String e = preferences.getString("E-mail", "");
		Boolean proceed = preferences.getBoolean("isProceedDone", false);
		if (e.matches("")) {
			setContentView(R.layout.getemail);

			inputEmail = (EditText) findViewById(R.id.email);
			inputEmail.setTypeface(tf);
			inputPassword = (EditText) findViewById(R.id.password);
			inputPassword.setTypeface(tf);
			submit = (Button) findViewById(R.id.submit);
			submit.setTypeface(tf);
			signup = (Button) findViewById(R.id.signup);
			signup.setTypeface(tf);
			Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
			Account[] accounts = AccountManager.get(GetEmail.this)
					.getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					possibleEmail = account.name;

				}
			}
			inputEmail.setText(possibleEmail);
			submit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					email = inputEmail.getText().toString();
					password = inputPassword.getText().toString();
					if (email.matches("")) {
						Toast.makeText(GetEmail.this,
								"Please enter your E-mail ID",
								Toast.LENGTH_SHORT).show();

					}
					if (password.matches("")) {
						Toast.makeText(GetEmail.this,
								"Please enter your password",
								Toast.LENGTH_SHORT).show();

					} else {

						new NetCheck().execute();
					}
				}
			});
			signup.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					Intent i = new Intent(GetEmail.this, Registration.class);
					startActivity(i);

				}
			});

		} else {
			if (proceed == false) {
				Intent i = new Intent(GetEmail.this, ConfirmEmail.class);
				startActivity(i);
				finish();
			} else {
				Intent two = new Intent(GetEmail.this, NavigationMain.class);
				SharedPreferences post = PreferenceManager
						.getDefaultSharedPreferences(GetEmail.this);

				post.edit().putInt("POSITION", 2).commit();
				startActivity(two);
				finish();
			}
		}

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(GetEmail.this, GridMain.class);

		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			nDialog = new ProgressDialog(GetEmail.this);
			nDialog.setMessage("Loading..");
			nDialog.setTitle("Checking Network");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

			ConnectivityManager cm = (ConnectivityManager) GetEmail.this
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
			return false;

		}

		@Override
		protected void onPostExecute(Boolean th) {

			if (th == true) {
				nDialog.dismiss();
				new SendEmailID().execute();
			} else {
				nDialog.dismiss();
				Toast.makeText(GetEmail.this, "check internet connection",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class SendEmailID extends AsyncTask<String, Integer, Integer> {
		int success;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(GetEmail.this);
			pDialog.setMessage("Signing you in, please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected Integer doInBackground(String... args) {

			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));

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

			if (success == 1) {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(GetEmail.this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("E-mail", email);
				editor.commit();
				boolean isProceedDone = true;

				editor.putBoolean("isProceedDone", isProceedDone);
				editor.commit();
				Intent intent = new Intent(GetEmail.this, NavigationMain.class);
				SharedPreferences pre = PreferenceManager
						.getDefaultSharedPreferences(GetEmail.this);

				pre.edit().putInt("POSITION", 2).commit();
				startActivity(intent);
				finish();

			} else {
				Toast.makeText(GetEmail.this, "Invalid E-mail/Password",
						Toast.LENGTH_SHORT).show();
			}

		}
	}
}
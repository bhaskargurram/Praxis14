package com.vesit.praxis14;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.vesit.praxis14.R;

public class Registration extends ActionBarActivity {
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private String possibleEmail;
	private static final String TAG_SUCCESS = "success";
	EditText inputFname;
	EditText inputLname, inputPassword, inputConfirmpassword,classcoll;
	Typeface tf;
	EditText inputEventname;
	EditText inputContact;
	TextView inputEmail;
	Boolean isProceedDone = false;
	String email, fname, lname, c, password, confirmpassword, uniqueid,classtext;

	private static String url_create_product = "http://praxis-14.vesit.edu/registratio.php";

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
		setContentView(R.layout.registration);

		tf=Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Light-webfont.ttf");
		
		inputConfirmpassword = (EditText) findViewById(R.id.inputConfirmpassword);
		inputConfirmpassword.setTypeface(tf);
		inputPassword = (EditText) findViewById(R.id.inputPassword);
		inputPassword.setTypeface(tf);
		inputFname = (EditText) findViewById(R.id.inputFname);
		inputFname.setTypeface(tf);
		inputLname = (EditText) findViewById(R.id.inputLname);
		inputLname.setTypeface(tf);
		inputEmail = (EditText) findViewById(R.id.inputEmail);
		inputEmail.setTypeface(tf);
		inputContact = (EditText) findViewById(R.id.inputContact);
		inputContact.setTypeface(tf);
		classcoll=(EditText) findViewById(R.id.inputclass);
		classcoll.setTypeface(tf);
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;

		// API level 8+
		Account[] accounts = AccountManager.get(Registration.this)
				.getAccounts();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				possibleEmail = account.name;

			}
		}
		inputEmail.setText(possibleEmail);
		Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				fname = inputFname.getText().toString();
				lname = inputLname.getText().toString();
				email = inputEmail.getText().toString();
				classtext= classcoll.getText().toString();
				c = inputContact.getText().toString();
				confirmpassword = inputConfirmpassword.getText().toString();
				password = inputPassword.getText().toString();
				if (fname.matches("")) {
					Toast.makeText(Registration.this,
							"Please enter your First Name", Toast.LENGTH_SHORT)
							.show();

				} else if (lname.matches("")) {
					Toast.makeText(Registration.this,
							"Please enter your Last Name", Toast.LENGTH_SHORT)
							.show();
				} else if (email.matches("")) {
					Toast.makeText(Registration.this,
							"Please enter your E-mail address",
							Toast.LENGTH_SHORT).show();
				} else if (password.matches("")) {
					Toast.makeText(Registration.this,
							"Please enter your password", Toast.LENGTH_SHORT)
							.show();
				} else if (confirmpassword.matches("")) {
					Toast.makeText(Registration.this,
							"Please enter your password again",
							Toast.LENGTH_SHORT).show();
				} else if (c.matches("")) {
					Toast.makeText(Registration.this,
							"Please enter your Contact Number",
							Toast.LENGTH_SHORT).show();
				} else if (!confirmpassword.matches(password)) {
					Toast.makeText(Registration.this, "Passwords do not match",
							Toast.LENGTH_SHORT).show();
				} else if (confirmpassword.length() < 8
						|| password.length() < 8) {
					Toast.makeText(Registration.this,
							"Password should be at least 8 characters long.",
							Toast.LENGTH_SHORT).show();
				} 
				else if(classtext.matches("")){
					
					Toast.makeText(Registration.this,
							"Enter a valid college name/ Class name for VESITians.",
							Toast.LENGTH_SHORT).show();
					
				}
				
				else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Registration.this);
					alertDialogBuilder.setTitle("Confirm E-mail");
					alertDialogBuilder
							.setMessage(
									"Is your E-mail ID "
											+ email
											+ " correct? A verification E-mail will be sent to your E-mail ID.")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, close
											// current activity
											new NetCheck().execute();
										}
									})
							.setNegativeButton("No, edit",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// if this button is clicked, just
											// close
											// the dialog box and do nothing
											dialog.cancel();
										}
									});
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();

				}

			}
		});

	}

	private class NetCheck extends AsyncTask<String, String, Boolean> {
		private ProgressDialog nDialog;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();

			nDialog = new ProgressDialog(Registration.this);
			nDialog.setMessage("Loading..");
			nDialog.setTitle("Checking Network");
			nDialog.setIndeterminate(false);
			nDialog.setCancelable(true);
			nDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

			ConnectivityManager cm = (ConnectivityManager) Registration.this
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				try {
					URL url = new URL("http://www.google.com");
					HttpURLConnection urlc = (HttpURLConnection) url
							.openConnection();
					urlc.setConnectTimeout(2000);
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
				new CreateNewProduct().execute();
			} else {
				nDialog.dismiss();
				Toast.makeText(Registration.this, "check internet connection",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class CreateNewProduct extends AsyncTask<String, Integer, Integer> {
		int success;

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(Registration.this);
			pDialog.setMessage("Registering. Please Wait... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected Integer doInBackground(String... args) {
			String fname = inputFname.getText().toString();
			String lname = inputLname.getText().toString();
			String contact = inputContact.getText().toString();
			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			String classt = classcoll.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("fname", fname));
			params.add(new BasicNameValuePair("class", classt));
			params.add(new BasicNameValuePair("lname", lname));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("contact", contact));

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
						.getDefaultSharedPreferences(Registration.this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("E-mail", email);
				editor.commit();
				Toast.makeText(Registration.this, "Registration successful.",
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(Registration.this, ConfirmEmail.class);
				startActivity(i);
				finish();
			} else if (success == 3) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						Registration.this);

				alertDialogBuilder
						.setMessage("You have already Registered.")
						.setCancelable(false)
						.setPositiveButton("Login",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										Intent intent = new Intent(
												Registration.this,
												GetEmail.class);
										startActivity(intent);
										finish();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			} else {
				Toast.makeText(Registration.this, "Registration unsuccessful.",
						Toast.LENGTH_SHORT).show();
			}

		}
	}
}

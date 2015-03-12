package com.vesit.praxis14;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.vesit.praxis14.R;

public class GridMain extends ActionBarActivity implements OnClickListener {

	private int p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
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
		setContentView(R.layout.grids);
		ImageView image = (ImageView) findViewById(R.id.imgtopnews);
		ImageView image2 = (ImageView) findViewById(R.id.imgevents);
		ImageView image3 = (ImageView) findViewById(R.id.imgregister);
		ImageView image4 = (ImageView) findViewById(R.id.imgworkshops);
		ImageView image5 = (ImageView) findViewById(R.id.imgcoupons);
		ImageView image6 = (ImageView) findViewById(R.id.imgabout);
		ImageView image7 = (ImageView) findViewById(R.id.imgsponsors);
		ImageView image8 = (ImageView) findViewById(R.id.imgcredits);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down_from_up);
		Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_left_to_right);
		Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_right_to_left);

		image.startAnimation(anim);
		image2.startAnimation(anim2);

		image3.startAnimation(anim3);
		image4.startAnimation(anim2);
		image5.startAnimation(anim3);
		image6.startAnimation(anim2);
		image7.startAnimation(anim3);
		image8.startAnimation(anim2);
	}

	public void click(View v) {
		ImageView image = (ImageView) findViewById(R.id.imgtopnews);
		ImageView image2 = (ImageView) findViewById(R.id.imgevents);
		ImageView image3 = (ImageView) findViewById(R.id.imgregister);
		ImageView image4 = (ImageView) findViewById(R.id.imgworkshops);
		ImageView image5 = (ImageView) findViewById(R.id.imgcoupons);
		ImageView image6 = (ImageView) findViewById(R.id.imgabout);
		ImageView image7 = (ImageView) findViewById(R.id.imgsponsors);
		ImageView image8 = (ImageView) findViewById(R.id.imgcredits);

		Animation anim4 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_up_from_down);
		Animation anim5 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_left_to_right_reverse);
		Animation anim6 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_right_to_left_reverese);
		image.startAnimation(anim4);
		image2.startAnimation(anim5);
		image3.startAnimation(anim6);
		image4.startAnimation(anim5);
		image5.startAnimation(anim6);
		image6.startAnimation(anim5);
		image7.startAnimation(anim6);
		image8.startAnimation(anim5);
		if (v.getId() == R.id.imgtopnews)
			p = 0;
		if (v.getId() == R.id.imgevents)
			p = 1;
		if (v.getId() == R.id.imgregister)
			p = 2;
		if (v.getId() == R.id.imgworkshops)
			p = 3;
		if (v.getId() == R.id.imgcoupons)
			p = 4;
		if (v.getId() == R.id.imgabout)
			p = 5;
		if (v.getId() == R.id.imgsponsors)
			p = 6;
		if (v.getId() == R.id.imgcredits)
			p = 7;

		 final Handler handler = new Handler();
		 handler.postDelayed(new Runnable() {
		 @Override
		 public void run() {

		Intent i = new Intent(GridMain.this, NavigationMain.class);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(GridMain.this);

		preferences.edit().putInt("POSITION", p).commit();
		// overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
		startActivity(i);

		finish();
		 }
		 }, 600);

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

	public void exitAppMethod() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

		finish();
	}

	@Override
	public void onBackPressed() {
		exitAppMethod();
	}

}

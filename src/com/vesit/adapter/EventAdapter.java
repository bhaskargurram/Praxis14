package com.vesit.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.vesit.praxis14.R;

public class EventAdapter extends ArrayAdapter<Integer> {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	HashMap<String, String> resultp = new HashMap<String, String>();

	public EventAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Integer getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		TextView eventname, teams, fees, pm_1, pm_2, pm_3;
		TextView description;
		ImageView image;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.events, parent, false);
		// Get the position
		resultp = data.get(position);
		eventname = (TextView) itemView.findViewById(R.id.eventname);
		image = (ImageView) itemView.findViewById(R.id.evimg);
		description = (TextView) itemView.findViewById(R.id.descr);
		teams = (TextView) itemView.findViewById(R.id.teams);
		fees = (TextView) itemView.findViewById(R.id.fees);
		pm_1 = (TextView) itemView.findViewById(R.id.pm_1);
		pm_2 = (TextView) itemView.findViewById(R.id.pm_2);
		pm_3 = (TextView) itemView.findViewById(R.id.pm_3);

		// Log.e("check", resultp.get("image").toString());
		eventname.setText(resultp.get("eventname"));
		description.setText(resultp.get("description"));
		teams.setText(resultp.get("teams"));
		fees.setText(resultp.get("fees"));
		pm_1.setText(resultp.get("pm_1"));
		pm_2.setText(resultp.get("pm_2"));
		pm_3.setText(resultp.get("pm_3"));
		if (resultp.get("image") == "") {
			eventname.setVisibility(View.VISIBLE);
			eventname.setText(resultp.get("eventname"));
			description.setVisibility(View.VISIBLE);
			description.setText(resultp.get("description"));
			teams.setVisibility(View.VISIBLE);
			teams.setText(resultp.get("teams"));
			fees.setVisibility(View.VISIBLE);
			fees.setText(resultp.get("fees"));
			pm_1.setVisibility(View.VISIBLE);
			pm_1.setText(resultp.get("pm_1"));
			pm_2.setVisibility(View.VISIBLE);
			pm_2.setText(resultp.get("pm_2"));
			pm_3.setVisibility(View.VISIBLE);
			pm_3.setText(resultp.get("pm_3"));

		}
		if (resultp.get("image") != "") {
			eventname.setVisibility(View.VISIBLE);
			eventname.setText(resultp.get("eventname"));
			description.setVisibility(View.VISIBLE);
			description.setText(resultp.get("description"));
			teams.setVisibility(View.VISIBLE);
			teams.setText(resultp.get("teams"));
			fees.setVisibility(View.VISIBLE);
			fees.setText(resultp.get("fees"));
			pm_1.setVisibility(View.VISIBLE);
			pm_1.setText(resultp.get("pm_1"));
			pm_2.setVisibility(View.VISIBLE);
			pm_2.setText(resultp.get("pm_2"));
			pm_3.setVisibility(View.VISIBLE);
			pm_3.setText(resultp.get("pm_3"));
			image.setVisibility(View.VISIBLE);
			image.setScaleType(ImageView.ScaleType.FIT_XY);
			image.setAdjustViewBounds(true);
			Ion.with(image).load(resultp.get("image"));

		}
		return itemView;
	}
}

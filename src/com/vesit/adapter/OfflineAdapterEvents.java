
package com.vesit.adapter;

import com.vesit.praxis14.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;

public class OfflineAdapterEvents extends ArrayAdapter<Integer> {
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public OfflineAdapterEvents(Context context,
			ArrayList<HashMap<String, String>> stuff) {
		this.context = context;

		data = stuff;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView news, title, teams, fees, pm_1, pm_2, pm_3;
		ImageView image;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.events, parent, false);
		resultp = data.get(position);
		title = (TextView) itemView.findViewById(R.id.eventname);
		image = (ImageView) itemView.findViewById(R.id.evimg);
		news = (TextView) itemView.findViewById(R.id.descr);
		teams = (TextView) itemView.findViewById(R.id.teams);
		fees = (TextView) itemView.findViewById(R.id.fees);
		pm_1 = (TextView) itemView.findViewById(R.id.pm_1);
		pm_2 = (TextView) itemView.findViewById(R.id.pm_2);
		pm_3 = (TextView) itemView.findViewById(R.id.pm_3);
		String filename;
		if (resultp.get("image").length() > 34)
			filename = resultp.get("image").substring(34);
		else
			filename = "";
		
		Bitmap bmp = BitmapFactory.decodeFile("sdcard/.Praxis14/" + filename);
		title.setVisibility(View.VISIBLE);
		title.setText(resultp.get("eventname"));
		news.setVisibility(View.VISIBLE);
		news.setText(resultp.get("description"));
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
		image.setImageBitmap(bmp);

		return itemView;
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

}

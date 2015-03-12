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

public class TopNewAdapter extends ArrayAdapter<Integer> {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	HashMap<String, String> resultp = new HashMap<String, String>();

	public TopNewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView news, title;
		ImageView image;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.topnews, parent, false);
		// Get the position
		resultp = data.get(position);
		title = (TextView) itemView.findViewById(R.id.title);
		image = (ImageView) itemView.findViewById(R.id.topnewsimg);
		news = (TextView) itemView.findViewById(R.id.topnewstext);
		if (resultp.get("image") == "") {
			title.setVisibility(View.VISIBLE);
			title.setText(resultp.get("title"));
			news.setVisibility(View.VISIBLE);
			news.setText(resultp.get("news"));

		}
		if (resultp.get("image") != "") {
			title.setVisibility(View.VISIBLE);
			title.setText(resultp.get("title"));
			news.setVisibility(View.VISIBLE);
			news.setText(resultp.get("news"));
			image.setVisibility(View.VISIBLE);
			Ion.with(image).load(resultp.get("image"));

		}

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
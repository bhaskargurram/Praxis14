package com.vesit.adapter;

import com.vesit.praxis14.R;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;

public class CreditAdapter extends ArrayAdapter<Integer> {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	HashMap<String, String> resultp = new HashMap<String, String>();

	public CreditAdapter(Context context,
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

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = null;

		if (position == 0 || position == 1||position==2) {
			TextView name;
			TextView posit;
			ImageView image;
			itemView = inflater.inflate(R.layout.credits, parent, false);
			// Get the position
			resultp = data.get(position);
			name = (TextView) itemView.findViewById(R.id.name);
			image = (ImageView) itemView.findViewById(R.id.imagecredit);
			posit = (TextView) itemView.findViewById(R.id.position);

			// Log.e("check", resultp.get("image").toString());
			name.setText(resultp.get("name"));
			image.setImageResource(Integer.parseInt(resultp.get("image")));
			posit.setText(resultp.get("position"));
		} else {
			itemView = inflater.inflate(R.layout.credits_core, parent, false);
			TextView name1, name2, post1, post2;
			ImageView image1, image2;
			name1 = (TextView) itemView.findViewById(R.id.name1);
			name2 = (TextView) itemView.findViewById(R.id.name2);
			post1 = (TextView) itemView.findViewById(R.id.post1);
			post2 = (TextView) itemView.findViewById(R.id.post2);
			image1 = (ImageView) itemView.findViewById(R.id.image1);
			image2 = (ImageView) itemView.findViewById(R.id.image2);
			resultp = data.get(position);
			name1.setText(resultp.get("name1"));
			name2.setText(resultp.get("name2"));
			post1.setText(resultp.get("post1"));
			post2.setText(resultp.get("post2"));
			image1.setImageResource(Integer.parseInt(resultp.get("image1")));
			image2.setImageResource(Integer.parseInt(resultp.get("image2")));

		}
		return itemView;

	}
}

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
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.nhaarman.listviewanimations.ArrayAdapter;

public class OfflineAdapter extends ArrayAdapter<Integer> {
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public OfflineAdapter(Context context,
			ArrayList<HashMap<String, String>> stuff) {
		this.context = context;

		data = stuff;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView news, title;
		ImageView image;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.topnews, parent, false);
		resultp = data.get(position);
		title = (TextView) itemView.findViewById(R.id.title);
		image = (ImageView) itemView.findViewById(R.id.topnewsimg);
		news = (TextView) itemView.findViewById(R.id.topnewstext);

		String filename;
		if (resultp.get("image").length() > 34)
			filename = resultp.get("image").substring(34);
		else
			filename = "";
		Bitmap bmp = BitmapFactory.decodeFile("sdcard/.Praxis14/" + filename);

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
			image.setScaleType(ImageView.ScaleType.FIT_XY);
			image.setAdjustViewBounds(true);
			image.setImageBitmap(bmp);

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

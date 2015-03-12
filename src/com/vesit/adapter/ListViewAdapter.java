package com.vesit.adapter;

import com.vesit.fragments.CouponFragment;
import com.vesit.praxis14.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView eventname;
		TextView uniqueid;
		TextView status;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.couponview, parent, false);
		// Get the position
		resultp = data.get(position);

		eventname = (TextView) itemView.findViewById(R.id.eventname);
		uniqueid = (TextView) itemView.findViewById(R.id.uniqueid);
		status = (TextView) itemView.findViewById(R.id.status);

		eventname.setText(resultp.get("eventname"));
		uniqueid.setText(resultp.get("uniqueid"));
		status.setText(resultp.get("status"));

		return itemView;
	}
}

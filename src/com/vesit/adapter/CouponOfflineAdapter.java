package com.vesit.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.vesit.praxis14.R;

public class CouponOfflineAdapter extends ArrayAdapter<Integer> {
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public CouponOfflineAdapter(Context context,
			ArrayList<HashMap<String, String>> couponList) {
		this.context = context;

		data = couponList;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView regid, status, eventname, uniqueid;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.couponview, parent, false);
		resultp = data.get(position);
		
		eventname = (TextView) itemView.findViewById(R.id.eventname);
		uniqueid = (TextView) itemView.findViewById(R.id.uniqueid);
		status = (TextView) itemView.findViewById(R.id.status);

	
		eventname.setText(resultp.get("eventname"));
		status.setText(resultp.get("status"));
		uniqueid.setText(resultp.get("uniqueid"));
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

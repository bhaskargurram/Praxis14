package com.vesit.fragments;

import com.vesit.praxis14.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class About extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.about, container, false);

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
TextView tv=(TextView)rootView.findViewById(R.id.textcollege);
TextView tv2=(TextView)rootView.findViewById(R.id.linkcollege);
TextView tv3=(TextView)rootView.findViewById(R.id.linkpraxis);

    tv2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				    Uri.parse("http://vesit.edu/"));
				startActivity(intent);	
		}
	});
 tv3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
				    Uri.parse("http://praxis-14.vesit.edu/"));
				startActivity(intent);	
		}
	});
		return rootView;
	}
}

package com.facebook.stream;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;

public class SelectionFragment extends Fragment {
	private static final String TAG = "SelectionFragment";
	Button timeline;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.selection, container, false);
		timeline=(Button) view.findViewById(R.id.timeline);
		timeline.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(),App.class);
				startActivity(i);
			}
		});
		return view;
	}

}
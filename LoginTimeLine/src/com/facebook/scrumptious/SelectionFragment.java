package com.facebook.scrumptious;

import org.json.JSONObject;


import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

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
				doRequest();
			}
		});
		return view;
	}
	
	
	private void doRequest() {
		
		Session session = Session.getActiveSession();
		Request.Callback callback = new Request.Callback() {
			public void onCompleted(Response response) {
				JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
				
				Log.d("data", ""+StreamRenderer.render(graphResponse));

			}
		};

		Request request = new Request(session, "me/home", null,
				HttpMethod.GET, callback);

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
	}
	

}
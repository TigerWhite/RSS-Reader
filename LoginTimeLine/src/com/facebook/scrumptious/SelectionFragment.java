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
//import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

public class SelectionFragment extends Fragment {
	private static final String TAG = "SelectionFragment";
	Button timeline;
	Button share;
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
				Log.d("lenglist", ""+StreamRenderer.getLengList());
				for(int i=0;i<StreamRenderer.getLengList();i++){
					Log.d("content", "" + i + "  "
							+ StreamRenderer.getList(i).getMessage() + " -- "
							+ StreamRenderer.getList(i).getDescription());
					if(StreamRenderer.getList(i).getLengComment()!=0){
						Log.d("lengcomment", ""+StreamRenderer.getList(i).getLengComment());
						for(int j=0;j<StreamRenderer.getList(i).getLengComment();j++) {
							Log.d("commet",""+j+"  "+StreamRenderer.getList(i).getComment(j).getMessage());
						}
					}
						
				}
			
			}
		});
		share=(Button) view.findViewById(R.id.share);
		share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getActivity(),Share.class);
				startActivity(i);
			}
		});
		return view;
	}
	
	
	private void doRequest() {
		Log.d("data", "start render");
		Session session = Session.getActiveSession();
		Request.Callback callback = new Request.Callback() {
			public void onCompleted(Response response) {
				JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
				
				StreamRenderer.render(graphResponse);
				Log.d("data",graphResponse.toString());
			}
		};

		Request request = new Request(session, "me/home", null,
				HttpMethod.GET, callback);

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
		
	}
	

}
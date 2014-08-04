package com.ecs.android.sample.oauth2;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainScreenComment extends Activity {

	private SharedPreferences prefs;
	private TextView txtApiResponse;
	ScrollView mScrollView;
	private OAuth2Helper oAuth2Helper;	
	private JSONObject jsonObject;
	private ArrayList<MyItemComment> myItemCommentList;

	
	

	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		oAuth2Helper = new OAuth2Helper(this.prefs);		

		this.txtApiResponse = (TextView) findViewById(R.id.response_code);
		this.mScrollView = (ScrollView) findViewById(R.id.SCROLLER_ID);
		//this.txtApiResponse.setText(R.string.waiting_for_data);
		this.txtApiResponse.setText("Done");

		performApiCall();		
	}
    
    /**
     * Performs an authorized API call.
     */
	private void performApiCall() {
		new ApiCallExecutor().execute();
	}
	
	//////////////////////////////////////////////////////////////////
	private ArrayList<MyItemComment> getmyItemCommentList(String jString) {
		ArrayList<MyItemComment> myItemCommentList = new ArrayList<MyItemComment>();
		try {
			jsonObject = new JSONObject(jString);

			JSONArray jaItems = jsonObject.getJSONArray("items");

			for (int i = 0; i < jaItems.length(); i++) {
				MyItemComment myItemComment = new MyItemComment();

				JSONObject object = jaItems.getJSONObject(i);
				JSONObject jActor = object.getJSONObject("actor");
				JSONObject jObject = object.getJSONObject("object");

				JSONObject jPlusoners = object.getJSONObject("plusoners");
				JSONObject jImage = jActor.getJSONObject("image");

				JSONArray jaInReplyTo = object.getJSONArray("inReplyTo");

				myItemComment.setKind(jsonObject.getString("kind").toString());
				myItemComment.setEtag(jsonObject.getString("etag").toString());
				myItemComment.setTitle(jsonObject.getString("title").toString());

				// Trong Items
				myItemComment.setKind_items(object.getString("kind").toString());
				myItemComment.setEtag_items(object.getString("etag").toString());
				myItemComment.setVerb_items(object.getString("verb").toString());
				myItemComment.setId_items(object.getString("id").toString());
				myItemComment.setPublished_items(object.getString("published")
						.toString());
				myItemComment.setUpdated_items(object.getString("updated").toString());

				// Trong Actor
				myItemComment.setId_actor(jActor.getString("id").toString());
				myItemComment.setDisplayName_actor(jActor.getString("displayName")
						.toString());
				myItemComment.setUrl_actor(jActor.getString("url").toString());
				myItemComment.setUrl_image_actor(jImage.getString("url"));

				// Trong Object
				myItemComment.setObjectType_object(jObject.getString("objectType")
						.toString());
				myItemComment.setContent_object(jObject.getString("content")
						.toString());
				//
				myItemComment.setSelfLink(object.getString("selfLink").toString());

				// Doan cuoi

//				JSONObject jInReplyToItem = jaInReplyTo.getJSONObject(i);
//				myItemComment.setId_inReplyTo(jInReplyToItem.getString("id")
//						.toString());
//				myItemComment.setUrl_inReplyTo(jInReplyToItem.getString("url")
//						.toString());
				
				myItemComment.setTotalItems_plusoners(jPlusoners.getString(
						"totalItems").toString());
				
				myItemCommentList.add(myItemComment);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myItemCommentList;
	}

	//////////////////////////////////////////////////////////////////
	
	private class ApiCallExecutor extends AsyncTask<Uri, Void, String> {

		String apiResponse = null;
		
		@Override
		protected String doInBackground(Uri...params) {
			
			try {
				apiResponse = oAuth2Helper.executeApiCall();
				Log.i(Constants.TAG, "Received response from API : " + apiResponse);
			} catch (Exception ex) {
				ex.printStackTrace();
				apiResponse=ex.getMessage();
			}
            return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);		
			myItemCommentList = getmyItemCommentList(apiResponse);
			System.out.println("Your public post: ");
			for (MyItemComment news : myItemCommentList) {
				System.out.println(news);
			}

		}

	}
	
}
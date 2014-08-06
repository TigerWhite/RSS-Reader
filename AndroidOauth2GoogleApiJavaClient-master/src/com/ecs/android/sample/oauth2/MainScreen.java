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

public class MainScreen extends Activity {

	private SharedPreferences prefs;
	private TextView txtApiResponse;
	ScrollView mScrollView;
	private OAuth2Helper oAuth2Helper;
	private JSONObject jsonObject;
	private ArrayList<MyItem> myItemList;
	//private ArrayList<MyItemComment> myItemListComment;

	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		oAuth2Helper = new OAuth2Helper(this.prefs);		

		this.txtApiResponse = (TextView) findViewById(R.id.response_code);
		this.mScrollView = (ScrollView) findViewById(R.id.SCROLLER_ID);
		this.txtApiResponse.setText(R.string.waiting_for_data);
		//this.txtApiResponse.setText("Done");

		performApiCall();		
	}
    
    /**
     * Performs an authorized API call.
     */
	private void performApiCall() {
		new ApiCallExecutor().execute();
	}
	
	//////////////////////////////////////////////////////////////////
	private ArrayList<MyItem> getMyItemList(String jString) {
		ArrayList<MyItem> myItemList = new ArrayList<MyItem>();
		try {
			jsonObject = new JSONObject(jString);

			JSONArray jaItems = jsonObject.getJSONArray("items");
			System.out.println("Total post: " + jaItems.length());
			for (int i = 0; i < jaItems.length(); i++) {
				MyItem myItem = new MyItem();

				JSONObject object = jaItems.getJSONObject(i);
				JSONObject jActor = object.getJSONObject("actor");
				//JSONObject jProvider = object.getJSONObject("provider");
				JSONObject jObject = object.getJSONObject("object");
				//JSONObject jAccess = object.getJSONObject("access");

				JSONObject jImage = jActor.getJSONObject("image");
				JSONObject jReplies = jObject.getJSONObject("replies");
				JSONObject jPlusoners = jObject.getJSONObject("plusoners");
				JSONObject jReshares = jObject.getJSONObject("resharers");
				//JSONArray jItemsAccess = jAccess.getJSONArray("items");

				myItem.setKind(jsonObject.getString("kind").toString());
				myItem.setEtag(jsonObject.getString("etag").toString());
				myItem.setTitle(jsonObject.getString("title").toString());
				myItem.setUpdated(jsonObject.getString("updated").toString());

				// Trong Items
				//myItem.setKind_items(object.getString("kind").toString());
				//myItem.setEtag_items(object.getString("etag").toString());
				myItem.setTitle_items(object.getString("title").toString());
				//myItem.setPublished_items(object.getString("published").toString());
				myItem.setUpdated_items(object.getString("updated").toString());
				myItem.setId_items(object.getString("id").toString());
				myItem.setUrl_items(object.getString("url").toString());

				// Trong Actor
				myItem.setId_actor(jActor.getString("id").toString());
				myItem.setDisplayName_actor(jActor.getString("displayName")
						.toString());
				myItem.setUrl_actor(jActor.getString("url").toString());
				myItem.setUrl_image_actor(jImage.getString("url"));

				//myItem.setVerb_items(object.getString("verb").toString());

				// Trong Object
				// Object ngoai
				//myItem.setObjectType_object(jObject.getString("objectType")
//						.toString());
				//myItem.setContent_object(jObject.getString("content").toString());
				//myItem.setUrl_object(jObject.getString("url").toString());
				// Trong Replies
				myItem.setTotalItems_replies_object(jReplies.getString(
						"totalItems").toString());
				myItem.setSelfLink_replies_object(jReplies
						.getString("selfLink").toString());
				// Trong Plusoners
				myItem.setTotalItems_plusoners_object(jPlusoners.getString(
						"totalItems").toString());
				myItem.setSelfLink_plusoners_object(jPlusoners.getString(
						"selfLink").toString());
				// Trong Resharers
				myItem.setTotalItems_resharers_object(jReshares.getString(
						"totalItems").toString());
				myItem.setSelfLink_resharers_object(jReshares.getString(
						"selfLink").toString());
				//Trong Attachments
				if (jObject.has("attachments")) {
					//System.out.println("Exist");
					JSONArray jAttchments = jObject.getJSONArray("attachments");
					for (int j = 0; j < jAttchments.length(); j++) {
						JSONObject jAttachmentsObject = jAttchments.getJSONObject(j);
						JSONObject jImageAttachments = jAttachmentsObject.getJSONObject("image");
						//JSONObject jFullImageAttachments = jAttachmentsObject.getJSONObject("fullImage");
						//myItem.setObjectType_attachments(jAttachmentsObject.getString("objectType").toString());
						myItem.setDisplayName_attachments(jAttachmentsObject.getString("displayName").toString());
						myItem.setUrl_attachments(jAttachmentsObject.getString("url").toString());
						// Image
						myItem.setUrl_image_attachments(jImageAttachments.getString("url").toString());
						//myItem.setType_image_attachments(jImageAttachments.getString("type").toString());
						//myItem.setHeight_image_attachments(jImageAttachments.getString("height").toString());
						//myItem.setWidth_image_attachments(jImageAttachments.getString("width").toString());
						// FullImage
						//myItem.setUrl_fullImage_attachments(jFullImageAttachments.getString("url").toString());
						//myItem.setType_fullImage_attachments(jFullImageAttachments.getString("type").toString());
					}
				}
				
				myItemList.add(myItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myItemList;
	}

	//////////////////////////////////////////////////////////////////
//	private ArrayList<MyItemComment> getMyItemListComment(String jString) {
//		ArrayList<MyItemComment> myItemListComment = new ArrayList<MyItemComment>();
//		try {
//			jsonObject = new JSONObject(jString);
//
//			JSONArray jaItems = jsonObject.getJSONArray("items");
//
//			for (int i = 0; i < jaItems.length(); i++) {
//				MyItemComment myItemComment = new MyItemComment();
//
//				JSONObject object = jaItems.getJSONObject(i);
//				JSONObject jActor = object.getJSONObject("actor");
//				JSONObject jObject = object.getJSONObject("object");
//
//				JSONObject jPlusoners = object.getJSONObject("plusoners");
//				JSONObject jImage = jActor.getJSONObject("image");
//
//				//JSONArray jaInReplyTo = object.getJSONArray("inReplyTo");
//
//				myItemComment.setKind(jsonObject.getString("kind").toString());
//				myItemComment.setEtag(jsonObject.getString("etag").toString());
//				myItemComment.setTitle(jsonObject.getString("title").toString());
//
//				// Trong Items
//				myItemComment.setKind_items(object.getString("kind").toString());
//				myItemComment.setEtag_items(object.getString("etag").toString());
//				myItemComment.setVerb_items(object.getString("verb").toString());
//				myItemComment.setId_items(object.getString("id").toString());
//				myItemComment.setPublished_items(object.getString("published")
//						.toString());
//				myItemComment.setUpdated_items(object.getString("updated").toString());
//
//				// Trong Actor
//				myItemComment.setId_actor(jActor.getString("id").toString());
//				myItemComment.setDisplayName_actor(jActor.getString("displayName")
//						.toString());
//				myItemComment.setUrl_actor(jActor.getString("url").toString());
//				myItemComment.setUrl_image_actor(jImage.getString("url"));
//
//				// Trong Object
//				myItemComment.setObjectType_object(jObject.getString("objectType")
//						.toString());
//				myItemComment.setContent_object(jObject.getString("content")
//						.toString());
//				//
//				myItemComment.setSelfLink(object.getString("selfLink").toString());
//
//				// Doan cuoi
//
////				JSONObject jInReplyToItem = jaInReplyTo.getJSONObject(i);
////				myItemComment.setId_inReplyTo(jInReplyToItem.getString("id")
////						.toString());
////				myItemComment.setUrl_inReplyTo(jInReplyToItem.getString("url")
////						.toString());
//				
//				myItemComment.setTotalItems_plusoners(jPlusoners.getString(
//						"totalItems").toString());
//				
//				myItemListComment.add(myItemComment);
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return myItemListComment;
//	}
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
			txtApiResponse.setText(apiResponse);
			myItemList = getMyItemList(apiResponse);
			//myItemListComment = getMyItemListComment(apiResponse);
			//txtApiResponse.setText(myItemList);
			System.out.println("Your public post: ");
			for (MyItem news : myItemList) {
				System.out.println(news);
				System.out.println(news.getId_items());
			}
//			for (MyItemComment comments : myItemListComment) {
//				System.out.println(comments);
//			}

		}

	}
	
}
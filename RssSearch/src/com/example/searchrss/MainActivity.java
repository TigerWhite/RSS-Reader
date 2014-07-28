package com.example.searchrss;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.androidbegin.jsonparsetutorial.R;

public class MainActivity extends Activity {
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	static String URL = "url";
	static String TITLE = "title";
	static String DESCRIPTION = "description";
	
	private EditText searchInput;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listview_main);
		
		searchInput = (EditText) findViewById(R.id.searchInput);
		searchInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				// Execute DownloadJSON AsyncTask
				new DownloadJSON().execute();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
				
//		searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if(actionId == EditorInfo.IME_ACTION_SEARCH){
//
//					// Execute DownloadJSON AsyncTask
//					new DownloadJSON().execute();
//				}
//				return false;
//			}
//		});
		
	}
//public void searchRSS()
	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			// Create a progressdialog
//			mProgressDialog = new ProgressDialog(MainActivity.this);
//			// Set progressdialog title
//			mProgressDialog.setTitle("Android Search RSS");
//			// Set progressdialog message
//			mProgressDialog.setMessage("Loading application...");
//			mProgressDialog.setIndeterminate(false);
//			// Show progressdialog
//			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			
			String temp = searchInput.getText().toString();
			String searchCode = temp.replaceAll("\\s", "%20");
			
			
			// Retrieve JSON Objects from the given URL address
			Log.d("tag8", "tag8 " + temp.replaceAll("\\s", "%20"));
			jsonobject = JSONfunctions
					.getJSONfromURL("https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q="+searchCode.toString()+"&output=json");

			try {
		JSONObject jsonobject2 = jsonobject.getJSONObject("responseData");
				jsonarray = jsonobject2.getJSONArray("entries");

				Log.d("tag5", "tag5 " + jsonarray.length());
				for (int i = 0; i < jsonarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);
					// Retrive JSON Objects
					map.put("url", jsonobject.getString("url"));
					Log.d("tag4", "tag4 " + jsonobject.getString("url"));
					map.put("title", Jsoup.parse(jsonobject.getString("title")).text());
					map.put("description", Jsoup.parse(jsonobject.getString("contentSnippet")).text());
//					// Set the JSON Objects into the array
					arraylist.add(map);
				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(MainActivity.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
//			mProgressDialog.dismiss();
		}
	}
}
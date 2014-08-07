package ltt.intership.searchrss;

import java.util.ArrayList;
import java.util.HashMap;

import ltt.intership.R;
import ltt.intership.androidlayout.MetroActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Searchrss
 * if intent.putInt = 1, save database
 * else intent.putExtra put a RSS url
 * @author Hoang Cong Thang
 * @param a			Description of a
 * @param b			Description of b
 * @return			Description of c
 */
public class SearchActivity extends Activity {
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
	protected static final int RESULT_SPEECH = 1;
	public String searchKey;
	public int flag;
	
	private EditText searchInput;
	private ImageButton btnSpeak;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listview_main);
		
		Intent i = getIntent();
		flag = Integer.parseInt(i.getStringExtra("flag"));
		searchInput = (EditText) findViewById(R.id.searchInput);
		btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
		
//		searchInput.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				// Execute DownloadJSON AsyncTask
//				new DownloadJSON().execute();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//			}
//		});
				
		searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionId == EditorInfo.IME_ACTION_SEARCH){

					// Execute DownloadJSON AsyncTask
					new DownloadJSON().execute();
				}
				return false;
			}
		});
		btnSpeak.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View v) {
 
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
 
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
 
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    searchInput.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
	}
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	 
	        switch (requestCode) {
	        case RESULT_SPEECH: {
	            if (resultCode == RESULT_OK && null != data) {
	 
	                ArrayList<String> text = data
	                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	 
	                searchInput.setText(text.get(0));
	                new DownloadJSON().execute();
	            }
	            break;
	        }
	        }
	 }
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MetroActivity.class);
		startActivity(intent);
		finish();
	}
	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<HashMap<String, String>, HashMap<String, String>, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			// Create a progressdialog
//			mProgressDialog = new ProgressDialog(SearchActivity.this);
//			// Set progressdialog title
//			mProgressDialog.setTitle("Android Search RSS");
//			// Set progressdialog message
//			mProgressDialog.setMessage("Loading application...");
//			mProgressDialog.setIndeterminate(false);
//			// Show progressdialog
//			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(HashMap<String, String>... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			
			searchKey = searchInput.getText().toString();
			String searchCode = searchKey.replaceAll("\\s", "%20");
			
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions
					.getJSONfromURL("https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q="+searchCode.toString()+"&output=json");

			try {
		JSONObject jsonobject2 = jsonobject.getJSONObject("responseData");
				jsonarray = jsonobject2.getJSONArray("entries");

				for (int i = 0; i < jsonarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);
					// Retrive JSON Objects
					map.put("url", jsonobject.getString("url"));
					Log.d("tag", "budn: "+ jsonobject.getString("url"));
					map.put("title", Jsoup.parse(jsonobject.getString("title")).text());
//					map.put("description", Jsoup.parse(jsonobject.getString("contentSnippet")).text());
					// Set the JSON Objects into the array
					SystemClock.sleep(100);
					publishProgress(map);
				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
		@Override
		 protected void onProgressUpdate(HashMap<String, String>... params) {
		 // TODO Auto-generated method stub
		 super.onProgressUpdate(params);
		 
		 arraylist.add(params[0]);
		 
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(SearchActivity.this, arraylist, searchKey, flag);
			
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
		 }
		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
//			listview = (ListView) findViewById(R.id.listview);
//			// Pass the results into ListViewAdapter.java
//			adapter = new ListViewAdapter(SearchActivity.this, arraylist, searchKey, flag);
//			
//			// Set the adapter to the ListView
//			listview.setAdapter(adapter);
			// Close the progressdialog
//			mProgressDialog.dismiss();
		}
	}
}
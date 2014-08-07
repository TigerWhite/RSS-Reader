package com.example.xmlparser2;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmlparser.R;
import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.SourceHelper;
import com.ltt.util.WebAccessHandler;

public class MainActivity extends Activity {

	private LinearLayout ll;
	private TextView tv;
	private Button btnGet;
	private Spinner spinner1;
	private Spinner spinner2;
	private SourceHelper sh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		ll = (LinearLayout) findViewById(R.id.linearLayout1);
		tv = new TextView(this);

		btnGet = (Button) findViewById(R.id.btnGet);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		btnGet.setOnClickListener(btnGetImgListener);
		sh = new SourceHelper(this);

		// Create an ArrayAdapter using a default spinner1 layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);

		// Set content to the adapter
		for (String item : sh.getCategories()) {
			adapter.add(item);
		}

		for (String item : sh.getItems()) {
			adapter2.add(item);
		}
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner1
		spinner1.setAdapter(adapter);
		spinner2.setAdapter(adapter2);

		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				String itemName = (String) parent.getItemAtPosition(pos);

				sh.setItemsFromCategory(itemName);
				// Set content to the adapter
				adapter2.clear();
				for (String item : sh.getItems()) {
					adapter2.add(item);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Toast.makeText(getApplicationContext(), "nothing selected",
						Toast.LENGTH_LONG).show();

			}
		});

	}

	private OnClickListener btnGetImgListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			ll.removeAllViews();

			DownloadMultiSiteTask dtask = new DownloadMultiSiteTask(2);
			dtask.execute(sh.getItems());

		}
	};

	// in kq tu list lay duoc
	private void printData(ArrayList<RssItemInfo> cartList)
			throws NullPointerException {

		for (RssItemInfo RssItemInfo : cartList) {
			tv = new TextView(this);
			tv.setText("TIEUDE : " + RssItemInfo.getTitle());
			ll.addView(tv);

			tv = new TextView(this);
			tv.setText("MOTA : " + Html.fromHtml(RssItemInfo.getDescription()));
			ll.addView(tv);

			tv = new TextView(this);
			tv.setText("LINK : " + RssItemInfo.getLink());
			ll.addView(tv);

			tv = new TextView(this);
			tv.setText("THUMB : " + RssItemInfo.getThumbnail());
			ll.addView(tv);

			tv = new TextView(this);
			tv.setText("DATE : " + RssItemInfo.getPubDate());
			ll.addView(tv);

			tv = new TextView(this);
			tv.setText("DATE FORMAT: " + RssItemInfo.getPubDateFormat());
			ll.addView(tv);

			tv = new TextView(this);
			tv.setText("---");
			ll.addView(tv);
		}

	}

	/**
	 * Lop download nhieu trang cung 1 luc
	 * 
	 * @author Nguyen Duc Hieu
	 * 
	 */
	class DownloadMultiSiteTask extends
			AsyncTask<String, ArrayList<RssItemInfo>, Integer> {
		private WebAccessHandler webhandle = new WebAccessHandler();
		private int sites = 0;

		int testNum = 0;

		public DownloadMultiSiteTask() {
			super();
		}

		public DownloadMultiSiteTask(int testNum) {
			this.testNum = testNum;
		}

		@Override
		protected void onProgressUpdate(ArrayList<RssItemInfo>... values) {
			try {
				printData(values[0]);
			} catch (NullPointerException e) {
				return;
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(String... params) {
			Log.v("doInBackground", "doing download of image");
			for (String link : params) {
				// Thuc hien phan tich XML
				InputStream inStream = null;
				inStream = webhandle.getStreamFromLink(link);

				if (inStream == null) {
					continue;
				}
				publishProgress(new RssParser().parseXML(inStream, testNum));
				sites++;
			}
			return sites;
		}

		@Override
		protected void onPostExecute(Integer result) {
			Log.i("download task", "get from " + result + " sites");
		}

		
	}
}
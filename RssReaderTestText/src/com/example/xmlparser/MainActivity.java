package com.example.xmlparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
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

import com.ltt.rssreader.NewsSourceInfo;
import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.SourceHelper;
import com.ltt.util.WebAccessHandler;

public class MainActivity extends Activity {

	private ArrayList<RssItemInfo> listData;
	private NewsSourceInfo ns;
	private LinearLayout ll;
	private TextView tv;
	// private WebView wv;
	private Button btnInfo;
	private Button btnOk;
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

		btnInfo = (Button) findViewById(R.id.btnInfo);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnGet = (Button) findViewById(R.id.btnGet);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		btnInfo.setOnClickListener(btnGetImgListener);
		btnOk.setOnClickListener(btnGetImgListener);
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
			WebAccessHandler webhandle = new WebAccessHandler();
			String link = spinner2.getSelectedItem().toString();

			// Thuc hien phan tich XML
			InputStream inStream = null;

			try {
				inStream = webhandle.getStreamFromUrl(link);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
				Toast.makeText(MainActivity.this, "loi phuong thuc",
						Toast.LENGTH_SHORT).show();

			} catch (IOException e1) {
				e1.printStackTrace();
				Toast.makeText(MainActivity.this, "loi ket noi",
						Toast.LENGTH_SHORT).show();
			}

			if (inStream == null) {
				try {
					inStream = webhandle.fetchURL(link);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "link loi",
							Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "loi io khi fetch",
							Toast.LENGTH_SHORT).show();
				}
			}

			if (inStream == null)
				return;
			
			switch (v.getId()){
			case R.id.btnInfo:
				ns = new RssParser().getSourceInfo(inStream);
				ll.removeAllViews();
				printSource(ns);
				break;
			case R.id.btnOk:
				listData = new RssParser().parseXML(inStream, 3);
				ll.removeAllViews();
				try {
					printData(listData);			
				} catch (NullPointerException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT)
							.show();
					return;
				}			
				break;
			case R.id.btnGet:
				try {
					printContent(inStream);
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "cannot display", Toast.LENGTH_SHORT)
					.show();
					return;
				}
			}
			
			//close connection
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			// wv = new WebView(this);
			// wv.setLayoutParams(new
			// WebView.LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT, 0, 0));
			// wv.loadDataWithBaseURL(RssItemInfo.getQuantity(),
			// RssItemInfo.getItemNumber(), "text/html", "utf-8", null);
			// ll.addView(wv);

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

	protected void printSource(NewsSourceInfo ns2) {
		tv = new TextView(this);
		tv.setText("Ten bao: " + ns.getTitle());
		ll.addView(tv);
		
		tv = new TextView(this);
		tv.setText("Mo ta: " + ns.getDescription());
		ll.addView(tv);
		
		tv = new TextView(this);
		tv.setText("link: " + ns.getLink());
		ll.addView(tv);
		
		tv = new TextView(this);
		tv.setText("thumb: " + ns.getThumbnail());
		ll.addView(tv);
		
	}

	private void printContent(InputStream is) throws IOException {
		String line;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));

		while ((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		tv = new TextView(this);
		tv.setText(sb.toString());
		ll.addView(tv);

	}

}
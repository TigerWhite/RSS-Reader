package com.example.rssreadertest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.WebAccessHandler;

public class MainActivity extends Activity {

	private Spinner spinner;
	private Button btnOk;
	private ListView listViewItem;
	private ArrayList<RssItemInfo> listData;
	private OnClickListener btnGetImgListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			WebAccessHandler webhandle = new WebAccessHandler();
			String link = spinner.getSelectedItem().toString();
			
			// Thuc hien phan tich XML
			InputStream inStream = null;
			
			try {
				inStream = webhandle.getStreamFromUrl(link);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
				Toast.makeText(MainActivity.this, "loi phuong thuc",
						Toast.LENGTH_SHORT).show();
				return;
	
			} catch (IOException e1) {
				e1.printStackTrace();
				Toast.makeText(MainActivity.this, "loi ket noi",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (inStream == null){
				try {
					inStream = webhandle.fetchURL(link);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "link loi",
							Toast.LENGTH_SHORT).show();
					return;
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "loi io khi fetch",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}

			listData = new RssParser().parseXML(inStream);
			CustomListAdapter listAdapter = new
			        CustomListAdapter(MainActivity.this, R.layout.list_single, listData);
	        
			try {
				listViewItem.setAdapter(listAdapter);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this,
                		"No data received" ,
                		Toast.LENGTH_SHORT).show();
				return;
			}
	        
			listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                @Override
	                public void onItemClick(AdapterView<?> parent, View view,
	                                        int position, long id) {
	                	
	                    Toast.makeText(MainActivity.this,
	                    		listData.get(position).getLink(),
	                    		Toast.LENGTH_SHORT).show();
	                }
	            });
		}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnOk = (Button)findViewById(R.id.btnOk);
        spinner = (Spinner)findViewById(R.id.spinner1);
		listViewItem=(ListView)findViewById(R.id.listView1);

        btnOk.setOnClickListener(btnGetImgListener);
        
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayUrl, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

}

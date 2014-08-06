package com.example.rssreadertest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.SourceHelper;
import com.ltt.util.WebAccessHandler;

public class MainActivity extends Activity {

	private Spinner spinner1;
	private Spinner spinner2;
	private Button btnOk;
	private ListView listViewItem;
	private ArrayList<RssItemInfo> listData;
	private SourceHelper sh;
	private OnClickListener btnGetImgListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			WebAccessHandler webhandle = new WebAccessHandler();
			String link = spinner2.getSelectedItem().toString();
			
			// Thuc hien phan tich XML
			InputStream inStream = null;
			
			inStream = webhandle.getStreamFromLink(link);
			if (inStream == null){
				Toast.makeText(MainActivity.this,
                		"No data received" ,
                		Toast.LENGTH_SHORT).show();
				return;
			}
			
			listData = new RssParser().parseXML(inStream);
			CustomListAdapter listAdapter = new
			        CustomListAdapter(MainActivity.this, R.layout.list_single, listData);
	        
			try {
				listViewItem.setAdapter(listAdapter);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this,
                		"No item to display" ,
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
			
			//close connection
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		btnOk.setOnClickListener(btnGetImgListener);
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

		listViewItem=(ListView)findViewById(R.id.listView1);

        btnOk.setOnClickListener(btnGetImgListener);
    }

}

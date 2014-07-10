package com.ltt.rssreader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

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

public class MainActivity extends Activity {

	private Spinner spinner;
	private Button btnOk;
	private ListView listViewItem;
	private ArrayList<ItemInfo> listData;
	private OnClickListener btnGetImgListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String link = spinner.getSelectedItem().toString();
			
			// Thuc hien phan tich XML
			InputStream inStream = null;
			try {
				inStream = new WebAccessHandler(MainActivity.this).fetchURL(link);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this,
                		"Cannot open stream" ,
                		Toast.LENGTH_SHORT).show();
				return;
			}
			
			listData = parseXML(inStream);
			
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

	// Ham phan tich XML
		private ArrayList<ItemInfo> parseXML(InputStream is) {
			ArrayList<ItemInfo> cartList = null;
			try {
				// Tao doi tuong dung cho viec phan tich cu phap tai lieu XML
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				// Doi tuong doc XML
				XMLReader xr = sp.getXMLReader();

				// Tao doi tuong xu ly XML theo tuan tu cua minh
				OrderXMLHandler myXMLHandler = new OrderXMLHandler();
				// Thiet lap noi dung xu ly
				xr.setContentHandler(myXMLHandler);
				// Nguon du lieu vao
				InputSource inStream = new InputSource(is);
				// Bat dau xu ly du lieu vao
				xr.parse(inStream);

				// In chi tiet san pham ra giao dien ung dung
				cartList = myXMLHandler.getCartList();

				is.close();
			} catch (Exception e) {
				Toast.makeText(MainActivity.this,
                		"loi format xml",
                		Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			return cartList;
		}
}

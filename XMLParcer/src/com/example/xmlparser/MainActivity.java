package com.example.xmlparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssXMLHandler;
import com.ltt.util.WebAccessHandler;

public class MainActivity extends Activity {

	private ArrayList<RssItemInfo> listData;
	private LinearLayout ll;
	private TextView tv;
//	private WebView wv;
	private Button btnOk;
	private Spinner spinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		ll = (LinearLayout) findViewById(R.id.linearLayout1);
		tv = new TextView(this);
		
		btnOk = (Button) findViewById(R.id.btnOk);
		spinner = (Spinner) findViewById(R.id.spinner1);

		btnOk.setOnClickListener(btnGetImgListener);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.arrayUrl, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

	}

	private OnClickListener btnGetImgListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String link = spinner.getSelectedItem().toString();

			// Thuc hien phan tich XML
			InputStream inStream = null;
			try {
				inStream = (new WebAccessHandler(MainActivity.this)).fetchURL(link);
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "Cannot open stream",
						Toast.LENGTH_SHORT).show();
				return;
			}

			listData = parseXML(inStream);
			ll.removeAllViews();
			try {
				printData(listData);
			} catch (NullPointerException e) {
				e.printStackTrace();
				Toast.makeText(MainActivity.this, "no data",
						Toast.LENGTH_SHORT).show();
				return;
			}

		}
	};
	
	// Hàm phân tích XML
	private ArrayList<RssItemInfo> parseXML(InputStream is) {
		ArrayList<RssItemInfo> cartList = null;
		try {
			// Tạo đối tượng dùng cho việc phân tích cú pháp tài liệu XML
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			// Đối tượng đọc XML
			XMLReader xr = sp.getXMLReader();

			// Tạo đối tượng xử lý XML theo tuần tự của mình
			RssXMLHandler myXMLHandler = new RssXMLHandler();
			// Thiết lập nội dung xử lý
			xr.setContentHandler(myXMLHandler);
			// Nguồn dữ liệu vào
			InputSource inStream = new InputSource(is);
			// Bắt đầu xử lý dữ liệu vào
			xr.parse(inStream);

			// In chi tiết sản phẩm ra giao diện ứng dụng
			cartList = myXMLHandler.getCartList();

			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("xml reader", "loi format xml");
		}

		return cartList;
	}

	// in kq tu list lay duoc
	private void printData(ArrayList<RssItemInfo> cartList) throws NullPointerException{

		for (RssItemInfo RssItemInfo : cartList) {
			tv = new TextView(this);
			tv.setText("TIEUDE : " + RssItemInfo.getTitle());
			ll.addView(tv);
			
			tv = new TextView(this);
			tv.setText("MOTA : " + RssItemInfo.getDescription());
			ll.addView(tv);
//			wv = new WebView(this);
//			wv.setLayoutParams(new WebView.LayoutParams(LayoutParams.WRAP_CONTENT, 
//                    LayoutParams.WRAP_CONTENT, 0, 0));
//			wv.loadDataWithBaseURL(RssItemInfo.getQuantity(), RssItemInfo.getItemNumber(), "text/html", "utf-8", null);
//			ll.addView(wv);
			
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
			tv.setText("---");
			ll.addView(tv);
		}

	}

	@SuppressWarnings("unused")
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
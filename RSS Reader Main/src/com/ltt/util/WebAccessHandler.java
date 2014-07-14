package com.ltt.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssXMLHandler;

import android.content.Context;
import android.util.Log;

public class WebAccessHandler {
	Context c;
	
	public WebAccessHandler(Context c) {
		super();
		this.c = c;
	}


	public InputStream fetchURL(String strUrl)
			throws MalformedURLException {
		URL url = null;
		InputStream stream = null;
		try {
			url = new URL(strUrl);
			stream = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("xml reader", "loi io khi fetch");
			return null;
		}
		return stream;
	}


	// private static InputStream fetchURL(String strURL) throws IOException {
	// InputStream inputStream = null;
	// URL url = new URL(strURL);
	// URLConnection conn = url.openConnection();
	//
	// try {
	// HttpURLConnection httpConn = (HttpURLConnection) conn;
	// httpConn.setRequestMethod("GET");
	// httpConn.connect();
	//
	// if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
	// inputStream = httpConn.getInputStream();
	// }
	// } catch (Exception ex) {
	// }
	// return inputStream;
	// }

	/** Hàm phân tích XML
	 * 
	 * @param is	InputStream
	 * @return list of RSSItemInfo
	 */
	public ArrayList<RssItemInfo> parseXML(InputStream is) {
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


}
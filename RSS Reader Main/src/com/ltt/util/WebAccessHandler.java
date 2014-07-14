package com.ltt.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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


}
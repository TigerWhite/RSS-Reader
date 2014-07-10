package com.ltt.rssreader;

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

}
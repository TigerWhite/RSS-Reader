package com.ltt.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.widget.Toast;

public class WebAccessHandler {
	Context c;
	
	public WebAccessHandler(Context c) {
		super();
		this.c = c;
	}


	public InputStream OpenHttpConnection(String strURL)
            throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex) {
        	Toast.makeText(c,
            		"Loi ket noi",
            		Toast.LENGTH_SHORT).show();
        }
        return inputStream;
    }

}
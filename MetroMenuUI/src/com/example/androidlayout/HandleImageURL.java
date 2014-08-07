package com.example.androidlayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.ltt.util.WebAccessHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class HandleImageURL {
	
	public String url;
	
	public HandleImageURL(String url){
		this.url = url;
	}
//	public Bitmap loadBitmap(String url, BitmapFactory.Options options) {
//		Bitmap bitmap = null;
//		InputStream in = null;
//		try {
//			in = new WebAccessHandler().downloadOpenStream("http://dantri21.vcmedia.vn/zoom/130_100/uajiKupQ6reCuKKDlVlG/Image/2014/08/suarezchileiini-10482.jpg");
//			bitmap = BitmapFactory.decodeStream(in, null, options);
//		} catch (Exception e1) {
//			return null;
//		}
//
//		//close connection
//		try {
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return bitmap;
//	}
	public Bitmap getBitmapImage() {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
        	Log.d("tag", "errorgetbitmap: "+ url);
            e1.printStackTrace();
        }
        return bitmap;
    }
	public Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
		
    public InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
        	Log.d("tag", "errorhttp: "+ url);
            ex.printStackTrace();
        }
        return stream;
    }
}


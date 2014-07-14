package com.ltt.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class WebAccessHandler {

	/**
	 * hàm lấy dữ liệu từ link bằng openStream
	 * sử dụng tốt cho cnn
	 * @param strUrl	String
	 * @return	InputStream
	 * @throws MalformedURLException
	 */
	public InputStream fetchURL(String strUrl) throws MalformedURLException {
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

	/**
	 * hàm lấy dữ liệu từ link bằng POST method
	 * sử dụng tốt cho vnexpress, dantri
	 * @param url	String
	 * @return	InputStream
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public InputStream getStreamFromUrl(String url)
			throws ClientProtocolException, IOException {
		InputStream is;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) return null;
		is = httpResponse.getEntity().getContent();

		return is;
	}

}
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

/**
 * Class cung cap ham xu ly ket noi du lieu tren web
 * 
 * @author Nguyen Duc Hieu
 * 
 */
public class WebAccessHandler {

	/**
	 * Ham lay du lieu InputStream tu string link<br>
	 * Ham nay su dung ca 2 ham download va da qua xu ly exception
	 * 
	 * @param link
	 *            String
	 * @return InputStream or null
	 */
	public InputStream getStreamFromLink(String link) {
		InputStream inStream = null;
		try {
			inStream = downloadOpenHttp(link);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			Log.e("web handle", "loi phuong thuc");

		} catch (Exception e1) {
			e1.printStackTrace();
			Log.e("web handle", "loi ket noi");
		}

		if (inStream == null) {
			try {
				inStream = downloadOpenStream(link);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e("web handle", "loi link");
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("web handle", "loi io khi fetch");
			}
		}

		return inStream;
	}

	/**
	 * hàm lấy dữ liệu từ link bằng openStream sử dụng tốt cho cnn
	 * 
	 * @param strUrl
	 *            String
	 * @return InputStream
	 * @throws MalformedURLException
	 *             , IOException
	 */
	public InputStream downloadOpenStream(String strUrl) throws MalformedURLException,
			IOException {
		URL url = null;
		InputStream stream = null;

		url = new URL(strUrl);
		stream = url.openStream();

		return stream;
	}

	/**
	 * hàm lấy dữ liệu từ link bằng POST method sử dụng tốt cho vnexpress,
	 * dantri
	 * 
	 * @param url
	 *            String
	 * @return InputStream
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public InputStream downloadOpenHttp(String url)
			throws ClientProtocolException, IOException {
		InputStream is;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			return null;
		is = httpResponse.getEntity().getContent();

		return is;
	}

}
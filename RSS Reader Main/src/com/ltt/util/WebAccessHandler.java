package com.ltt.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssXMLHandler;

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

	/**
	 * Hàm phân tích XML
	 * 
	 * @param is
	 *            InputStream
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
<<<<<<< HEAD
package com.ltt.rssreader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

public class RssParser {

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
=======
package com.ltt.rssreader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

/**
 * Class cung cap ham xu ly rss cho end-user
 * 
 * @author Nguyen Duc Hieu
 */
public class RssParser {

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
>>>>>>> staging

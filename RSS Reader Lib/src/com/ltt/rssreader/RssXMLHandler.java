<<<<<<< HEAD
package com.ltt.rssreader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class RssXMLHandler extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	RssItemInfo productInfo = null;
	ArrayList<RssItemInfo> cartList;

	public ArrayList<RssItemInfo> getCartList() {
		return cartList;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {


		if (qName.equals("rss")) {
			cartList = new ArrayList<RssItemInfo>();
		} else if (qName.equals("item")) {
			productInfo = new RssItemInfo();
			currentElement = true;
		}

		if (attributes.getLength() > 1 && currentElement == true){
			String value = attributes.getValue("url");
			if (value != null)
				if (value.contains(".jpg"))
				productInfo.setThumbnail(attributes.getValue("url"));
        }

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (productInfo != null)
			if (qName.equalsIgnoreCase("title"))
				productInfo.setTitle(currentValue.trim());
			else if (qName.equalsIgnoreCase("description"))
				productInfo.setDescription(currentValue.trim());
			else if (qName.equalsIgnoreCase("link"))
				productInfo.setLink(currentValue.trim());
			else if (qName.equalsIgnoreCase("pubDate"))
				productInfo.setPubDate(currentValue.trim());
			else if (qName.equalsIgnoreCase("summaryimg")) // use for 24h
				productInfo.setThumbnail(currentValue.trim());
			else if (qName.equalsIgnoreCase("item")) {
				// now format cmplx desc
				String desc = productInfo.getDescription();
				String extractedImg = extractDesc(desc);
				if (extractedImg != null)
					productInfo.setThumbnail(extractedImg);
				//tien hanh chuan hoa link
				String curThumb = productInfo.getThumbnail();
				if (curThumb != null && !curThumb.equals(""))
				try {
					new URL(curThumb); //if not full url, need to concat
				} catch (MalformedURLException e1) {
					productInfo.setThumbnail(concatUrl(productInfo.getLink(), curThumb));
				}
				
				productInfo.setDescription(desc.replaceAll("<.+?>", ""));
				
				cartList.add(productInfo);
				currentElement = false;
			}

		currentValue = "";
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}
	
	// extract data from complex description
		private String extractDesc(String inDesc) {
			String output = null;
			output = getMatch("<\\s*img\\s*[^>]+src\\s*=\\s*(['\"]?)(.*?).jpg\\1",
					inDesc, 2);

			if (output != null) output += ".jpg";
			return output;
		}

		private String getMatch(String patternString, String text, int groupIndex) {
			Pattern pattern = Pattern.compile(patternString,
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			return getMatch(pattern, text, groupIndex);
		}

		private String getMatch(Pattern pattern, String text, int groupIndex) {
			if (text != null) {
				Matcher matcher = pattern.matcher(text);
				String match = null;
				while (matcher.find()) {
					match = matcher.group(groupIndex);
					break;
				}
				return match;
			} else {
				return null;
			}
		}
		
		private String concatUrl(String baseUrl, String relativeUrl){
			try {
				URL url = new URL(baseUrl);
				relativeUrl = url.getProtocol() + "://" + url.getHost()
						+ relativeUrl;
			} catch (MalformedURLException e2) {
				Log.e("RssreadrLib", "concat error on base url");
				return relativeUrl;
			}
			return relativeUrl;
		}
=======
package com.ltt.rssreader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * Class trung gian, thua ke {@link DefaultHandler} dung de parse du lieu xml
 * 
 * @author Nguyen Duc Hieu
 */
public class RssXMLHandler extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	RssItemInfo productInfo = null;
	ArrayList<RssItemInfo> cartList;

	public ArrayList<RssItemInfo> getCartList() {
		return cartList;
	}

	//ham xu ly khi tag mo
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equals("rss")) {
			cartList = new ArrayList<RssItemInfo>();
		} else if (qName.equals("item")) {
			productInfo = new RssItemInfo();
			currentElement = true;
		}

		if (attributes.getLength() > 1 && currentElement == true) {
			String value = attributes.getValue("url");
			if (value != null)
				if (value.contains(".jpg"))
					productInfo.setThumbnail(attributes.getValue("url"));
		}

	}

	//ham xu ly khi tag dong
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (productInfo != null)
			if (qName.equalsIgnoreCase("title"))
				productInfo.setTitle(currentValue.trim());
			else if (qName.equalsIgnoreCase("description"))
				productInfo.setDescription(currentValue.trim());
			else if (qName.equalsIgnoreCase("link"))
				productInfo.setLink(currentValue.trim());
			else if (qName.equalsIgnoreCase("pubDate"))
				productInfo.setPubDate(currentValue.trim());
			else if (qName.equalsIgnoreCase("summaryimg")) // use for 24h
				productInfo.setThumbnail(currentValue.trim());
			else if (qName.equalsIgnoreCase("item")) {
				// now format cmplx desc
				String desc = productInfo.getDescription();
				String extractedImg = extractDesc(desc);
				if (extractedImg != null)
					productInfo.setThumbnail(extractedImg);
				// tien hanh chuan hoa link
				String curThumb = productInfo.getThumbnail();
				if (curThumb != null && !curThumb.equals(""))
					try {
						new URL(curThumb); // if not full url, need to concat
					} catch (MalformedURLException e1) {
						productInfo.setThumbnail(concatUrl(
								productInfo.getLink(), curThumb));
					}

				productInfo.setDescription(desc.replaceAll("<.+?>", ""));

				cartList.add(productInfo);
				currentElement = false;
			}

		currentValue = "";
	}

	//ham xu ly khi duyet qua cac ky tu
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}

	// extract data from complex description
	private String extractDesc(String inDesc) {
		String output = null;
		output = getMatch("<\\s*img\\s*[^>]+src\\s*=\\s*(['\"]?)(.*?).jpg\\1",
				inDesc, 2);

		if (output != null)
			output += ".jpg";
		return output;
	}

	// 2 ham xu ly pattern
	private String getMatch(String patternString, String text, int groupIndex) {
		Pattern pattern = Pattern.compile(patternString,
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		return getMatch(pattern, text, groupIndex);
	}

	private String getMatch(Pattern pattern, String text, int groupIndex) {
		if (text != null) {
			Matcher matcher = pattern.matcher(text);
			String match = null;
			while (matcher.find()) {
				match = matcher.group(groupIndex);
				break;
			}
			return match;
		} else {
			return null;
		}
	}

	/**
	 * Ham xu ly link, chuyen doi link relative sang absolute
	 * @param baseUrl	link goc
	 * @param relativeUrl	link relative can convert
	 * @return
	 */
	private String concatUrl(String baseUrl, String relativeUrl) {
		try {
			URL url = new URL(baseUrl);
			relativeUrl = url.getProtocol() + "://" + url.getHost()
					+ relativeUrl;
		} catch (MalformedURLException e2) {
			Log.e("RssreadrLib", "concat error on base url");
			return relativeUrl;
		}
		return relativeUrl;
	}
>>>>>>> staging
}
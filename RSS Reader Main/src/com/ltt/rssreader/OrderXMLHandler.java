package com.ltt.rssreader;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OrderXMLHandler extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	ItemInfo productInfo = null;
	ArrayList<ItemInfo> cartList;

	public ArrayList<ItemInfo> getCartList() {
		return cartList;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (qName.equals("rss")) {
			cartList = new ArrayList<ItemInfo>();
		} else if (qName.equals("item")) {
			productInfo = new ItemInfo();
		}

		if (localName.equals("thumbnail")) {
            productInfo.setThumbnail(attributes.getValue("url"));
        }

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (productInfo != null)
			if (qName.equalsIgnoreCase("title"))
				productInfo.setTitle(currentValue.trim());
			else if (qName.equalsIgnoreCase("description"))
				productInfo.setDescription(currentValue.trim());
			else if (qName.equalsIgnoreCase("link"))
				productInfo.setLink(currentValue.trim());
			else if (qName.equalsIgnoreCase("pubDate"))
				productInfo.setPubDate(currentValue.trim());
			else if (qName.equalsIgnoreCase("item"))
				cartList.add(productInfo);

		currentValue = "";
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
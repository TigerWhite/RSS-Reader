package com.ltt.rssreader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class trung gian, thua ke {@link DefaultHandler} dung de parse du lieu xml
 * Nhiem vu: Lay thong tin ve trang bao cung cap url
 * 
 * @author Nguyen Duc Hieu
 */
public class RssXMLNameHandler extends DefaultHandler {

//	boolean currentElement = false;
	String currentValue = "";

	NewsSourceInfo ns = null;

	public NewsSourceInfo getSource() {
		return ns;
	}

	//ham xu ly khi tag mo
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws MySAXException {

		if (qName.equals("rss")) {
			ns = new NewsSourceInfo();
		} else if (qName.equals("item")) {
			 throw new MySAXException("Done get News sourse");
		}

		if (attributes.getLength() > 1) {
			String value = attributes.getValue("url");
			if (value != null)
				if (value.contains(".jpg"))
					ns.setThumbnail(attributes.getValue("url"));
		}

	}

	//ham xu ly khi tag dong
	@Override
	public void endElement(String uri, String localName, String qName) {

		if (ns != null)
			if (qName.equalsIgnoreCase("title"))
				ns.setTitle(currentValue.trim());
			else if (qName.equalsIgnoreCase("description"))
				ns.setDescription(currentValue.trim());
			else if (qName.equalsIgnoreCase("link"))
				ns.setLink(currentValue.trim());
			

		currentValue = "";
	}

	//ham xu ly khi duyet qua cac ky tu
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

//		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
//		}
	}


}
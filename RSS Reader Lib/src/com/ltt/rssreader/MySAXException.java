package com.ltt.rssreader;

import org.xml.sax.SAXException;

/**
 * Class dung de break SAX parser
 * @author Nguyen Duc Hieu
 *
 */
public class MySAXException extends SAXException {

	private static final long serialVersionUID = 696765928634529428L;

	public MySAXException(String message) {
		super(message);
	}

}

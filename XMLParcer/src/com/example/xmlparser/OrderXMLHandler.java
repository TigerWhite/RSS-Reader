package com.example.xmlparser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OrderXMLHandler extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	ProductInfo productInfo = null;
	ArrayList<ProductInfo> cartList;

	public ArrayList<ProductInfo> getCartList() {
		return cartList;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("rss")) {
			cartList = new ArrayList<ProductInfo>();
		} else if (qName.equals("item")) {
			productInfo = new ProductInfo();
		}

		if (localName.equals("thumbnail")) {
            productInfo.setPrice(attributes.getValue("url"));
        }

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		if (productInfo != null)
			if (qName.equalsIgnoreCase("title"))
				productInfo.setSeqNo(currentValue.trim());
			else if (qName.equalsIgnoreCase("description")) {
				productInfo.setItemNumber(currentValue.trim());
				String extractedImg = null;
				if ( (extractedImg = extractDesc(currentValue.trim())) != null)
					productInfo.setPrice(extractedImg);
			}
			else if (qName.equalsIgnoreCase("link"))
				productInfo.setQuantity(currentValue.trim());
			else if (qName.equalsIgnoreCase("pubdate"))
				productInfo.setExtra(currentValue.trim());
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
	
	//extract data from stupid complex description
	private String extractDesc (String inDesc) {
		String output = null;
		output = getMatch(
				"<\\s*img\\s*[^>]+src\\s*=\\s*(['\"]?)(.*?)\\1",
				inDesc, 2);
		
		return output;
	}
	
	private String getMatch(String patternString, String text, int groupIndex){
	    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE | Pattern.DOTALL );
	    return getMatch(pattern, text, groupIndex);
	}

	private String getMatch(Pattern pattern, String text, int groupIndex){
	    if(text!=null){
	        Matcher matcher = pattern.matcher(text);
	        String match = null;
	        while(matcher.find()){
	            match = matcher.group(groupIndex);
	            break;
	        }
	        return match;           
	    }else{
	        return null;
	    }
	}
}
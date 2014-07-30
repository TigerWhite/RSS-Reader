package com.ltt.rssreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class chua thong tin ve 1 muc rss
 * 
 * @author Nguyen Duc Hieu
 */
public class RssItemInfo {
	private String title;
	private String description;
	private String link;
	private String thumbnail;
	private String pubDate;

	public RssItemInfo() {
		title = description = link = thumbnail = pubDate = "";
	}

	/**
	 * Ham tra ve thoi gian dang Date
	 * 
	 * @return Date
	 */
	public Date getPubDateFormat() {
		Date date = null;
		Locale loc = Locale.getDefault();
		SimpleDateFormat[] dateFormatArray = {
				new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", loc),
				new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", loc),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", loc),
				new SimpleDateFormat("M/d/yyyy hh:mm:ss aaa", loc),
				new SimpleDateFormat("E, dd MMM yyyy", loc) };
		for (SimpleDateFormat df : dateFormatArray) {
			try {
				date = df.parse(pubDate);
				return date;
			} catch (ParseException ex) {
				date = null;
			}
		}
		return null;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}

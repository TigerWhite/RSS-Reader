package com.ltt.rssreader;

/**
 * Class chua thong tin ve 1 trang cung cap rss
 * 
 * @author Nguyen Duc Hieu
 */
public class NewsSourceInfo {
	private String title;
	private String description;
	private String link;
	private String thumbnail;
	
	public NewsSourceInfo(){
		title = description = link = thumbnail = "";
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

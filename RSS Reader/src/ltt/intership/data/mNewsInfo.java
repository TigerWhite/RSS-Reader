package ltt.intership.data;

import java.io.Serializable;

import com.ltt.rssreader.NewsSourceInfo;

public class mNewsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -320690977690995038L;
	private NewsSourceInfo news;

	public mNewsInfo(NewsSourceInfo news) {
		this.news = news;
	}

	public NewsSourceInfo getNewsSource() {
		return this.news;
	}
}

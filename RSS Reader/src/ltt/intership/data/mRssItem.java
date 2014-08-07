package ltt.intership.data;

import java.io.Serializable;

import com.ltt.rssreader.RssItemInfo;

public class mRssItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6877412850495152718L;
	private RssItemInfo item;;

	public mRssItem(RssItemInfo rss_item) {
		this.item = rss_item;
	}

	public RssItemInfo getRssItemInfo() {
		return this.item;
	}
}

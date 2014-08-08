package ltt.intership.data;

import java.io.InputStream;
import java.util.ArrayList;

import android.util.Log;

import com.ltt.rssreader.NewsSourceInfo;
import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.WebAccessHandler;

public class mListItem {
	// private static mListItem singleObj;
	private ArrayList<mRssItem> list;
	private String url;
	private mNewsInfo news;

	public mListItem(String url) {
		list = new ArrayList<mRssItem>();
		update(url);
	}

	private void update(String new_url) {
		if (!list.isEmpty()) {
			list.clear();
		}
		if (this.url == null) {
			Log.i("update instance mListItem", "first time");
		} else if (new_url.equalsIgnoreCase(this.url)) {
			Log.i("update instance mListItem", "repeated -> ignore");
			return;
		}
		this.url = new_url;

		Log.i("update instance mListItem", "update with url: " + this.url);

		InputStream inStream = getStreamRss(new_url);
		if (inStream == null) {
			Log.e("instream", "instream null");
		}

		NewsSourceInfo newSource = new RssParser().getSourceInfo(inStream);
		this.news = new mNewsInfo(newSource);

		inStream = getStreamRss(new_url);
		ArrayList<RssItemInfo> listRssItem = new RssParser().parseXML(inStream);

		if (listRssItem == null) {
			Log.e("listnew", "listRssItem null");
		}

		for (RssItemInfo item : listRssItem) {
			this.list.add(new mRssItem(item));
		}
		Log.i("after add item", "size: " + this.list.size());
	}

	public ArrayList<mRssItem> getList() {
		return this.list;
	}

	public mRssItem get(int position) {
		return this.list.get(position);
	}

	public mNewsInfo getNewsSource() {
		return this.news;
	}

	private InputStream getStreamRss(String link) {
		WebAccessHandler webhandle = new WebAccessHandler();
		InputStream inStream = null;
		inStream = webhandle.getStreamFromLink(link);
		return inStream;
	}
}

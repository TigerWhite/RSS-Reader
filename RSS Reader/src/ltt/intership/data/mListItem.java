package ltt.intership.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import android.util.Log;
import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.WebAccessHandler;

public class mListItem {
//	private static mListItem singleObj;
	private ArrayList<mRssItem> list;
	private String url;

	public mListItem(String url) {
		list = new ArrayList<mRssItem>();
		update(url);
	}

	private void update(String new_url) {
		if (!list.isEmpty()) {
			list.clear();
		}
		if (this.url == null) {
			Log.i("update instance mListItem", "url null");
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
		ArrayList<RssItemInfo> listRssItem = new RssParser().parseXML(inStream);
		if (listRssItem == null) {
			Log.e("listnew", "listRssItem null");
		}

		for (RssItemInfo item : listRssItem) {
			this.list.add(new mRssItem(item));
			Log.i("add item", item.getLink());
		}
		Log.i("after add item", "size: " + this.list.size());
	}

	public ArrayList<mRssItem> getList() {
		return this.list;
	}

	public mRssItem get(int position) {
		return this.list.get(position);
	}

	// public static mListItem getInstance(String url) {
	// if (singleObj == null) {
	// singleObj = new mListItem(url);
	// singleObj.url = url;
	// }
	//
	// return singleObj;
	// }

	public InputStream getStreamRss(String link) {
		WebAccessHandler webhandle = new WebAccessHandler();
		InputStream inStream = null;

		try {
			inStream = webhandle.getStreamFromUrl(link);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
			// Toast.makeText(getActivity(), "loi phuong thuc",
			// Toast.LENGTH_SHORT).show();
			return null;

		} catch (IOException e1) {
			e1.printStackTrace();
			// Toast.makeText(getActivity(), "loi ket noi", Toast.LENGTH_SHORT)
			// .show();
			return null;
		}

		if (inStream == null) {
			try {
				inStream = webhandle.fetchURL(link);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				// Toast.makeText(getActivity(), "link loi",
				// Toast.LENGTH_SHORT).show();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				// Toast.makeText(getActivity(), "loi io khi fetch",
				// Toast.LENGTH_SHORT).show();
				return null;
			}
		}
		return inStream;
	}
}

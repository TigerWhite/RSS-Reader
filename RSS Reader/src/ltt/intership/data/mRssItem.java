package ltt.intership.data;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.util.WebAccessHandler;

public class mRssItem {
	private RssItemInfo item;
	private Bitmap img;

	public mRssItem(RssItemInfo rss_item) {
		this.item = rss_item;
	}

	public mRssItem(RssItemInfo rss_item, Bitmap rss_img) {
		this.item = rss_item;
		this.img = rss_img;
		// this.img = renderImage(rss_item.getThumbnail());
	}

	public RssItemInfo getRssItemInfo() {
		return this.item;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public Bitmap getImg() {
		// if (this.img == null) {
		// this.img = renderImage(this.item.getThumbnail());
		// }
		return this.img;
	}
	
	private Bitmap loadBitmap(String url, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = new WebAccessHandler().fetchURL(url);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			in.close();
		} catch (Exception e1) {
			return null;
		}
		return bitmap;
	}
	
	public Bitmap renderImage(){
		if (img != null) {
			return img;
		}else{
			return renderImage(this.item.getThumbnail());
		}
	}
	private Bitmap renderImage(String src) {
		if (img != null) {
			return img;
		}
		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;

		Bitmap image = loadBitmap(src, bmOptions);

		return image;
	}
}

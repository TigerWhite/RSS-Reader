package ltt.intership.data;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ltt.rssreader.NewsSourceInfo;
import com.ltt.util.WebAccessHandler;

public class mNewsInfo {
	private NewsSourceInfo news;
	private Bitmap img;
	
	public mNewsInfo(NewsSourceInfo news){
		this.news = news;
		this.img = renderImage(news.getThumbnail());
	}
	
	public NewsSourceInfo getNewsSource(){
		return this.news;
	}
	public Bitmap getImg(){
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

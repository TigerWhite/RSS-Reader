package ltt.intership.androidlayout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.rssreader.RssParser;
import com.ltt.util.WebAccessHandler;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class MetroItem extends FrameLayout {
	public MetroItem(Context context) {
		super(context);
	}

	public MetroItem(Context context, int imageID, int id) {
		this(context, DEFAULT_TEXT, imageID, DEFAULT_LINK, id);
	}

	public MetroItem(Context context, int imageID, String title, int id) {
		this(context, title, imageID, DEFAULT_LINK, id);
	}

	public MetroItem(Context context, String title, int image, String link,
			int id) {
		super(context);
		this.title = title;
		this.image = image;
		this.id = id;
		this.link = link;

		this.setOnClickListener((OnClickListener) context);
		this.setOnLongClickListener((OnLongClickListener) context);
		// this.setBackgroundResource(image);
		this.setId(id);

		imageView0 = new ImageView(context);
		imageView0.setPadding(5, 0, 0, 0);
		imageView0.setImageDrawable(getResources().getDrawable(image));
		imageView0.setScaleType(ScaleType.CENTER_CROP);

		imageView1 = new ImageView(context);
		imageView1.setPadding(5, 0, 0, 0);
		// imageView1.setImageDrawable(getResources().getDrawable(image));
		imageView1.setScaleType(ScaleType.CENTER_CROP);

		imageView2 = new ImageView(context);
		imageView2.setPadding(5, 0, 0, 0);
		// imageView2.setImageDrawable(getResources().getDrawable(image));
		imageView2.setScaleType(ScaleType.CENTER_CROP);

		// Picasso.with(getContext()).load("http://dantri21.vcmedia.vn/zoom/130_100/uajiKupQ6reCuKKDlVlG/Image/2014/08/suarezchileiini-10482.jpg").into(imageView);
		// AnimationDrawable animation = new AnimationDrawable();
		// animation.addFrame(getResources().getDrawable(R.drawable.orange),
		// 4000);
		// animation.addFrame(getResources().getDrawable(R.drawable.orange_pressed),
		// 5000);
		// animation.setOneShot(false);
		// animation.start();

		// Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
		// 0.0f,
		// Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF,
		// 1f, Animation.RELATIVE_TO_SELF, 0f);
		slide = new ScaleAnimation((float) 1, (float) 1, (float) 0, (float) 1,
				Animation.RELATIVE_TO_SELF, (float) 0,
				Animation.RELATIVE_TO_SELF, (float) 1);

		slide.setDuration(1000);
		slide.setFillAfter(true);
		slide.setFillEnabled(true);

		setAnimationParams();

		// imageView.setScaleType(ScaleType.FIT_XY);
		// imageView.setX(2);
		// imageView.setY(2);

		textView = new TextView(context);
		textView.setTextColor(getResources().getColor(android.R.color.white));
		Typeface dincond = Typeface.createFromAsset(context.getAssets(),
				"fonts/segoeui.ttf");
		textView.setTypeface(dincond);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
		textView.setText(title);
		// textView.setBackground(getResources().getDrawable(image));
		// textView.setGravity(Gravity.BOTTOM);
		textView.setGravity(Gravity.BOTTOM);

		// this.setGravity(Gravity.BOTTOM);
		this.setClickable(true);

		this.addView(imageView0);
		this.addView(imageView1);
		this.addView(imageView2);
		this.addView(textView);

	}

	public void setID(int id) {
		this.setId(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public void setIcon(int resid) {
		imageView1.setImageDrawable(getResources().getDrawable(resid));
	}

	// public ImageView getImageView() {
	// return imageView;
	// }

	public TextView getTextView() {
		return textView;
	}

	public void setAnimationParams() {
		Random r = new Random();
		// final int it = r.nextInt(1) + 3;
		final int it = this.id % 4 + 1;

		// final int imageArray[] = {R.drawable.pink,R.drawable.pink_pressed};
		if (isGetTwoUrlFromRSSUrl(link) != 0) {
			// if(id <= 10){
			// isGetTwoUrlFromRSSUrl("http://www.thanhnien.comnnn.vn/_layouts/newsrss.aspx?Channel=Gi%C3%A1o+d%E1%BB%A5c");
			final Handler handler = new Handler();
			Runnable runnable = new Runnable() {
				int i = 0;

				public void run() {

					if (i == 0) {
						Picasso.with(getContext()).load(urlList.get(0))
								.into(imageView1);
						Picasso.with(getContext()).load(urlList.get(1))
								.into(imageView2);
						// imageView.setImageResource(imageArray[i]);
						// imageView2.setImageResource(imageArray[i+1]);
						// imageView2.startAnimation(slide);
					}
					if (i == 1) {
						Picasso.with(getContext()).load(urlList.get(1))
								.into(imageView1);
						Picasso.with(getContext()).load(urlList.get(0))
								.into(imageView2);
						// imageView.setImageResource(imageArray[i]);
						// imageView2.setImageResource(imageArray[i-1]);
					}
					imageView2.startAnimation(slide);
					i++;
					if (i > 1) {
						i = 0;
					}
					handler.postDelayed(this, 3000 * it); // for interval...
				}
			};
			handler.postDelayed(runnable, 0); // for initial delay..

		}

	}

	public int isGetTwoUrlFromRSSUrl(String url) {
		try {

			if (url.equals("facebook") == true || url.equals("twiter") == true
					|| url.equals("google+") == true) {
				return 0;
			}
			WebAccessHandler webhandle = new WebAccessHandler();
			ArrayList<RssItemInfo> temp = new ArrayList<RssItemInfo>();
			InputStream inStream = null;
			inStream = webhandle.getStreamFromLink(url);

			if (inStream == null) {
				return 0;
			}
			temp = new RssParser().parseXML(inStream, 2);
			if (temp.size() == 0 || temp == null || temp.get(0) == null
					|| temp.get(0).getThumbnail() == null) {

				return 0;
			}

			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).getThumbnail().length() != 0) {
					urlList.add(temp.get(i).getThumbnail());
					Log.d("tag", "linkurl: " + i + ", " + urlList.get(i));
				}
			}
			if (urlList.size() == 1) {
				urlList.add(urlList.get(0));
			}
			if (urlList.size() == 0) {
				return 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	private int width, height;
	private int image, id;
	private String title, link;
	private static final int DEFAULT_WIDTH = 1;
	private static final int DEFAULT_HEIGHT = 1;
	private static final String DEFAULT_TEXT = "Title";
	private static final String DEFAULT_LINK = "http://www.thanhnsfsdien.comnnn.vn/_layouts/newsrss.aspx?Channel=Gi%C3%A1o+d%E1%BB%A5c";
	private ImageView imageView0, imageView1, imageView2;
	private TextView textView;
	private ArrayList<RssItemInfo> listData;
	private ArrayList<String> urlList = new ArrayList<String>();
	private Animation slide;
}
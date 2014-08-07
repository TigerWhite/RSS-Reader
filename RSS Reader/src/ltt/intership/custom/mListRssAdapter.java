package ltt.intership.custom;

import ltt.intership.R;
import ltt.intership.data.mListItem;
import ltt.intership.data.mRssItem;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class mListRssAdapter extends ArrayAdapter<mRssItem> {
	Activity context = null;
	int layoutId;
	DisplayMetrics metrics;
	mListItem mArr;
	mRssItem item;

	TextView newspaper, title, date;
	ImageView img_article, img_news;

	public interface OnItemClick {
		public void onClick(int position);
	}

	private OnItemClick onItemClick;

	public void setOnItemClick(OnItemClick onItemClick) {
		this.onItemClick = onItemClick;
	}

	public mListRssAdapter(Activity context, int layoutId, mListItem list) {
		super(context, layoutId, list.getList());
		this.context = context;
		this.layoutId = layoutId;
		this.mArr = list;
		this.metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.i("init mListRssAdapter", "list size " + list.getList().size());
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		Log.i("init view in mListRssAdapter", "start at position " + position);

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		Animation animation = null;
		animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
		animation.setDuration(300);
		convertView.startAnimation(animation);
		animation = null;

		item = this.mArr.get(position);
		if (item != null) {
			newspaper = (TextView) convertView
					.findViewById(R.id.itemrss_newspaper);
			img_news = (ImageView) convertView
					.findViewById(R.id.itemrss_img_newspaper);

			title = (TextView) convertView.findViewById(R.id.itemrss_title);
			date = (TextView) convertView.findViewById(R.id.itemrss_date);
			img_article = (ImageView) convertView
					.findViewById(R.id.itemrss_img_article);

			newspaper.setText(mArr.getNewsSource().getNewsSource().getTitle());
			String news_logo_url = mArr.getNewsSource().getNewsSource()
					.getThumbnail();
			if (news_logo_url.length() != 0) {
				Picasso.with(context).load(news_logo_url).into(img_news);
			}
			title.setText(item.getRssItemInfo().getTitle());
			date.setText(item.getRssItemInfo().getPubDate());

			Picasso.with(context).load(item.getRssItemInfo().getThumbnail())
					.into(img_article);

		} else {
			Log.i("adapter", "item null");
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (onItemClick != null)
					onItemClick.onClick(position);

			}
		});
		return convertView;
	}
}

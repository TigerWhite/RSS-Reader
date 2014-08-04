package ltt.intership.custom;

import java.io.InputStream;

import ltt.intership.R;
import ltt.intership.data.mListItem;
import ltt.intership.data.mRssItem;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltt.util.WebAccessHandler;

public class mListRssAdapter extends ArrayAdapter<mRssItem> {
	Activity context = null;
	int layoutId;
	DisplayMetrics metrics;
	mListItem mArr;
	mRssItem item;

	TextView newspaper, title, date;
	ImageView img_article;

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
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.i("init view", "start at position " + position);

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
			title = (TextView) convertView.findViewById(R.id.itemrss_title);
			date = (TextView) convertView.findViewById(R.id.itemrss_date);
			img_article = (ImageView) convertView
					.findViewById(R.id.itemrss_img_article);

			title.setText(item.getRssItemInfo().getTitle());
			date.setText(item.getRssItemInfo().getPubDate());

			if (item.getImg() != null) {
				Log.i("load existed image", "position :" + position);
				img_article.setImageBitmap(item.getImg());
			} else {
				Log.i("created image", "position :" + position);
				new mLoadBitmap(convertView, item.getRssItemInfo()
						.getThumbnail(), position).execute();
			}

		} else {
			Log.i("adapter", "item null");
		}

		return convertView;
	}

	public Bitmap loadBitmap(String url, BitmapFactory.Options options) {
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

	private class mLoadBitmap extends AsyncTask<Void, Void, Void> {
		View v;
		String thumb;
		Bitmap bmpContent;
		int pos;

		public mLoadBitmap(View view, String thumbnail, int position) {
			this.v = view;
			this.thumb = thumbnail;
			this.pos = position;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(Void... params) {
			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;

			bmpContent = loadBitmap(this.thumb, bmOptions);

			publishProgress();
			return null;
		};

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);

			ImageView img = (ImageView) this.v
					.findViewById(R.id.itemrss_img_article);
			if (bmpContent != null) {
				img.setImageBitmap(bmpContent);
			} else {
				// img.setVisibility(View.GONE);
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mArr.get(pos).setImg(bmpContent);
		}
	}
}

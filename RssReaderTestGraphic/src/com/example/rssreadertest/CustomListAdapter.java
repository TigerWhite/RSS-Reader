<<<<<<< HEAD
package com.example.rssreadertest;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.util.WebAccessHandler;

public class CustomListAdapter extends ArrayAdapter<RssItemInfo> {
	ArrayList<RssItemInfo> array;
	int resource;
	ImageView img;
	TextView txtTitle;
	TextView txtDesc;
	TextView txtDate;
	Context context;
	RssItemInfo friend;

	public CustomListAdapter(Context context, int textViewResourceId,
			ArrayList<RssItemInfo> array) {
		super(context, textViewResourceId, array);

		this.context = context;
		this.resource = textViewResourceId;
		this.array = array;
	}

	// Phuong thuc xac dinh View ma Adapter hien thi
	// CustomViewFriend
	public View getView(int position, View convertView, ViewGroup parent) {
		View friendView = convertView;
		if (friendView == null) {
			friendView = new CustomViewFriend(getContext());
		}
		friend = array.get(position);
		if (friend != null) {
			txtTitle = ((CustomViewFriend) friendView).txtTitle;
			txtDesc = ((CustomViewFriend) friendView).txtDesc;
			txtDate = ((CustomViewFriend) friendView).txtDate;
			img = ((CustomViewFriend) friendView).img;

			// lay doi tuong friend va dua ra UI
			txtTitle.setText(friend.getTitle());
			txtDesc.setText(Html.fromHtml(friend.getDescription()));
			txtDate.setText(friend.getPubDate());

			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;
			Bitmap bmpContent = loadBitmap(friend.getThumbnail(), bmOptions);
			if (bmpContent != null) {
				img.setImageBitmap(bmpContent);
			} else {
				img.setVisibility(View.GONE);
			}
			img.invalidate();

		}
		return friendView;

	}

	public Bitmap loadBitmap(String url, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = new WebAccessHandler().fetchURL(url);
//			in = new WebAccessHandler().getStreamFromUrl(url);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			in.close();
		} catch (Exception e1) {
			return null;
		}
		return bitmap;
	}

	class CustomViewFriend extends RelativeLayout {
		ImageView img;
		TextView txtTitle;
		TextView txtDesc;
		TextView txtDate;
		Context context;

		public CustomViewFriend(Context context) {
			super(context);
			// su dung LayoutInflater de gan giao dien trong list_single.xml
			this.context = context;
			LayoutInflater li = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			li.inflate(R.layout.list_single, this, true);

			img = (ImageView) findViewById(R.id.img);
			txtTitle = (TextView) findViewById(R.id.title);
			txtDesc = (TextView) findViewById(R.id.desc);
			txtDate = (TextView) findViewById(R.id.date);
		}
	}

=======
package com.example.rssreadertest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ltt.rssreader.RssItemInfo;
import com.ltt.util.WebAccessHandler;

public class CustomListAdapter extends ArrayAdapter<RssItemInfo> {
	ArrayList<RssItemInfo> array;
	int resource;
	ImageView img;
	TextView txtTitle;
	TextView txtDesc;
	TextView txtDate;
	Context context;
	RssItemInfo friend;

	public CustomListAdapter(Context context, int textViewResourceId,
			ArrayList<RssItemInfo> array) {
		super(context, textViewResourceId, array);

		this.context = context;
		this.resource = textViewResourceId;
		this.array = array;
	}

	// Phuong thuc xac dinh View ma Adapter hien thi
	// CustomViewFriend
	public View getView(int position, View convertView, ViewGroup parent) {
		View friendView = convertView;
		if (friendView == null) {
			friendView = new CustomViewFriend(getContext());
		}
		friend = array.get(position);
		if (friend != null) {
			txtTitle = ((CustomViewFriend) friendView).txtTitle;
			txtDesc = ((CustomViewFriend) friendView).txtDesc;
			txtDate = ((CustomViewFriend) friendView).txtDate;
			img = ((CustomViewFriend) friendView).img;

			// lay doi tuong friend va dua ra UI
			txtTitle.setText(friend.getTitle());
			txtDesc.setText(Html.fromHtml(friend.getDescription()));
			txtDate.setText(friend.getPubDate());

			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;
			Bitmap bmpContent = loadBitmap(friend.getThumbnail(), bmOptions);
			if (bmpContent != null) {
				img.setImageBitmap(bmpContent);
			} else {
				img.setVisibility(View.GONE);
			}
			img.invalidate();

		}
		return friendView;

	}

	public Bitmap loadBitmap(String url, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = new WebAccessHandler().fetchURL(url);
//			in = new WebAccessHandler().getStreamFromUrl(url);
			bitmap = BitmapFactory.decodeStream(in, null, options);
		} catch (Exception e1) {
			return null;
		}
		
		//close connection
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bitmap;
	}

	class CustomViewFriend extends RelativeLayout {
		ImageView img;
		TextView txtTitle;
		TextView txtDesc;
		TextView txtDate;
		Context context;

		public CustomViewFriend(Context context) {
			super(context);
			// su dung LayoutInflater de gan giao dien trong list_single.xml
			this.context = context;
			LayoutInflater li = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			li.inflate(R.layout.list_single, this, true);

			img = (ImageView) findViewById(R.id.img);
			txtTitle = (TextView) findViewById(R.id.title);
			txtDesc = (TextView) findViewById(R.id.desc);
			txtDate = (TextView) findViewById(R.id.date);
		}
	}

>>>>>>> staging
}
package ltt.intership.custom;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import ltt.intership.R;
import ltt.intership.custom.mListRssAdapter.OnItemClick;
import ltt.intership.data.mListItem;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import twitter4j.Status;

public class mListTweetAdapter extends ArrayAdapter<Status> {
	Activity act;
	List<Status> listStatus;
	int layoutId;

	TextView userScreen, updateTime, updateText;
	ImageView userImg;

	public mListTweetAdapter(Activity activity, int layoutId,
			List<Status> list) {
		super(activity, layoutId, list);
		this.act = activity;
		this.layoutId = layoutId;
		this.listStatus = list;
	}

	public interface OnItemClick {
		public void onClick(int position);
	}

	private OnItemClick onItemClick;

	public void setOnItemClick(OnItemClick onItemClick) {
		this.onItemClick = onItemClick;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = act.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);

		Status tweet = listStatus.get(position);

		userScreen = (TextView) convertView.findViewById(R.id.tweet_userScreen);
		updateTime = (TextView) convertView.findViewById(R.id.tweet_updateTime);
		updateText = (TextView) convertView.findViewById(R.id.tweet_updateText);
		userImg = (ImageView) convertView.findViewById(R.id.tweet_userImg);

		userScreen.setText(tweet.getUser().getScreenName());
		updateTime.setText(tweet.getCreatedAt().getDate() + " - "
				+ tweet.getCreatedAt().getMonth());
		updateText.setText(tweet.getText());
		Picasso.with(act).load(tweet.getUser().getProfileImageURL())
				.into(userImg);

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

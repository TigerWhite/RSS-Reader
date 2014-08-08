package ltt.intership.fragment;

import java.util.List;

import ltt.intership.R;
import ltt.intership.activity.ReadArticleActivity;
import ltt.intership.activity.SettingActivity;
import ltt.intership.custom.mListRssAdapter;
import ltt.intership.custom.mListTweetAdapter;
import ltt.intership.custom.mListRssAdapter.OnItemClick;
import ltt.intership.data.StreamRenderer;
import ltt.intership.data.mListItem;
import ltt.intership.utils.Config;

import org.json.JSONObject;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;

public class ListRssFragment extends Fragment {
	public ListRssFragment() {
	}

	mListRssAdapter mRssAdapter;
	mListTweetAdapter mTweetAdapter;
	String url;
	String category;
	LinearLayout btnBack;
	ListView lvRss;
	mListItem list;
	ImageButton btnSetting;

	private SharedPreferences tweetPrefs;
	private Twitter timelineTwitter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_rss, container,
				false);

		this.url = getActivity().getIntent().getStringExtra("url");
		this.category = getActivity().getIntent().getStringExtra("category");

		Log.i("url received", url);

		// list view

		if (url.equalsIgnoreCase("facebook")) {
			requestFbTimeline();
			Log.d("lenglist", "" + StreamRenderer.getLengList());
			for (int i = 0; i < StreamRenderer.getLengList(); i++) {
				Log.d("content", "" + i + "  "
						+ StreamRenderer.getList(i).getMessage() + " -- "
						+ StreamRenderer.getList(i).getDescription());
				if (StreamRenderer.getList(i).getLengComment() != 0) {
					Log.d("lengcomment", ""
							+ StreamRenderer.getList(i).getLengComment());
					for (int j = 0; j < StreamRenderer.getList(i)
							.getLengComment(); j++) {
						Log.d("commet",
								""
										+ j
										+ "  "
										+ StreamRenderer.getList(i)
												.getComment(j).getMessage());
					}
				}

			}
		} else if (url.equalsIgnoreCase("twitter")) {
			tweetPrefs = getActivity().getSharedPreferences(
					Config.GLOBAL_PREFS, 0);
			String userToken = tweetPrefs.getString(Config.TWITTER_USER_TOKEN,
					null);
			String userSecret = tweetPrefs.getString(
					Config.TWITTER_USER_SECRET, null);
			// create new configuration
			Configuration twitConf = new ConfigurationBuilder()
					.setDebugEnabled(true)
					.setOAuthConsumerKey(Config.TWITTER_KEY)
					.setOAuthConsumerSecret(Config.TWITTER_SECRET)
					.setOAuthAccessToken(userToken)
					.setOAuthAccessTokenSecret(userSecret).build();
			// instantiate new twitter
			timelineTwitter = new TwitterFactory(twitConf).getInstance();
			List<Status> homeTimeline = null;
			try {
				homeTimeline = timelineTwitter.getHomeTimeline();
			} catch (Exception e) {
				// TODO: handle exception
			}
			lvRss = (ListView) rootView.findViewById(R.id.listRss_lv);
			mTweetAdapter = new mListTweetAdapter(getActivity(),
					R.layout.tweet_info, homeTimeline);
		} else {
			lvRss = (ListView) rootView.findViewById(R.id.listRss_lv);

			list = new mListItem(url);

			Log.i("get instance", "size: " + list.getList().size());

			mRssAdapter = new mListRssAdapter(getActivity(),
					R.layout.item_rss_layout, list);
			lvRss.setAdapter(mRssAdapter);

			mRssAdapter.setOnItemClick(new OnItemClick() {

				@Override
				public void onClick(int position) {
					// TODO Auto-generated method stub
					Log.i("item list view selected", "pos: " + position);
					Intent i = new Intent(getActivity(),
							ReadArticleActivity.class);
					i.putExtra("url", url);
					i.putExtra("position", position);
					startActivity(i);
				}
			});
		}

		btnBack = (LinearLayout) rootView.findViewById(R.id.listRss_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

		((TextView) rootView.findViewById(R.id.listRss_tv_category))
				.setText(category);

		btnSetting = (ImageButton) rootView
				.findViewById(R.id.listRss_btn_setting);
		btnSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), SettingActivity.class);
				startActivity(i);
			}
		});
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	private void requestFbTimeline() {
		Log.d("data", "start render");
		Session session = Session.getActiveSession();
		Request.Callback callback = new Request.Callback() {
			public void onCompleted(Response response) {
				JSONObject graphResponse = response.getGraphObject()
						.getInnerJSONObject();

				StreamRenderer.render(graphResponse);
			}
		};

		Request request = new Request(session, "me/home", null, HttpMethod.GET,
				callback);

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();

	}

}

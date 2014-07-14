package com.example.zend_demo_twitter4j;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

public class TwitterProfile extends Activity {
	protected final String TWITTER_KEY = "n5X2nGrnDCxA0hDqcdiVPF9zV";
	protected final String TWITTER_SECRET = "0QnIopG0446kcMC6fDuoU8SuAlzAe3VtvlpzQiKaJgQpN5vxrh";
	protected final String PREFS = "prefs_twitter";
	protected final String USER_TOKEN = "user_token";
	protected final String USER_SECRET = "user_secret";

	private Twitter profileTwitter;

	/** shared preferences for user details */
	private SharedPreferences mPrefs;

	// debugging tag
	private String LOG_TAG = "ProfileActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_profile);
		// get prefs
		mPrefs = getSharedPreferences("prefs_twitter", 0);
		// get user preferences
		String userToken = mPrefs.getString("user_token", null);
		String userSecret = mPrefs.getString("user_secret", null);

		// create new configuration
		Configuration twitConf = new ConfigurationBuilder()
				.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_KEY)
				.setOAuthConsumerSecret(TWITTER_SECRET)
				.setOAuthAccessToken(userToken)
				.setOAuthAccessTokenSecret(userSecret).build();
		// instantiate new twitter
		profileTwitter = new TwitterFactory(twitConf).getInstance();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			Log.i(LOG_TAG, "limit: "+profileTwitter.getRateLimitStatus().toString());
			
			// retrieve user profile
			Log.i(LOG_TAG, "USER PROFILE");
			Long userID = profileTwitter.getId();
			User user = profileTwitter.showUser(userID);
			String userName = user.getName();
			String userImageUrl = user.getProfileImageURL();
			String url = user.getURL();
			String location = user.getLocation();
			String description = user.getDescription();

			Log.i(LOG_TAG, "name:" + userName);
			Log.i(LOG_TAG, "image url:" + userImageUrl);
			Log.i(LOG_TAG, "url:" + url);
			Log.i(LOG_TAG, "location:" + location);
			Log.i(LOG_TAG, "description:" + description);

			Log.i(LOG_TAG, "GET TWEETS");
			int page = 1;
			List<Status> mTweet = null;
			do {
				mTweet = profileTwitter.getUserTimeline(new Paging(page, 200));
				if (mTweet.isEmpty()) {
					break;
				} else {
					for (Status mStatus : mTweet) {
						Log.i(LOG_TAG,
								mStatus.getText()
										+ "\n "
										+ DateUtils
												.getRelativeTimeSpanString(mStatus
														.getCreatedAt()
														.getTime()) + " ");
					}
					page++;
				}
			} while (true);

			Log.i(LOG_TAG, "GET FAVORITE");

			List<Status> mFavorite = profileTwitter.getFavorites();
			for (Status favor : mFavorite) {
				Log.i(LOG_TAG, favor.toString());
			}
			
			 Log.i(LOG_TAG, "GET TWEET MENTION");
			 // max 800 tweet
			 List<Status> mMention = profileTwitter.getMentionsTimeline(new Paging(1, 200));
			 Log.i(LOG_TAG, "GET SAVED SEARCH");
			 List<SavedSearch> mSaved= profileTwitter.getSavedSearches();
			// Log.i(LOG_TAG, "GET FOLLOW");
			 IDs followeingID = profileTwitter.getFriendsIDs(-1);
			 do {
				for (Long i : followeingID.getIDs()) {
					Log.i(LOG_TAG, "following: " + profileTwitter.showUser(i).getName());
				}
			} while (followeingID.hasNext());
			// Log.i(LOG_TAG, "GET FOLLOWER");
			 IDs followerID = profileTwitter.getFollowersIDs(-1);
			 do {
				for (Long i : followerID.getIDs()) {
					Log.i(LOG_TAG, "follower: " + profileTwitter.showUser(i).getName());
				}
			} while (followerID.hasNext());
			 
		} catch (Exception te) {
			Log.e(LOG_TAG, "Exception: " + te);
		}

	}

	private void addView() {
		// Drawable imgDrawable = LoadImageFromWeb(imgUrl[0]);
		//
		// ImageView imgNew = new ImageView(getApplicationContext());
		// LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// p.gravity = Gravity.CENTER;
		// imgNew.setLayoutParams(p);
		// imgNew.setTop(10);
		// imgNew.setImageDrawable(imgDrawable);
		// llTop.addView(imgNew);
	}

	private Drawable LoadImageFromWeb(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}
}

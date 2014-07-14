package com.example.zend_demo_twitter4j;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.provider.ContactsContract.Profile;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	protected final String TWITTER_KEY = "n5X2nGrnDCxA0hDqcdiVPF9zV";
	protected final String TWITTER_SECRET = "0QnIopG0446kcMC6fDuoU8SuAlzAe3VtvlpzQiKaJgQpN5vxrh";
	protected final String TWITTER_CALLBACK_URL = "zend-testing://test";
	protected final String PREFS = "prefs_twitter";
	protected final String USER_TOKEN = "user_token";
	protected final String USER_SECRET = "user_secret";

	protected Twitter twitterConnection;
	protected RequestToken twitterRequestToken;
	protected SharedPreferences twitterPrefs;

	private String LOG_TAG = "MainActivityLog";

	/** main view for the home timeline */
	private ListView homeTimeline;
	/** database helper for update data */
	private SQLiteDataHelper timelineHelper;
	/** update database */
	private SQLiteDatabase timelineDB;
	/** cursor for handling data */
	private Cursor timelineCursor;
	/** adapter for mapping data */

	private UpdateAdapter timelineAdapter;
	/** Broadcast receiver for when new updates are available */
	private BroadcastReceiver niceStatusReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);

		twitterPrefs = getSharedPreferences(PREFS, MODE_PRIVATE);

		if (twitterPrefs.getString(USER_TOKEN, null) == null) {
			Log.d(LOG_TAG, "first time login");
			setContentView(R.layout.layout_first_time_login);

			twitterConnection = new TwitterFactory().getInstance();
			twitterConnection.setOAuthConsumer(TWITTER_KEY, TWITTER_SECRET);
			try {
				twitterRequestToken = twitterConnection
						.getOAuthRequestToken(TWITTER_CALLBACK_URL);
			} catch (TwitterException te) {
				Log.e(LOG_TAG, "error: " + te.getMessage());
			}

			Button btnLogin = (Button) findViewById(R.id.btnSignIn);
			btnLogin.setOnClickListener(this);
		} else {
			setUpTimeline();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSignIn:
			Toast.makeText(getApplicationContext(), "clicked",
					Toast.LENGTH_SHORT).show();

			String authURL = twitterRequestToken.getAuthenticationURL();
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(authURL)));

			break;
		// user has pressed tweet button
		case R.id.tweetbtn:
			// launch tweet activity
			startActivity(new Intent(this, Tweet.class));
			break;
		case R.id.btnProfile:
			// launch profile activity
			 startActivity(new Intent(this, TwitterProfile.class));
			break;
		default:
			break;
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Uri twitterURI = intent.getData();
		if (twitterURI != null
				&& twitterURI.toString().startsWith(TWITTER_CALLBACK_URL)) {
			Log.d(LOG_TAG, "URI parameter: " + twitterURI.toString());
			String oaVerifier = twitterURI.getQueryParameter("oauth_verifier");

			try {
				AccessToken accToken = twitterConnection.getOAuthAccessToken(
						twitterRequestToken, oaVerifier);
				twitterPrefs.edit().putString(USER_TOKEN, accToken.getToken())
						.putString(USER_SECRET, accToken.getTokenSecret())
						.commit();

				Log.e(LOG_TAG, "Access granted: " + accToken.getToken() + "-"
						+ accToken.getTokenSecret());
				setUpTimeline();
			} catch (TwitterException e) {
				Log.e(LOG_TAG, "Failed to get access token: " + e.getMessage());
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected void setUpTimeline() {
		setContentView(R.layout.layout_timeline);

		try {
			// get the timeline
			homeTimeline = (ListView) findViewById(R.id.homeList);
			// instantiate database helper
			timelineHelper = new SQLiteDataHelper(this);
			// get the database
			timelineDB = timelineHelper.getReadableDatabase();
			// query the database, most recent tweets first
			timelineCursor = timelineDB.query("home", null, null, null, null,
					null, "update_time DESC");
			// manage the updates using a cursor
			startManagingCursor(timelineCursor);
			// instantiate adapter
			timelineAdapter = new UpdateAdapter(this, timelineCursor);
			// this will make the app populate the new update data in the
			// timeline view
			homeTimeline.setAdapter(timelineAdapter);
			// instantiate receiver class for finding out when new updates are
			// available
			niceStatusReceiver = new TwitterUpdateReceiver();
			// register for updates
			registerReceiver(niceStatusReceiver, new IntentFilter(
					"TWITTER_UPDATES"));
			// start the Service for updates now
			this.getApplicationContext().startService(
					new Intent(this.getApplicationContext(),
							TimelineService.class));
		} catch (Exception te) {
			Log.e(LOG_TAG, "Failed to fetch timeline: " + te.getMessage());
		}

		// setup onclick listener for tweet button
		LinearLayout tweetClicker = (LinearLayout) findViewById(R.id.tweetbtn);
		tweetClicker.setOnClickListener(this);

		Button btnProfile = (Button) findViewById(R.id.btnProfile);
		btnProfile.setOnClickListener(this);
	}

	/**
	 * Class to implement Broadcast receipt for new updates
	 */
	class TwitterUpdateReceiver extends BroadcastReceiver {
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent) {
			int rowLimit = 100;
			if (DatabaseUtils.queryNumEntries(timelineDB, "home") > rowLimit) {
				String deleteQuery = "DELETE FROM home WHERE "
						+ BaseColumns._ID + " NOT IN " + "(SELECT "
						+ BaseColumns._ID + " FROM home ORDER BY "
						+ "update_time DESC " + "limit " + rowLimit + ")";
				timelineDB.execSQL(deleteQuery);
			}
			timelineCursor = timelineDB.query("home", null, null, null, null,
					null, "update_time DESC");
			startManagingCursor(timelineCursor);
			timelineAdapter = new UpdateAdapter(context, timelineCursor);
			homeTimeline.setAdapter(timelineAdapter);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			// stop the updater Service
			stopService(new Intent(this, TimelineService.class));
			// remove receiver register
			unregisterReceiver(niceStatusReceiver);
			// close the database
			timelineDB.close();
			
			// remove twitter key
			twitterPrefs.edit().remove(USER_TOKEN).commit();
			twitterPrefs.edit().remove(USER_SECRET).commit();
		} catch (Exception se) {
			Log.e(LOG_TAG, "unable to stop Service or receiver");
		}
	}
}

package com.example.zend_demo_twitter4j;

import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class TimelineService extends Service {
	/** twitter authentication key */
	public final static String TWIT_KEY = "n5X2nGrnDCxA0hDqcdiVPF9zV";
	/** twitter secret */
	public final static String TWIT_SECRET = "0QnIopG0446kcMC6fDuoU8SuAlzAe3VtvlpzQiKaJgQpN5vxrh";
	/** twitter object */
	private Twitter timelineTwitter;

	/** database helper object */
	private SQLiteDataHelper niceHelper;
	/** timeline database */
	private SQLiteDatabase niceDB;

	/** shared preferences for user details */
	private SharedPreferences nicePrefs;
	/** handler for updater */
	private Handler niceHandler;
	/** delay between fetching new tweets */
	private static int mins = 1;// alter to suit
	private static final long FETCH_DELAY = mins * (60 * 1000);
	// debugging tag
	private String LOG_TAG = "TimelineService";

	/** updater thread object */
	private TimelineUpdater niceUpdater;

	@Override
	public void onCreate() {
		super.onCreate();
		// get prefs
		nicePrefs = getSharedPreferences("prefs_twitter", 0);
		// get database helper
		niceHelper = new SQLiteDataHelper(this);
		// get the database
		niceDB = niceHelper.getWritableDatabase();

		// get user preferences
		String userToken = nicePrefs.getString("user_token", null);
		String userSecret = nicePrefs.getString("user_secret", null);
		Log.d(LOG_TAG, "token: " + userToken + "--- secret: " + userSecret);
		
		// create new configuration
		Configuration twitConf = new ConfigurationBuilder().setDebugEnabled(true).setOAuthConsumerKey(TWIT_KEY)
				.setOAuthConsumerSecret(TWIT_SECRET)
				.setOAuthAccessToken(userToken)
				.setOAuthAccessTokenSecret(userSecret).build();
		// instantiate new twitter
		timelineTwitter = new TwitterFactory(twitConf).getInstance();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStart(intent, startId);
		// get handler
		niceHandler = new Handler();
		// create an instance of the updater class
		niceUpdater = new TimelineUpdater();
		// add to run queue
		niceHandler.post(niceUpdater);
		// return sticky
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// stop the updating
		niceHandler.removeCallbacks(niceUpdater);
		niceDB.close();
	}

	/**
	 * TimelineUpdater class implements the runnable interface
	 */
	class TimelineUpdater implements Runnable {
		// run method
		public void run() {
			// check for updates - assume none
			boolean statusChanges = false;

			// fetch timeline
			try {
				// retrieve the new home timeline tweets as a list
				List<Status> homeTimeline = timelineTwitter.getHomeTimeline();
				// iterate through new status updates
				for (Status statusUpdate : homeTimeline) {
					// call the getValues method of the data helper class,
					// passing the new updates
					ContentValues timelineValues = SQLiteDataHelper
							.getValues(statusUpdate);
					// if the database already contains the updates they will
					// not be inserted
					niceDB.insertOrThrow("home", null, timelineValues);
					// confirm we have new updates
					statusChanges = true;
				}
			} catch (Exception te) {
				Log.e(LOG_TAG, "Exception: " + te);
			}
			// if we have new updates, send a Broadcast
			if (statusChanges) {
				// this should be received in the main timeline class
				sendBroadcast(new Intent("TWITTER_UPDATES"));
			}
			// delay fetching new updates
			niceHandler.postDelayed(this, FETCH_DELAY);
		}
	}

}

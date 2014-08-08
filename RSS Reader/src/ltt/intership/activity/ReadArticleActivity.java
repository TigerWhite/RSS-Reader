package ltt.intership.activity;

import java.util.ArrayList;

import ltt.intership.R;
import ltt.intership.custom.mSpinnerAdapter;
import ltt.intership.data.mListItem;
import ltt.intership.data.mSocialMedia;
import ltt.intership.fragment.ScreenSlidePageFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.google.android.gms.plus.PlusShare;

public class ReadArticleActivity extends FragmentActivity implements
		OnClickListener {
	private int NUM_PAGES = 1;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private mListItem list;

	private LinearLayout btnBack;
	private Button btnSearch, btnShare;

	private Spinner spinner;
	private boolean firstTime;

	// share facebook
	private static final String PERMISSION = "publish_actions";
	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_article);

		firstTime = true;

		String url = getIntent().getStringExtra("url");
		int pos = getIntent().getIntExtra("position", 1);

		Log.i("url received", url);

		list = new mListItem(url);
		this.NUM_PAGES = list.getList().size();

		// init facebook
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When changing pages, reset the action bar actions since they
				// are dependent
				// on which page is currently active. An alternative approach is
				// to have each
				// fragment expose actions itself (rather than the activity
				// exposing actions),
				// but for simplicity, the activity provides the actions in this
				// sample.
				invalidateOptionsMenu();
			}
		});
		mPager.setPageTransformer(true, new DepthPageTransformer());
		mPager.setCurrentItem(pos);

		btnBack = (LinearLayout) findViewById(R.id.readArticle_btn_back);
		btnBack.setOnClickListener(this);

		btnSearch = (Button) findViewById(R.id.readArticle_btn_search);
		btnSearch.setOnClickListener(this);

		btnShare = (Button) findViewById(R.id.readArticle_btn_share);
		btnShare.setOnClickListener(this);

		spinner = (Spinner) findViewById(R.id.readArticle_spinner);
		// spinner.setOnItemSelectedListener(this);
		ArrayList<mSocialMedia> data = new ArrayList<mSocialMedia>();
		data.add(new mSocialMedia("facebook", R.drawable.fb_logo));
		data.add(new mSocialMedia("google", R.drawable.google_logo));
		data.add(new mSocialMedia("twitter", R.drawable.twitter_logo));
		mSpinnerAdapter mAdapter = new mSpinnerAdapter(this,
				android.R.layout.simple_spinner_item, data);
		spinner.setAdapter(mAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (firstTime) {
					firstTime = false;
					return;
				}
				int pos = mPager.getCurrentItem();
				switch (arg2) {
				case 0:
					shareFacebook(list.get(pos).getRssItemInfo().getLink());
					break;
				case 1:
					shareGoogle(list.get(pos).getRssItemInfo().getLink());
					break;
				case 2:
					shareTwitter("twitter "
							+ list.get(pos).getRssItemInfo().getLink());
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		((TextView) findViewById(R.id.readArticle_tv_theme)).setText(list
				.getNewsSource().getNewsSource().getTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_article, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.readArticle_btn_back:
			finish();
			break;
		case R.id.readArticle_btn_search:
			Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
			break;
		case R.id.readArticle_btn_share:
			spinner.performClick();
			break;
		default:
			break;
		}
	}

	private void shareFacebook(String url) {
		// Toast.makeText(getApplicationContext(), url,
		// Toast.LENGTH_LONG).show();
		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this).setLink(url).build();
			uiHelper.trackPendingDialogCall(shareDialog.present());

		} else {
			publishFeedDialog();
			// Fallback. For example, publish the post using the Feed Dialog
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						// xu ly khi dang ki thanh cong
					}
				});
	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		int pos = mPager.getCurrentItem();
		params.putString("name", list.get(pos).getRssItemInfo().getTitle());
		// params.putString("caption",
		// "Build great social apps and get more installs.");
		// params.putString("description",
		// "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		params.putString("link", list.get(pos).getRssItemInfo().getLink());
		params.putString("picture", list.get(pos).getRssItemInfo()
				.getThumbnail());

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
				getApplicationContext(), Session.getActiveSession(), params))
				.setOnCompleteListener(listener).build();
		feedDialog.show();
	}

	OnCompleteListener listener = new OnCompleteListener() {

		@Override
		public void onComplete(Bundle values, FacebookException error) {
			// TODO Auto-generated method stub
			if (error == null) {
				// When the story is posted, echo the success
				// and the post Id.
				final String postId = values.getString("post_id");
				if (postId != null) {
					Toast.makeText(getApplicationContext(),
							"Posted story, id: " + postId, Toast.LENGTH_SHORT)
							.show();
				} else {
					// User clicked the Cancel button
					Toast.makeText(getApplicationContext(),
							"Publish cancelled", Toast.LENGTH_SHORT).show();
				}
			} else if (error instanceof FacebookOperationCanceledException) {
				// User clicked the "x" button
				Toast.makeText(getApplicationContext(), "Publish cancelled",
						Toast.LENGTH_SHORT).show();
			} else {
				// Generic, ex: network error
				Toast.makeText(getApplicationContext(), "Error posting story",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private void shareGoogle(String url) {
		Intent shareIntent = new PlusShare.Builder(ReadArticleActivity.this)
				.setType("text/plain").setText(" ")
				.setContentUrl(Uri.parse(url)).getIntent();
		startActivityForResult(shareIntent, 0);
	}

	private void shareTwitter(String url) {
		// Toast.makeText(getApplicationContext(), url,
		// Toast.LENGTH_LONG).show();
	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			return ScreenSlidePageFragment.create(position,
					list.getList().get(position), list.getNewsSource());
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}
	}

	public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.85f;
		private static final float MIN_ALPHA = 0.5f;

		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) { // [-1,1]
				// Modify the default slide transition to shrink the page as
				// well
				float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
				float vertMargin = pageHeight * (1 - scaleFactor) / 2;
				float horzMargin = pageWidth * (1 - scaleFactor) / 2;
				if (position < 0) {
					view.setTranslationX(horzMargin - vertMargin / 2);
				} else {
					view.setTranslationX(-horzMargin + vertMargin / 2);
				}

				// Scale the page down (between MIN_SCALE and 1)
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

				// Fade the page relative to its size.
				view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
						/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

	public class DepthPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.75f;

		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 0) { // [-1,0]
				// Use the default slide transition when moving to the left page
				view.setAlpha(1);
				view.setTranslationX(0);
				view.setScaleX(1);
				view.setScaleY(1);

			} else if (position <= 1) { // (0,1]
				// Fade the page out.
				view.setAlpha(1 - position);

				// Counteract the default slide transition
				view.setTranslationX(pageWidth * -position);

				// Scale the page down (between MIN_SCALE and 1)
				float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
						* (1 - Math.abs(position));
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}
}

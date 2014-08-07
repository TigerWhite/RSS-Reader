package ltt.intership.androidlayout;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import ltt.intership.R;
import ltt.intership.activity.ListRssActivity;
import ltt.intership.datahelper.DatabaseHandler;
import ltt.intership.searchrss.SearchActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

public class MetroActivity extends Activity implements OnClickListener,
		OnLongClickListener {
	private static final String TAG = "MainActivity";
	private List<MetroItem> mMetroItem;
	DatabaseHandler db;
	MetroLayout mListParam;
	String url = null;
	Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metro);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// mMetroItem = createMetro();
//		 this.deleteDatabase("RSSSearch");

		File database = this.getDatabasePath("RSSSearch");
		if (!database.exists()) {
			db = new DatabaseHandler(this);
			db.initDefaultMetroItem();
		} else
			db = new DatabaseHandler(this);
		mMetroItem = db.getAllMetroItem();
		// Intent i = getIntent();
		// url = i.getStringExtra("url");
		// Log.d("str", "newstr: "+url);
		// if(url != null){
		// db.addData(new MetroItem(this, "title", R.drawable.selector_orange,
		// url, mMetroItem.size()+1));
		// }
		// mMetroItem = db.getAllMetroItem();
		//
		// Log.d("tag", "sizeMetro2: "+ mMetroItem.size());
		mMetroItem
				.add(new MetroItem(
						this,
						"Add",
						R.drawable.selector_add,
						"http://www.thanhnsfsdien.comnnn.vn/_layouts/newsrss.aspx?Channel=Gi%C3%A1o+d%E1%BB%A5c",
						mMetroItem.size() + 1));
		generateView();
		// new LoadOnTableLayout().execute();
	}

	private void generateView() {

		final RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.relative_layout);
		ViewTreeObserver vto = mainLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				final int oneUnitWidth = mainLayout.getMeasuredWidth() / 3;
				final int oneUnitHeight = mainLayout.getMeasuredHeight();
				Log.i(TAG, "Unit Width: " + oneUnitWidth);
				Log.i(TAG, "Unit Height: " + oneUnitHeight);

				// ///////////////////////////////////////
				// Log.d("tag", "sizemMetro: "+ mMetroItem.size());
				mListParam = new MetroLayout(oneUnitWidth, oneUnitHeight);
				List<RelativeLayout.LayoutParams> temp = mListParam
						.getMetroLayoutParamsMap();

				Iterator<MetroItem> it = mMetroItem.iterator();
				MetroItem tempTag;

				int i = 0;
				// id range have to >0
				while (it.hasNext()) {
					Log.d("tag", "getId + add: " + mMetroItem.get(i).getId());
					tempTag = (MetroItem) it.next();
					tempTag.setLayoutParams(temp.get(i));
					mainLayout.addView(mMetroItem.get(i));

					i++;
				}
				// //////////////////////////////////

				/***************************************************************/
				/**
				 * Delete tree observer
				 */
				ViewTreeObserver obs = mainLayout.getViewTreeObserver();
				obs.removeGlobalOnLayoutListener(this);
			}
		});

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mMetroItem.size()) {
			// add new subject into Metro UI
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra("flag", "1");
			startActivity(intent);
			finish();
		}
		// param of get() 0 -> n, param of getId() 1 -> n
		else {
			// Uri uri =
			// Uri.parse(urlMaker(mMetroItem.get(v.getId()-1).getLink()));
			// Log.d("tag", "idof: "+ v.getId());
			// this.startActivity(new Intent(Intent.ACTION_VIEW, uri));
			String uri = mMetroItem.get(v.getId() - 1).getLink();
			String cate = mMetroItem.get(v.getId() - 1).getTitle();
			Intent i = new Intent(this, ListRssActivity.class);
			i.putExtra("url", uri);
			i.putExtra("category", cate);
			startActivity(i);
		}
		// case 9:
		// Intent intent = getIntent();
		// finish();
		// startActivity(intent);
		// break;

	}

	@Override
	public boolean onLongClick(View view) {
		// TODO Auto-generated method stub
		// Toast.makeText(getBaseContext(), "Long Clicked",
		// Toast.LENGTH_SHORT).show();
		Log.d("tag", "mMetroSize onlongclick: " + mMetroItem.size());
		if (view.getId() == mMetroItem.size()) {
			// add new subject into Metro UI
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra("flag", "1");
			startActivity(intent);
			finish();
		} else {
			// //// can delete this code if check all exception...
			if (view.getId() > 10)
				showAlertDialog(view);
		}
		// case 9:
		// Intent intent = getIntent();
		// finish();
		// startActivity(intent);
		// break;
		return true;
	}

	// private List<MetroItem> createMetro(){
	// //create the list of tags with popularity values and related url
	// List<MetroItem> tempList = new ArrayList<MetroItem>();
	// String mDrawableName = "selector_orange";
	// int resID = getResources().getIdentifier(mDrawableName , "drawable",
	// getPackageName());
	// tempList.add(new MetroItem(this, resID, 1));
	// tempList.add(new MetroItem(this, R.drawable.selector_blue1, 2));
	// tempList.add(new MetroItem(this, R.drawable.selector_green2, 3));
	// tempList.add(new MetroItem(this, R.drawable.selector_blue3, 4));
	// tempList.add(new MetroItem(this, R.drawable.selector_green1, 5));
	// tempList.add(new MetroItem(this, R.drawable.selector_blue3, 6));
	// tempList.add(new MetroItem(this, R.drawable.selector_pink, 7));
	// tempList.add(new MetroItem(this, R.drawable.selector_green3, 8));
	// tempList.add(new MetroItem(this, R.drawable.selector_yellow, 9));
	// tempList.add(new MetroItem(this, R.drawable.selector_blue4, 10));
	// // tempList.add(new MetroItem(this, R.drawable.selector_blue4, 11));
	//
	// return tempList;
	// }

	String urlMaker(String url) {
		if ((url.substring(0, 7).equalsIgnoreCase("http://"))
				|| (url.substring(0, 8).equalsIgnoreCase("https://")))
			return url;
		else
			return "http://" + url;
	}

	public void showAlertDialog(final View view) {
		AlertDialog.Builder b = new AlertDialog.Builder(this);

		b.setTitle("Delete Item");
		b.setMessage("Are you sure you want to delete?");
		b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// RelativeLayout main = (RelativeLayout) view.getParent();
				// main.removeView(view);
				// main.invalidate();
				//
				// db.deleteMetroItem(mMetroItem.get(view.getId() - 1));
				db.deleteMetroItem(mMetroItem.get(view.getId() - 1));
				mMetroItem.remove(view.getId() - 1);

				RelativeLayout main = (RelativeLayout) view.getParent();
				main.removeView(view);
				main.invalidate();
				// Intent intent = getIntent();
				// finish();
				// startActivity(intent);
				// finish();
			}
		});
		b.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which)

			{

				dialog.cancel();

			}

		});

		b.create().show();
	}

	private class LoadOnTableLayout extends AsyncTask<Void, MetroItem, Void> {

		public RelativeLayout mainLayout;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// /////////////////////////////////////
			List<RelativeLayout.LayoutParams> temp = mListParam
					.getMetroLayoutParamsMap();
			MetroItem mMetro;
			int sum = db.getListCount();
			Log.d("tag", "db.size: " + sum);
			for (int i = 0; i < sum; i++) {
				mMetro = db.getMetroItem(i + 1);
				mMetro.setLayoutParams(temp.get(i));
				publishProgress(mMetro);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(final MetroItem... params) {
			// TODO Auto-generated method stub

			super.onProgressUpdate(params);
			mainLayout = (RelativeLayout) findViewById(R.id.relative_layout);
			ViewTreeObserver vto = mainLayout.getViewTreeObserver();
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {

					final int oneUnitWidth = mainLayout.getMeasuredWidth() / 3;
					final int oneUnitHeight = mainLayout.getMeasuredHeight();
					Log.i(TAG, "Unit Width: " + oneUnitWidth);
					Log.i(TAG, "Unit Height: " + oneUnitHeight);

					mListParam = new MetroLayout(oneUnitWidth, oneUnitHeight);

					mainLayout.addView(params[0]);

					ViewTreeObserver obs = mainLayout.getViewTreeObserver();
					obs.removeGlobalOnLayoutListener(this);
				}
			});

		}

		@Override
		protected void onPostExecute(Void args) {

		}
	}

	public void addSocialMetroItem(int[] params) {
		for (int i = 1; i <= params.length; i++) {
			if (params[i] == 1)
				switch (i) {
				case 0:
					db.addData(new MetroItem(this, "Facebook",
							R.drawable.selector_orange, "facebook", 11));
					break;
				case 1:
					db.addData(new MetroItem(this, "Twiter",
							R.drawable.selector_orange, "twiter", 11));
					break;
				case 2:
					db.addData(new MetroItem(this, "Google+",
							R.drawable.selector_orange, "google+", 11));
					break;
				}

		}

	}
}
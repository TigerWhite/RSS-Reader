package com.example.androidlayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.datahelper.DatabaseHandler;
import com.example.searchrss.SearchActivity;

public class MainActivity extends Activity implements OnClickListener, OnLongClickListener {
	private static final String TAG = "MainActivity";
	private List<MetroItem> mMetroItem;
	DatabaseHandler db;
	MetroLayout mListParam;
	String url = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		mMetroItem = createMetro();
//		this.deleteDatabase("RSSSearch");
		
		File database=this.getDatabasePath("RSSSearch");
		if(!database.exists()){
			db = new DatabaseHandler(this);
			db.initDefaultMetroItem();
		}
		else
		db = new DatabaseHandler(this);
		mMetroItem = db.getAllMetroItem();
		Intent i = getIntent();
		url = i.getStringExtra("url");
//		Log.d("str", "newstr: "+url);
//		if(url != null){
//		db.addData(new MetroItem(this, "title", R.drawable.selector_orange, url, mMetroItem.size()+1));
//		}
		mMetroItem = db.getAllMetroItem();
		
		Log.d("tag", "sizeMetro2: "+ mMetroItem.size());
		mMetroItem.add(new MetroItem(this, R.drawable.selector_add,  mMetroItem.size()+1));
		generateView();
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
				
//				Log.d("tag", "sizemMetro: "+ mMetroItem.size());
				mListParam = new MetroLayout(oneUnitWidth, oneUnitHeight);
				List<RelativeLayout.LayoutParams> temp = mListParam.getMetroLayoutParamsMap();

				
				Iterator<MetroItem> it = mMetroItem.iterator();
				MetroItem tempTag;
				
				
				int i = 0;
					// id range have to >0
				while (it.hasNext()) {
					Log.d("tag", "getId + add: "+ mMetroItem.get(i).getId());
					tempTag = (MetroItem) it.next();
					tempTag.setLayoutParams(temp.get(i));
					mainLayout.addView(mMetroItem.get(i));
					i++;
				}
				

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
		if(v.getId() == mMetroItem.size()) {
			// add new subject into Metro UI
			Intent intent = new Intent(this, SearchActivity.class);
		    startActivity(intent);
		    finish();
		}
		//param of get() 0 -> n, param of getId() 1 -> n
		else{
			Uri uri = Uri.parse(urlMaker(mMetroItem.get(v.getId()-1).getLink()));
			Log.d("tag", "idof: "+ v.getId());
			this.startActivity(new Intent(Intent.ACTION_VIEW, uri));
			}
//		case 9:
//			   Intent intent = getIntent();
//			    finish();
//			    startActivity(intent);
//			break;
		
	}
	@Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub
//        Toast.makeText(getBaseContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
		Log.d("tag", "mMetroSize onlongclick: "+ mMetroItem.size());
        if(v.getId() == mMetroItem.size()) {
			// add new subject into Metro UI
			Intent intent = new Intent(this, SearchActivity.class);
		    startActivity(intent);
		    finish();
		}
		else{
//			Uri uri = Uri.parse(urlMaker(mMetroItem.get(v.getId()-1).getLink()));
//			this.startActivity(new Intent(Intent.ACTION_VIEW, uri));

			db.deleteMetroItem(mMetroItem.get(v.getId()-1));
			Intent intent = getIntent();
		    finish();
		    startActivity(intent);
		    finish();
			}
//		case 9:
//			   Intent intent = getIntent();
//			    finish();
//			    startActivity(intent);
//			break;
        return true;
    }
	private List<MetroItem> createMetro(){
		//create the list of tags with popularity values and related url
		List<MetroItem> tempList = new ArrayList<MetroItem>();
		String mDrawableName = "selector_orange";
		int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
		tempList.add(new MetroItem(this, resID, 1));
		tempList.add(new MetroItem(this, R.drawable.selector_blue1, 2));
		tempList.add(new MetroItem(this, R.drawable.selector_green2, 3));
		tempList.add(new MetroItem(this, R.drawable.selector_blue3, 4));
		tempList.add(new MetroItem(this, R.drawable.selector_green1, 5));
		tempList.add(new MetroItem(this, R.drawable.selector_blue3, 6));
		tempList.add(new MetroItem(this, R.drawable.selector_pink, 7));
		tempList.add(new MetroItem(this, R.drawable.selector_green3, 8));
		tempList.add(new MetroItem(this, R.drawable.selector_yellow, 9));
		tempList.add(new MetroItem(this, R.drawable.selector_blue4, 10));
//		tempList.add(new MetroItem(this, R.drawable.selector_blue4, 11));

		return tempList;
	}

	String urlMaker(String url) {
		if ((url.substring(0, 7).equalsIgnoreCase("http://"))
				|| (url.substring(0, 8).equalsIgnoreCase("https://")))
			return url;
		else
			return "http://" + url;
	}
}
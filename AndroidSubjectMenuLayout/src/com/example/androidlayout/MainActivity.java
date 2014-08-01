package com.example.androidlayout;

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
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private List<MetroItem> mMetroItem;
	MetroLayout mListParam;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMetroItem = createMetro();
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
				
				

				mListParam = new MetroLayout(oneUnitWidth, oneUnitHeight);
				List<RelativeLayout.LayoutParams> temp = mListParam.getMetroLayoutParamsMap();

				Log.d("tag", "size1: "+ temp.size());
				Iterator it = mMetroItem.iterator();
				MetroItem tempTag;
				
				int i = 0;
					// id range have to >0
				while (it.hasNext()) {
					tempTag = (MetroItem) it.next();
					tempTag.setLayoutParams(temp.get(i));
					mainLayout.addView(mMetroItem.get(i));
					i++;
				}
				
//				mListParam.getListParams().get(i)
//				/**
//				 * 1
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams otelParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth*2, oneUnitWidth*2);
//				otelParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//				otelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//				otelParams.addRule(RelativeLayout.RIGHT_OF, 0);
//				otelParams.addRule(RelativeLayout.BELOW, 0);
//				 otelParams.setMargins(0, 0, 10, 10);
//				mMetroItem.get(0).setLayoutParams(otelParams);
//				/***************************************************************/
//
//				/**
//				 * 2
//				 ***************************************************************/
//				RelativeLayout.LayoutParams otherParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
////				otherParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//				otherParams.addRule(RelativeLayout.RIGHT_OF, 1);
//				otherParams.addRule(RelativeLayout.BELOW, 0);
//				otherParams.setMargins(10, -20, 0, 10);
//				mMetroItem.get(1).setLayoutParams(otherParams);
//				/***************************************************************/
//
//				/**
//				 * 3
//				 ***************************************************************/
//				RelativeLayout.LayoutParams animationParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
//				animationParams.addRule(RelativeLayout.BELOW, 2);
//				animationParams.addRule(RelativeLayout.RIGHT_OF, 1);
//				animationParams.setMargins(10, 10, 0, 20);
//
//				mMetroItem.get(2).setLayoutParams(animationParams);
//				/***************************************************************/
//
//				/**
//				 * 4
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams wtdParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
//				wtdParams.addRule(RelativeLayout.BELOW, 1);
//				wtdParams.addRule(RelativeLayout.RIGHT_OF, 0);
//				wtdParams.setMargins(0, 10, 10, 10);
//				mMetroItem.get(3).setLayoutParams(wtdParams);
//				/***************************************************************/
//
//				/**
//				 * 5
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth *2, oneUnitWidth);
//				tvParams.addRule(RelativeLayout.BELOW, 1);
//				tvParams.addRule(RelativeLayout.RIGHT_OF, 4);
//				tvParams.setMargins(10, 10, 0, 10);
//				mMetroItem.get(4).setLayoutParams(tvParams);
//
//				/***************************************************************/
//
//				/**
//				 * 6
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams dealsParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth *2, oneUnitWidth);
//				dealsParams.addRule(RelativeLayout.RIGHT_OF, 0);
//				dealsParams.addRule(RelativeLayout.BELOW, 4);
//				dealsParams.setMargins(0, 10, 10, 10);
//				mMetroItem.get(5).setLayoutParams(dealsParams);
//
//				/***************************************************************/
//				/**
//				 * 7
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams weatherParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
//				weatherParams.addRule(RelativeLayout.RIGHT_OF, 6);
//				weatherParams.addRule(RelativeLayout.BELOW, 5);
//				weatherParams.setMargins(10, 10, 0, 10);
//				mMetroItem.get(6).setLayoutParams(weatherParams);
//
//				/***************************************************************/
//
//				/**
//				 * 8
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams alacarteParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
//				alacarteParams.addRule(RelativeLayout.BELOW, 6);
//				alacarteParams.addRule(RelativeLayout.RIGHT_OF, 0);
////				alacarteParams.addRule(RelativeLayout.BELOW, View6.getId());
//				alacarteParams.setMargins(-20, 10, 10, 10);
//				mMetroItem.get(7).setLayoutParams(alacarteParams);
//
//				/***************************************************************/
//
//				/**
//				 * 9
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams wteParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
//				wteParams.addRule(RelativeLayout.RIGHT_OF, 8);
//				wteParams.addRule(RelativeLayout.BELOW, 7);
//				wteParams.setMargins(10, 10, 10, 10);
//				mMetroItem.get(8).setLayoutParams(wteParams);
//
//				/***************************************************************/
//				/**
//				 * 10
//				 ***************************************************************/
//				final RelativeLayout.LayoutParams myInfoParams = new RelativeLayout.LayoutParams(
//						oneUnitWidth, oneUnitWidth);
//				myInfoParams.addRule(RelativeLayout.BELOW, 7);
//				myInfoParams.addRule(RelativeLayout.RIGHT_OF, 9);
//				myInfoParams.setMargins(10, 10, 0, 0);
//
//				mMetroItem.get(9).setLayoutParams(myInfoParams);

				/***************************************************************/
				/**
				 * Delete tree observer
				 */
				ViewTreeObserver obs = mainLayout.getViewTreeObserver();
				obs.removeGlobalOnLayoutListener(this);
			}
		});

//		mainLayout.addView(View1);
//		mainLayout.addView(View2);
//		mainLayout.addView(mMetroItem.get(0));
//		mainLayout.addView(mMetroItem.get(1));
//		mainLayout.addView(mMetroItem.get(2));
//		mainLayout.addView(mMetroItem.get(3));
//		mainLayout.addView(mMetroItem.get(4));
//		mainLayout.addView(mMetroItem.get(5));
//		mainLayout.addView(mMetroItem.get(6));
//		mainLayout.addView(mMetroItem.get(7));
//		mainLayout.addView(mMetroItem.get(8));
//		mainLayout.addView(mMetroItem.get(9));
//		mainLayout.addView(View7);
//		mainLayout.addView(View8);
//		mainLayout.addView(View9);
//		mainLayout.addView(View10);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 1:
			Uri uri = Uri.parse(urlMaker(mMetroItem.get(0).getLink()));
			this.startActivity(new Intent(Intent.ACTION_VIEW, uri));
			break;
		case 9:
			   Intent intent = getIntent();
			    finish();
			    startActivity(intent);
			break;
		case 2:
			Toast.makeText(this, "Pressed 2", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(this, "Pressed 3", Toast.LENGTH_SHORT).show();
			break;
		case 4:
			Toast.makeText(this, "Pressed 4", Toast.LENGTH_SHORT).show();
			break;
		case 5:
			Toast.makeText(this, "Pressed 5", Toast.LENGTH_SHORT).show();
			break;
		case 6:
			Toast.makeText(this, "Pressed 6", Toast.LENGTH_SHORT).show();
			break;
		case 7:
			Toast.makeText(this, "Pressed 7", Toast.LENGTH_SHORT).show();
			break;
		case 8:
			Toast.makeText(this, "Pressed 8", Toast.LENGTH_SHORT).show();
			break;
		case 10:
			Toast.makeText(this, "Pressed 10", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	private List<MetroItem> createMetro(){
		//create the list of tags with popularity values and related url
		List<MetroItem> tempList = new ArrayList<MetroItem>();

		tempList.add(new MetroItem(this, R.drawable.selector_orange, 1));
		tempList.add(new MetroItem(this, R.drawable.selector_blue1, 2));
		tempList.add(new MetroItem(this, R.drawable.selector_green2, 3));
		tempList.add(new MetroItem(this, R.drawable.selector_blue3, 4));
		tempList.add(new MetroItem(this, R.drawable.selector_green1, 5));
		tempList.add(new MetroItem(this, R.drawable.selector_blue3, 6));
		tempList.add(new MetroItem(this, R.drawable.selector_pink, 7));
		tempList.add(new MetroItem(this, R.drawable.selector_green3, 8));
		tempList.add(new MetroItem(this, R.drawable.selector_yellow, 9));
		tempList.add(new MetroItem(this, R.drawable.selector_blue4, 10));
		tempList.add(new MetroItem(this, R.drawable.selector_blue4, 11));

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
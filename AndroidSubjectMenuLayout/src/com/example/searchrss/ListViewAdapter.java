package com.example.searchrss;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidlayout.R;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
	}

	@Override
	public int getCount() {
		Log.d("tag", "tag3" + data.size());
		return data.size();
		
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView url;
		TextView title;
		TextView description;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		url = (TextView) itemView.findViewById(R.id.url);
		title = (TextView) itemView.findViewById(R.id.title);
		description = (TextView) itemView.findViewById(R.id.description);


		// Capture position and set results to the TextViews
		url.setText(resultp.get(SearchActivity.URL));
Log.d("tag2", "tag2 "+resultp.get(SearchActivity.URL));
		title.setText(resultp.get(SearchActivity.TITLE));
		description.setText(resultp.get(SearchActivity.DESCRIPTION));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
//				resultp = data.get(position);
//				Intent intent = new Intent(context, SingleItemView.class);
//				// Pass all data rank
//				intent.putExtra("rank", resultp.get(MainActivity.RANK));
//				// Pass all data country
//				intent.putExtra("country", resultp.get(MainActivity.COUNTRY));
//				// Pass all data population
//				intent.putExtra("population",resultp.get(MainActivity.POPULATION));
//				// Start SingleItemView Class
//				context.startActivity(intent);

			}
		});
		return itemView;
	}
}

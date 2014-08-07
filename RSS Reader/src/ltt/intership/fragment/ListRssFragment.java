package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.activity.ReadArticleActivity;
import ltt.intership.activity.SettingActivity;
import ltt.intership.activity.StartUpActivity;
import ltt.intership.custom.mListRssAdapter;
import ltt.intership.custom.mListRssAdapter.OnItemClick;
import ltt.intership.data.mListItem;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListRssFragment extends Fragment {
	public ListRssFragment() {
	}

	mListRssAdapter mAdapter;
	String url;
	String category;
	LinearLayout btnBack;
	ListView lvRss;
	mListItem list;
	ImageButton btnSetting;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_rss, container,
				false);

		this.url = getActivity().getIntent().getStringExtra("url");
		this.category = getActivity().getIntent().getStringExtra("category");
		
		Log.i("url received", url);
		// list view
		lvRss = (ListView) rootView.findViewById(R.id.listRss_lv);

		list = new mListItem(url);

		Log.i("get instance", "size: " + list.getList().size());

		mAdapter = new mListRssAdapter(getActivity(), R.layout.item_rss_layout,
				list);
		lvRss.setAdapter(mAdapter);

		mAdapter.setOnItemClick(new OnItemClick() {
			
			@Override
			public void onClick(int position) {
				// TODO Auto-generated method stub
				Log.i("item list view selected", "pos: " + position);
				Intent i = new Intent(getActivity(), ReadArticleActivity.class);
				i.putExtra("url", url);
				i.putExtra("position", position);
				startActivity(i);
			}
		});

		btnBack = (LinearLayout) rootView.findViewById(R.id.listRss_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
		
		((TextView)rootView.findViewById(R.id.listRss_tv_category)).setText(category);
		
		btnSetting = (ImageButton)rootView.findViewById(R.id.listRss_btn_setting);
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

}

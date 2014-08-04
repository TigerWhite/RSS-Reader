package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.activity.ReadArticleActivity;
import ltt.intership.custom.mListRssAdapter;
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
import android.widget.LinearLayout;
import android.widget.ListView;

public class ListRssFragment extends Fragment {
	public ListRssFragment() {
	}

	mListRssAdapter mAdapter;
	String url;
	LinearLayout btnBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_rss, container,
				false);

		this.url = getActivity().getIntent().getStringExtra("url");
		Log.i("url received", url);
		// list view
		ListView lvRss = (ListView) rootView.findViewById(R.id.listRss_lv);

		mListItem list = new mListItem(url);

		Log.i("get instance", "size: " + list.getList().size());

		mAdapter = new mListRssAdapter(getActivity(), R.layout.item_rss_layout,
				list);
		lvRss.setAdapter(mAdapter);
		lvRss.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Intent i = new Intent(getActivity(), ReadArticleActivity.class);
				i.putExtra("url", url);
				i.putExtra("position", pos);

				startActivity(i);
				// getActivity().finish();
			}
		});

		btnBack = (LinearLayout) rootView.findViewById(R.id.listRss_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
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

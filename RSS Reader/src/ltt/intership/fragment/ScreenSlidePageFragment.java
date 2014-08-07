package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.activity.ListRssActivity;
import ltt.intership.activity.ShowWebviewActivity;
import ltt.intership.data.mNewsInfo;
import ltt.intership.data.mRssItem;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ScreenSlidePageFragment extends Fragment {
	public static final String ARG_PAGE = "page";

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;

	private mRssItem item;
	private mNewsInfo news;

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	public static ScreenSlidePageFragment create(int pageNumber,
			mRssItem new_item, mNewsInfo newspaper) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		args.putSerializable(mRssItem.class.getName(), new_item);
		args.putSerializable(mNewsInfo.class.getName(), newspaper);
		fragment.setArguments(args);
		return fragment;
	}

	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		item = (mRssItem) getArguments().getSerializable(
				mRssItem.class.getName());
		news = (mNewsInfo) getArguments().getSerializable(
				mNewsInfo.class.getName());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_read_article, container, false);

		// Set the view of article.
		((TextView) rootView.findViewById(R.id.rss_tv_newspaper)).setText(news
				.getNewsSource().getTitle());
		Picasso.with(getActivity())
				.load(news.getNewsSource().getThumbnail())
				.into(((ImageView) rootView
						.findViewById(R.id.rss_img_newspaper)));

		Picasso.with(getActivity()).load(item.getRssItemInfo().getThumbnail())
				.into(((ImageView) rootView.findViewById(R.id.rss_img)));
		((TextView) rootView.findViewById(R.id.rss_title)).setText(item
				.getRssItemInfo().getTitle());
		((TextView) rootView.findViewById(R.id.rss_tv_publish_time))
				.setText(item.getRssItemInfo().getPubDateFormat()
						.toLocaleString());
		((TextView) rootView.findViewById(R.id.rss_content)).setText(item
				.getRssItemInfo().getDescription());
		((Button) rootView.findViewById(R.id.rss_btn_show_article))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// Intent i = new Intent(getActivity(),
						// ShowWebviewActivity.class);
						// i.putExtra("url", item.getRssItemInfo().getLink());
						// i.putExtra("category",
						// news.getNewsSource().getTitle());
						// startActivity(i);
					}
				});

		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}
}

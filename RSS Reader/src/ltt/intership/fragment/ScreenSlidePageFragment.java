package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.data.mRssItem;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
	public static final String ARG_PAGE = "page";

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;

	private static mRssItem item;

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	public static ScreenSlidePageFragment create(int pageNumber,
			mRssItem new_item) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		item = new_item;
		return fragment;
	}

	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_read_article, container, false);

		// Set the view of article.
		((ImageView) rootView.findViewById(R.id.rss_img)).setImageBitmap(item
				.renderImage());
		((TextView) rootView.findViewById(R.id.rss_title)).setText(item
				.getRssItemInfo().getTitle());
		((TextView) rootView.findViewById(R.id.rss_tv_publish_time))
				.setText(item.getRssItemInfo().getPubDate());
		((TextView) rootView.findViewById(R.id.rss_content)).setText(item
				.getRssItemInfo().getDescription());

		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}
}

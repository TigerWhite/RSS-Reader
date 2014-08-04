package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.activity.ListRssActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainFragment extends Fragment {

	private Spinner sp;
	private Button btn;
	private String url = "";

	public MainFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		sp = (Spinner) rootView.findViewById(R.id.main_spinner);

		btn = (Button) rootView.findViewById(R.id.main_btn_start_list_rss);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "start", Toast.LENGTH_SHORT)
						.show();
				Log.i("main", "start");
				handingRss();

			}
		});
		return rootView;
	}

	private void handingRss() {
		this.url = sp.getSelectedItem().toString();

		Intent i = new Intent(getActivity(), ListRssActivity.class);
		i.putExtra("url", this.url);

		startActivity(i);
		// getActivity().finish();
	}
}

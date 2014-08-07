package ltt.intership.activity;

import ltt.intership.R;
import ltt.intership.R.id;
import ltt.intership.R.layout;
import ltt.intership.R.menu;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;
import android.os.Build;

public class ShowWebviewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview);

		if (savedInstanceState == null) {
			PlaceholderFragment placeholderFragment =  new PlaceholderFragment();
			placeholderFragment.setArguments(getIntent().getExtras());
			getFragmentManager().beginTransaction()
					.add(R.id.container, placeholderFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_webview, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private WebView webView;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_webview,
					container, false);
			
//			String web_url = getArguments().getString("url");
//			String web_cate=getArguments().getString("category");
//			Toast.makeText(getActivity(), web_url + "-" + web_cate, Toast.LENGTH_LONG).show();
			webView = (WebView) rootView.findViewById(R.id.showWebview_web);
			// webView.getSettings().setJavaScriptEnabled(true);
			// webView.loadUrl(web_url);
			
			return rootView;
		}
	}

}

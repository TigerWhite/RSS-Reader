package ltt.intership.activity;

import ltt.intership.R;
import ltt.intership.R.id;
import ltt.intership.R.layout;
import ltt.intership.R.menu;
import ltt.intership.utils.Config;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Build;

public class AccountProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_profile);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new AccProfileFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_profile, menu);
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
	public static class AccProfileFragment extends Fragment implements
			OnClickListener {
		ImageButton btnBack;
		Button btnLogout;
		String type;

		public AccProfileFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_account_profile,
					container, false);
			type = getActivity().getIntent().getStringExtra(
					Config.PREFS_TYPE_ACC);

			btnBack = (ImageButton) rootView
					.findViewById(R.id.accProfile_btn_back);
			btnBack.setOnClickListener(this);

			btnLogout = (Button) rootView.findViewById(R.id.accProfile_logout);
			btnLogout.setOnClickListener(this);
			
			((TextView)rootView.findViewById(R.id.accProfile_social_name)).setText(type);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.accProfile_btn_back:
				startActivity(new Intent(getActivity(),
						AccountManageActivity.class));
				getActivity().finish();
				break;
			case R.id.accProfile_logout:

				logout();

				startActivity(new Intent(getActivity(),
						AccountManageActivity.class));
				getActivity().finish();
				break;
			default:
				break;
			}
		}

		private void logout() {
			if (Config.PREFS_FACEBOOK.equalsIgnoreCase(type)) {
				
				return;
			}

			if (Config.PREFS_GOOGLE.equalsIgnoreCase(type)) {

				return;
			}

			if (Config.PREFS_TWITTER.equalsIgnoreCase(type)) {

				return;
			}

			if (Config.PREFS_LTT.equalsIgnoreCase(type)) {

				return;
			}
		}
	}

}

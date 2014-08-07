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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.os.Build;

public class AccountManageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_manage);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new AccManageFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_manage, menu);
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
	public static class AccManageFragment extends Fragment implements
			OnClickListener {
		SharedPreferences mPrefs;
		ImageButton btnBack;

		LinearLayout btn_accFb, btn_accGoogle, btn_accTwitter, btn_accLtt;
		LinearLayout btn_new_accFb, btn_new_accGoogle, btn_new_accTwitter;

		public AccManageFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_account_manage,
					container, false);
			mPrefs = getActivity().getSharedPreferences(Config.GLOBAL_PREFS, 0);

			btnBack = (ImageButton) rootView
					.findViewById(R.id.accManage_btn_back);
			btnBack.setOnClickListener(this);

			btn_accFb = (LinearLayout) rootView
					.findViewById(R.id.accManage_fb_account);
			btn_accFb.setOnClickListener(this);

			btn_accGoogle = (LinearLayout) rootView
					.findViewById(R.id.accManage_google_account);
			btn_accGoogle.setOnClickListener(this);

			btn_accTwitter = (LinearLayout) rootView
					.findViewById(R.id.accManage_twitter_account);
			btn_accTwitter.setOnClickListener(this);

			btn_accLtt = (LinearLayout) rootView
					.findViewById(R.id.accManage_ltt_account);
			btn_accLtt.setOnClickListener(this);

			btn_new_accFb = (LinearLayout) rootView
					.findViewById(R.id.accManage_new_fb_account);
			btn_new_accFb.setOnClickListener(this);

			btn_new_accGoogle = (LinearLayout) rootView
					.findViewById(R.id.accManage_new_google_account);
			btn_new_accGoogle.setOnClickListener(this);

			btn_new_accTwitter = (LinearLayout) rootView
					.findViewById(R.id.accManage_new_twitter_account);
			btn_new_accTwitter.setOnClickListener(this);

			checkLogin();
			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.accManage_btn_back:
				startActivity(new Intent(getActivity(), SettingActivity.class));
				getActivity().finish();
				break;
			case R.id.accManage_fb_account:
				showAccProfile(Config.PREFS_FACEBOOK);
				break;
			case R.id.accManage_google_account:
				showAccProfile(Config.PREFS_GOOGLE);
				break;
			case R.id.accManage_twitter_account:
				showAccProfile(Config.PREFS_TWITTER);
				break;
			case R.id.accManage_ltt_account:
				showAccProfile(Config.PREFS_LTT);
				break;

			case R.id.accManage_new_fb_account:
				break;
			case R.id.accManage_new_google_account:
				break;
			case R.id.accManage_new_twitter_account:
				break;
			default:
				break;
			}

		}

		private void checkLogin() {
			if (mPrefs.getString(Config.PREFS_LOGIN_FACEBOOK, null) != null) {
				btn_accFb.setVisibility(View.VISIBLE);
				btn_new_accFb.setVisibility(View.GONE);
			} else {
				btn_accFb.setVisibility(View.GONE);
				btn_new_accFb.setVisibility(View.VISIBLE);
			}
			if (mPrefs.getString(Config.PREFS_LOGIN_GOOGLE, null) != null) {
				btn_accGoogle.setVisibility(View.VISIBLE);
				btn_new_accGoogle.setVisibility(View.GONE);
			} else {
				btn_accGoogle.setVisibility(View.GONE);
				btn_new_accGoogle.setVisibility(View.VISIBLE);
			}

			if (mPrefs.getString(Config.PREFS_LOGIN_TWITTER, null) != null) {
				btn_accTwitter.setVisibility(View.VISIBLE);
				btn_new_accTwitter.setVisibility(View.GONE);
			} else {
				btn_accTwitter.setVisibility(View.GONE);
				btn_new_accTwitter.setVisibility(View.VISIBLE);
			}
			if (mPrefs.getString(Config.PREFS_LOGIN_LTT, null) != null) {
				btn_accLtt.setVisibility(View.VISIBLE);
			}
		}

		private void showAccProfile(String type_acc) {
			Intent i = new Intent(getActivity(), AccountProfileActivity.class);
			i.putExtra(Config.PREFS_TYPE_ACC, type_acc);
			startActivity(i);
			getActivity().finish();
		}
	}
}

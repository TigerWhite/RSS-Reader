package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.activity.AccountManageActivity;
import ltt.intership.activity.StartUpActivity;
import ltt.intership.utils.Config;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingFragment extends Fragment implements OnClickListener {

	LinearLayout btn_acc, btn_lang, btn_theme, btn_logout;
	ImageButton btn_back;
	SharedPreferences mPrefs;
	
	public SettingFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_setting, container,
				false);
		mPrefs = getActivity().getSharedPreferences(Config.GLOBAL_PREFS, 0);
		
		btn_back = (ImageButton) rootView.findViewById(R.id.setting_btn_back);
		btn_back.setOnClickListener(this);

		btn_acc = (LinearLayout) rootView
				.findViewById(R.id.setting_btn_account);
		btn_acc.setOnClickListener(this);

		btn_lang = (LinearLayout) rootView
				.findViewById(R.id.setting_btn_language);
		btn_lang.setOnClickListener(this);

		btn_theme = (LinearLayout) rootView
				.findViewById(R.id.setting_btn_theme);
		btn_theme.setOnClickListener(this);

		btn_logout = (LinearLayout) rootView
				.findViewById(R.id.setting_btn_logout);
		btn_logout.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_btn_back:
			getActivity().finish();
			break;
		case R.id.setting_btn_account:
			Intent i = new Intent(getActivity(), AccountManageActivity.class);
			startActivity(i);
			getActivity().finish();
			break;
		case R.id.setting_btn_language:
			break;
		case R.id.setting_btn_theme:
			break;
		case R.id.setting_btn_logout:
			mPrefs.edit().clear().commit();
			// Intent i2 = new Intent(getActivity(), StartUpActivity.class);
			// startActivity(i2);
			// getActivity().finish();
			break;
		default:
			break;
		}

	}
}

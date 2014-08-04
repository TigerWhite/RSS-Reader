package ltt.intership.fragment;

import java.util.Arrays;

import ltt.intership.R;
import ltt.intership.activity.MainActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.widget.LoginButton;

public class StartUpFragment extends Fragment implements View.OnClickListener {
	private ProgressBar pgBar;
	private LinearLayout llAll;
	private Animation bslide, islide;

	public StartUpFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_start_up, container,
				false);

		initView(rootView);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LoadingContent load = new LoadingContent();
		load.execute(2);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startup_googlePlus_sign_in:
			// dang nhap bang tai khoan google-plus
			Log.i("google plus sign in button", "clicked");
			LoginSuccess();
			break;
		case R.id.startup_facebook_sign_in:
			// dang nhap bang tai khoan facebook
			Log.i("fb sign in button", "clicked");
			break;
		case R.id.startup_twitter_sign_in:
			// dang nhap bang tai khoan twitter
			Log.i("twitter sign in button", "clicked");
			break;

		default:
			break;
		}
	}

	private class LoadingContent extends AsyncTask<Integer, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Integer... params) {
			for (int i = 0; i < params[0]; i++) {
				SystemClock.sleep(1000);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pgBar.setVisibility(View.GONE);
			llAll.setAnimation(bslide);
			llAll.setVisibility(View.VISIBLE);
			Log.i("progess bar", "completed");
		}
	}

	/**
	 * Khoi tao cac nut va hieu ung
	 * @param view
	 */
	private void initView(View view) {
		bslide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_from_bottom);
		islide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.hyperspace_out);

		llAll = (LinearLayout) view.findViewById(R.id.startup_all_part);
		llAll.setVisibility(View.GONE);

		pgBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		pgBar.setVisibility(View.VISIBLE);

		LoginButton loginbutton = (LoginButton) view
				.findViewById(R.id.startup_facebook_sign_in);
		loginbutton.setReadPermissions(Arrays.asList("user_location",
				"user_birthday", "user_likes", "read_stream"));
		loginbutton.setOnClickListener(this);
		
		Button btnGplusSignIn = (Button) view
				.findViewById(R.id.startup_googlePlus_sign_in);
		btnGplusSignIn.setOnClickListener(this);

		Button btnTwitterSignIn = (Button) view
				.findViewById(R.id.startup_twitter_sign_in);
		btnTwitterSignIn.setOnClickListener(this);

	}
	
	/**
	 * Chuyen sang activity moi sau khi dang nhap thanh cong
	 */
	private void LoginSuccess(){
		
		startActivity(new Intent(getActivity(), MainActivity.class));
		getActivity().finish();
		
	}
	
}

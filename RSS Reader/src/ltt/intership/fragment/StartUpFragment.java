package ltt.intership.fragment;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import ltt.intership.R;
import ltt.intership.activity.MainActivity;
import ltt.intership.activity.StartUpActivity.mFragmentReceiver;
import ltt.intership.connection.PrefManagement;
import ltt.intership.connection.UserFunctions;
import ltt.intership.utils.Config;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.facebook.widget.LoginButton;

public class StartUpFragment extends Fragment implements View.OnClickListener,
		mFragmentReceiver {
	private ProgressBar pgBar;
	private LinearLayout llLoginSocial;
	private LinearLayout llLoginLtt;
	private LinearLayout llSignIn;
	private LinearLayout llGetStart;
	private LinearLayout llSignUp;

	private LoginButton FbBtnLogin;

	private EditText edSignInName;
	private EditText edSignInPass;

	private EditText edRegisterName;
	private EditText edRegisterEmail;
	private EditText edRegisterPass;

	private Animation bslide, islide, lslide, rslide;

	private Twitter twitterConnection;
	private RequestToken twitterRequestToken;
	private SharedPreferences twitterPrefs;

	private boolean LoginFacebook = false;
	private boolean LoginGoogle = false;
	private boolean LoginTwitter = false;
	private boolean LoginLtt = false;

	private static final String login_succes = "login_success";
	private static final int notLogin = -1;
	private static final int mFACEBOOK = 0;
	private static final int mGOOGLE = 1;
	private static final int mTWITTER = 2;
	private static final int mLTT = 3;

	/*
	 * key khi dang nhap bang server cua nhom
	 */
	PrefManagement pref;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_ID = "id";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";

	public StartUpFragment() {

	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.i("mHandler", "mess: " + (String) msg.obj);
			if (((String) msg.obj).equalsIgnoreCase(login_succes)) {
				Log.i("mHandler", "para: " + msg.arg1);
				switch (msg.arg1) {
				case mFACEBOOK:
					Toast.makeText(getActivity(), "login facebook succesfull",
							Toast.LENGTH_SHORT).show();
					break;
				case mGOOGLE:
					Toast.makeText(getActivity(), "login google succesfull",
							Toast.LENGTH_SHORT).show();
					break;
				case mTWITTER:
					Toast.makeText(getActivity(), "login twitter succesfull",
							Toast.LENGTH_SHORT).show();
					break;
				case mLTT:
					Toast.makeText(getActivity(),
							"login ltt account succesfull", Toast.LENGTH_SHORT)
							.show();
					break;
				default:
					break;
				}
				LoginSuccess();
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_start_up, container,
				false);

		initView(rootView);
		initParameter();

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
			sendNewMss(login_succes, mGOOGLE);
			break;
		case R.id.startup_facebook_sign_in:
			// dang nhap bang tai khoan facebook
			FbBtnLogin.performClick();
			break;
		case R.id.startup_twitter_sign_in:
			// dang nhap bang tai khoan twitter
			startLoginByTwitterAcc();
			break;

		case R.id.startup_sign_in:
			llLoginSocial.setAnimation(lslide);
			llLoginSocial.setVisibility(View.GONE);

			llLoginLtt.setAnimation(rslide);
			llLoginLtt.setVisibility(View.VISIBLE);
			break;
		case R.id.startup_get_started:
			sendNewMss(login_succes, notLogin);
			break;
		case R.id.startup_btn_ltt_signin:
			if (!"".equals(edSignInName.getText().toString())
					&& !"".equals(edSignInPass.getText().toString())) {
				new SigninTask().execute();
			} else {
				Toast.makeText(getActivity(), "Please input full information",
						Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.startup_btn_create_account:
			llLoginLtt.setAnimation(lslide);
			llLoginLtt.setVisibility(View.GONE);

			llSignUp.setAnimation(lslide);
			llSignUp.setVisibility(View.VISIBLE);
			break;
		case R.id.startup_btn_ltt_signup:
			if (!"".equals(edRegisterName.getText().toString())
					&& !"".equals(edRegisterEmail.getText().toString())
					&& !"".equals(edRegisterPass.getText().toString())) {
				new RegisterTask().execute();
			} else {
				Toast.makeText(getActivity(), "Please input full information",
						Toast.LENGTH_LONG).show();
			}
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
			llLoginSocial.setAnimation(bslide);
			llLoginSocial.setVisibility(View.VISIBLE);
			Log.i("progess bar", "completed");
		}
	}

	/**
	 * Khoi tao cac nut va hieu ung
	 * 
	 * @param view
	 */
	private void initView(View view) {
		bslide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_from_bottom);
		islide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.hyperspace_out);
		lslide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_left_to_right);
		lslide.setDuration(1000);
		rslide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_from_right_to_left);
		rslide.setDuration(1000);

		llLoginSocial = (LinearLayout) view
				.findViewById(R.id.startup_login_social_account);
		llLoginSocial.setVisibility(View.GONE);

		llLoginLtt = (LinearLayout) view
				.findViewById(R.id.startup_login_ltt_account);
		llLoginLtt.setVisibility(View.GONE);

		llSignUp = (LinearLayout) view
				.findViewById(R.id.startup_create_ltt_account);
		llSignUp.setVisibility(View.GONE);

		llSignIn = (LinearLayout) view.findViewById(R.id.startup_sign_in);
		llSignIn.setOnClickListener(this);

		llGetStart = (LinearLayout) view.findViewById(R.id.startup_get_started);
		llGetStart.setOnClickListener(this);

		pgBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		pgBar.setVisibility(View.VISIBLE);

		FbBtnLogin = (LoginButton) view
				.findViewById(R.id.startup_facebook_login_button);
		FbBtnLogin.setReadPermissions(Arrays.asList("user_location",
				"user_birthday", "user_likes", "read_stream"));

		LinearLayout btnFbSignIn = (LinearLayout) view
				.findViewById(R.id.startup_facebook_sign_in);
		btnFbSignIn.setOnClickListener(this);

		LinearLayout btnGplusSignIn = (LinearLayout) view
				.findViewById(R.id.startup_googlePlus_sign_in);
		btnGplusSignIn.setOnClickListener(this);

		LinearLayout btnTwitterSignIn = (LinearLayout) view
				.findViewById(R.id.startup_twitter_sign_in);
		btnTwitterSignIn.setOnClickListener(this);

		Button btnLttSignIn = (Button) view
				.findViewById(R.id.startup_btn_ltt_signin);
		btnLttSignIn.setOnClickListener(this);

		Button btnCreateLttAcc = (Button) view
				.findViewById(R.id.startup_btn_create_account);
		btnCreateLttAcc.setOnClickListener(this);

		Button btnLttSignUp = (Button) view
				.findViewById(R.id.startup_btn_ltt_signup);
		btnLttSignUp.setOnClickListener(this);

		edSignInName = (EditText) view.findViewById(R.id.startup_edit_username);
		edSignInPass = (EditText) view.findViewById(R.id.startup_edit_password);

		edRegisterName = (EditText) view
				.findViewById(R.id.startup_ed_create_username);
		edRegisterEmail = (EditText) view
				.findViewById(R.id.startup_ed_create_email);
		edRegisterPass = (EditText) view
				.findViewById(R.id.startup_ed_create_password);

	}

	private void initParameter() {
		twitterPrefs = getActivity().getSharedPreferences(Config.GLOBAL_PREFS,
				0);
		pref = new PrefManagement(getActivity());
	}

	/**
	 * Chuyen sang activity moi sau khi dang nhap thanh cong
	 */
	private void LoginSuccess() {

		startActivity(new Intent(getActivity(), MainActivity.class));
		getActivity().finish();

	}

	/**
	 * kiem tra cac tai khoan hien co
	 */
	private void checkLogin() {

	}

	private boolean isLoginTwitter() {
		if (twitterPrefs.getString(Config.TWITTER_USER_TOKEN, null) == null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isLoginFacebook() {
		return LoginFacebook;
	}

	private boolean isLoginGoogle() {
		return false;
	}

	private void startLoginByTwitterAcc() {
		if (twitterPrefs == null) {
			twitterPrefs = getActivity().getSharedPreferences(
					Config.GLOBAL_PREFS, 0);
		}
		twitterConnection = new TwitterFactory().getInstance();
		twitterConnection.setOAuthConsumer(Config.TWITTER_KEY,
				Config.TWITTER_SECRET);
		try {
			twitterRequestToken = twitterConnection
					.getOAuthRequestToken(Config.TWITTER_CALLBACK_URL);
		} catch (TwitterException te) {
			Log.e("login by twitter account", "error: " + te.getMessage());
		}

		String authURL = twitterRequestToken.getAuthenticationURL();
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authURL)));
	}

	@Override
	public void onNewIntentReceive(Intent intent) {
		Uri twitterURI = intent.getData();
		if (twitterURI != null
				&& twitterURI.toString()
						.startsWith(Config.TWITTER_CALLBACK_URL)) {
			Log.d("login by twitter account",
					"URI parameter: " + twitterURI.toString());
			String oaVerifier = twitterURI.getQueryParameter("oauth_verifier");

			try {
				AccessToken accToken = twitterConnection.getOAuthAccessToken(
						twitterRequestToken, oaVerifier);
				twitterPrefs
						.edit()
						.putString(Config.TWITTER_USER_TOKEN,
								accToken.getToken())
						.putString(Config.TWITTER_USER_SECRET,
								accToken.getTokenSecret()).commit();

				Log.e("login by twitter account",
						"Access granted: " + accToken.getToken() + "-"
								+ accToken.getTokenSecret());

				LoginTwitter = true;
				sendNewMss(login_succes, mTWITTER);
			} catch (TwitterException e) {
				Log.e("login by twitter account",
						"Failed to get access token: " + e.getMessage());
			}
		}
	}

	@Override
	public void onLoginFacebookSuccess(boolean login) {
		this.LoginFacebook = login;
		if (login == true) {
			sendNewMss(login_succes, mFACEBOOK);
		}
	}

	private void sendNewMss(String msg_content, int para) {
		Message msg = new Message();
		msg.arg1 = para;
		msg.obj = msg_content;
		mHandler.sendMessage(msg);
	}

	private class RegisterTask extends AsyncTask<Void, Void, JSONObject> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "",
					".... Processing ....", true);
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONObject json = null;

			try {
				String name = edRegisterName.getText().toString();
				String email = edRegisterEmail.getText().toString();
				String password = edRegisterPass.getText().toString();
				UserFunctions userFunction = new UserFunctions(getActivity());
				json = userFunction.registerUser(name, email, password);
			} catch (Exception e) {

			}

			return json;
		}

		protected void onPostExecute(JSONObject json) {
			// disable dialog
			dialog.dismiss();

			// check for login response
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						// user successfully registred
						sendNewMss(login_succes, mLTT);
					} else {
						// Error in login
						Toast.makeText(getActivity(),
								json.getString(KEY_ERROR_MSG).toString(),
								Toast.LENGTH_LONG).show();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	private class SigninTask extends AsyncTask<Void, Void, JSONObject> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "",
					".... Processing ....", true);
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONObject json = null;

			try {
				String email = edSignInName.getText().toString();
				String password = edSignInPass.getText().toString();
				UserFunctions userFunction = new UserFunctions(getActivity());
				json = userFunction.loginUser(email, password);
			} catch (Exception e) {

			}

			return json;
		}

		protected void onPostExecute(JSONObject json) {
			// disable dialog
			dialog.dismiss();

			// check for Sign in response
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						// user successfully logged in
						JSONObject json_user = json.getJSONObject("user");
						pref.createLoginPref(json.getString(KEY_ID),
								json_user.getString(KEY_EMAIL),
								json_user.getString(KEY_NAME));

						// Complete Sign In
						sendNewMss(login_succes, mLTT);
					} else {
						// Error in login
						Toast.makeText(getActivity(),
								json.getString(KEY_ERROR_MSG).toString(),
								Toast.LENGTH_LONG).show();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
}

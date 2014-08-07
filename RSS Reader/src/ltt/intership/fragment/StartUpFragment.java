package ltt.intership.fragment;

import java.util.Arrays;

import ltt.intership.R;
import ltt.intership.activity.MainActivity;
import ltt.intership.activity.StartUpActivity;
import ltt.intership.activity.StartUpActivity.mFragmentReceiver;
import ltt.intership.androidlayout.MetroActivity;
import ltt.intership.connection.PrefManagement;
import ltt.intership.connection.UserFunctions;
import ltt.intership.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

public class StartUpFragment extends Fragment implements View.OnClickListener,
		mFragmentReceiver {
	private ProgressBar pgBar;
	private LinearLayout llLoginSocial;
	private LinearLayout llLoginLtt;
	private LinearLayout llSignIn;
	private LinearLayout llGetStart;
	private LinearLayout llSignUp;

	private LoginButton FbBtnLogin;
	private SignInButton GBtnLogin;

	private EditText edSignInName;
	private EditText edSignInPass;

	private EditText edRegisterName;
	private EditText edRegisterEmail;
	private EditText edRegisterPass;

	private Animation bslide, islide, lslide, rslide;

	private Twitter twitterConnection;
	private RequestToken twitterRequestToken;

	private SharedPreferences mPrefs;

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

	private static int stateScreen = 0;
	private static final int stateSocial = 1;
	private static final int stateSignIn = 2;
	private static final int stateSignUp = 3;

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
					Log.i("login facebook succesfull", "");
					mPrefs.edit()
							.putString(Config.PREFS_LOGIN_FACEBOOK, "true");
					if (mPrefs.getString(Config.PREFS_TYPE_ACC, null) == null) {
						mPrefs.edit()
								.putString(Config.PREFS_TYPE_ACC, "FACEBOOK")
								.putString(Config.PREFS_LOGIN_FACEBOOK, "true")
								.commit();
					} else {
						mPrefs.edit()
								.putString(Config.PREFS_LOGIN_FACEBOOK, "true")
								.commit();
					}
					break;
				case mGOOGLE:
					Log.i("login google succesfull", "");
					if (mPrefs.getString(Config.PREFS_TYPE_ACC, null) == null) {
						mPrefs.edit()
								.putString(Config.PREFS_TYPE_ACC, "GOOGLE")
								.putString(Config.PREFS_LOGIN_GOOGLE, "true")
								.commit();
					} else {
						mPrefs.edit()
								.putString(Config.PREFS_LOGIN_GOOGLE, "true")
								.commit();
					}
					break;
				case mTWITTER:
					Log.i("login twitter succesfull", "");
					if (mPrefs.getString(Config.PREFS_TYPE_ACC, null) == null) {
						mPrefs.edit()
								.putString(Config.PREFS_TYPE_ACC, "TWITTER")
								.putString(Config.PREFS_LOGIN_TWITTER, "true")
								.commit();
					} else {
						mPrefs.edit()
								.putString(Config.PREFS_LOGIN_TWITTER, "true")
								.commit();
					}
					break;
				case mLTT:
					Log.i("login ltt account succesfull", "");
					if (mPrefs.getString(Config.PREFS_TYPE_ACC, null) == null) {
						mPrefs.edit().putString(Config.PREFS_TYPE_ACC, "LTT")
								.putString(Config.PREFS_LOGIN_LTT, "true")
								.commit();
					} else {
						mPrefs.edit().putString(Config.PREFS_LOGIN_LTT, "true")
								.commit();
					}
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
			// GBtnLogin.performClick();
			startLoginByGoogle();
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
			llLoginSocial.setVisibility(View.GONE);
			llSignUp.setVisibility(View.GONE);

			llLoginLtt.setAnimation(lslide);
			llLoginLtt.setVisibility(View.VISIBLE);
			break;
		case R.id.startup_get_started:
			sendNewMss(login_succes, notLogin);
			break;
		case R.id.startup_btn_ltt_signin:
			// sign in with ltt account
			if (!"".equals(edSignInName.getText().toString())
					&& !"".equals(edSignInPass.getText().toString())) {
				new SigninTask().execute();
			} else {
				Toast.makeText(getActivity(), "Please input full information",
						Toast.LENGTH_LONG).show();
			}
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
			break;
		case R.id.startup_tv_create_new_acc:
			llLoginLtt.setVisibility(View.GONE);
			llLoginSocial.setVisibility(View.GONE);

			llSignUp.setAnimation(rslide);
			llSignUp.setVisibility(View.VISIBLE);
			break;
		case R.id.startup_tv_existed_acc:
			llSignUp.setVisibility(View.GONE);
			llLoginLtt.setVisibility(View.GONE);

			llLoginSocial.setAnimation(rslide);
			llLoginSocial.setVisibility(View.VISIBLE);
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
			stateScreen = stateSocial;
			pgBar.setVisibility(View.GONE);
			llLoginLtt.setVisibility(View.GONE);
			llSignUp.setVisibility(View.GONE);

			llLoginSocial.setAnimation(bslide);
			llLoginSocial.setVisibility(View.VISIBLE);
			Log.i("progess bar", "completed");

			checkLogin();
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
				R.anim.slide_from_left);
		rslide = AnimationUtils.loadAnimation(getActivity(),
				R.anim.slide_from_right);

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

		GBtnLogin = (SignInButton) view
				.findViewById(R.id.startup_google_login_button);

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

		((TextView) view.findViewById(R.id.startup_tv_existed_acc))
				.setOnClickListener(this);
		((TextView) view.findViewById(R.id.startup_tv_create_new_acc))
				.setOnClickListener(this);
	}

	private void initParameter() {
		mPrefs = getActivity().getSharedPreferences(Config.GLOBAL_PREFS, 0);
		pref = new PrefManagement(getActivity());
	}

	/**
	 * Chuyen sang activity moi sau khi dang nhap thanh cong
	 */
	private void LoginSuccess() {

		startActivity(new Intent(getActivity(), MetroActivity.class));
		getActivity().finish();

	}

	/**
	 * kiem tra cac tai khoan hien co
	 */
	private void checkLogin() {
		if (mPrefs.getString(Config.PREFS_TYPE_ACC, null) != null) {

			if (isLoginTwitter()) {
				sendNewMss(login_succes, mTWITTER);
			}
			if (mPrefs.getString(Config.PREFS_LOGIN_LTT, null) != null) {
				sendNewMss(login_succes, mLTT);
			}
		}
	}

	private boolean isLoginTwitter() {
		if (mPrefs.getString(Config.TWITTER_USER_TOKEN, null) == null) {
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

	private void startLoginByGoogle() {
		((StartUpActivity) getActivity()).resolveSignInError();
	}

	private void startLoginByTwitterAcc() {
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
	public boolean onBackPressed() {
		boolean backState = false;
		switch (stateScreen) {
		case stateSocial:
			backState = false;
			break;
		case stateSignIn:
			backState = true;
			llLoginLtt.setVisibility(View.GONE);
			llSignUp.setVisibility(View.GONE);

			llLoginSocial.setAnimation(rslide);
			llLoginSocial.setVisibility(View.VISIBLE);
			break;
		case stateSignUp:
			backState = true;
			llSignUp.setVisibility(View.GONE);
			llLoginLtt.setVisibility(View.GONE);

			llLoginLtt.setAnimation(rslide);
			llLoginLtt.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		return backState;

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
				mPrefs.edit()
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

	@Override
	public void onLoginGoogleSuccess(boolean login) {
		this.LoginGoogle = login;
		if (login == true) {
			sendNewMss(login_succes, mGOOGLE);
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

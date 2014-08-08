package ltt.intership.fragment;

import ltt.intership.R;
import ltt.intership.activity.ListRssActivity;
import ltt.intership.activity.StartUpActivity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
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

import com.facebook.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

public class MainFragment extends Fragment implements ConnectionCallbacks,
		OnConnectionFailedListener {

	private Spinner sp;
	private Button btnStart;
	private Button btnLogoutFB;
	private Button btnLogoutGoogle;
	private Button btnLogoutTwitter;
	private Button btnTimeline;
	
	private String url = "";

	private GoogleApiClient mGoogleApiClient;
	private static final int STATE_DEFAULT = 0;
	private static final int STATE_SIGN_IN = 1;
	private static final int STATE_IN_PROGRESS = 2;
	private static final int RC_SIGN_IN = 0;
	private static final int DIALOG_PLAY_SERVICES_ERROR = 0;
	private int mSignInProgress;
	private PendingIntent mSignInIntent;
	private int mSignInError;

	public MainFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mGoogleApiClient = buildGoogleApiClient();
		
		sp = (Spinner) rootView.findViewById(R.id.main_spinner);

		btnStart = (Button) rootView.findViewById(R.id.main_btn_start_list_rss);
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "start", Toast.LENGTH_SHORT)
						.show();
				Log.i("main", "start");
				handingRss();

			}
		});

		btnLogoutFB = (Button) rootView.findViewById(R.id.main_btn_logout_fb);
		btnLogoutFB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "logout", Toast.LENGTH_SHORT)
						.show();
				logoutFacebook(getActivity());
			}
		});

		btnLogoutGoogle = (Button) rootView
				.findViewById(R.id.main_btn_logout_google);
		btnLogoutGoogle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "logout", Toast.LENGTH_SHORT)
						.show();
				logoutGoogle();
			}
		});
		// btnLogoutTwitter = (Button)
		// rootView.findViewById(R.id.main_btn_logout_twitter);
		// btnLogoutTwitter.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// Toast.makeText(getActivity(), "logout", Toast.LENGTH_SHORT)
		// .show();
		// logoutFacebook(getActivity());
		// }
		// });
		
		btnTimeline = (Button)rootView.findViewById(R.id.main_btn_load_fb_timeline);
		btnTimeline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("main", "loadtimeline");
				handingTimeline();
			}
		});
		return rootView;
	}

	private void logoutFacebook(Context context) {
		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				// clear your preferences if saved
			}
		} else {

			session = new Session(context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
			// clear your preferences if saved

		}
		Intent i = new Intent(getActivity(), StartUpActivity.class);
		startActivity(i);
	}

	private GoogleApiClient buildGoogleApiClient() {
		return new GoogleApiClient.Builder(getActivity())
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Plus.API, Plus.PlusOptions.builder().build())
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
	}

	private void logoutGoogle() {
		Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
		mGoogleApiClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

		if (mSignInProgress != STATE_IN_PROGRESS) {
			mSignInIntent = result.getResolution();
			mSignInError = result.getErrorCode();

		}
	}

	private void handingRss() {
		this.url = sp.getSelectedItem().toString();

		Intent i = new Intent(getActivity(), ListRssActivity.class);
		i.putExtra("url", this.url);
		i.putExtra("category", "SPORT");
		startActivity(i);
		// getActivity().finish();
	}
	private void handingTimeline() {
		this.url = sp.getSelectedItem().toString();

		Intent i = new Intent(getActivity(), ListRssActivity.class);
		i.putExtra("url", "facebook");
		startActivity(i);
		// getActivity().finish();
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub

	}
}

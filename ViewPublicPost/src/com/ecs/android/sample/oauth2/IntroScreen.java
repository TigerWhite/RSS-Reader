package com.ecs.android.sample.oauth2;

import java.io.IOException;
//import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;

import com.google.api.client.auth.oauth2.Credential;

public class IntroScreen extends Activity {

	
	private Timer timer = new Timer();
	private Button btnOAuthGooglePlus;
	private SharedPreferences prefs;
	protected int elapsedTime;
	private Button btnClearGooglePlus;
	private Button btnApiGooglePlus;
	private Button btnViewComment;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		btnOAuthGooglePlus = (Button)findViewById(R.id.btn_oauth_googleplus);
		btnClearGooglePlus = (Button)findViewById(R.id.btn_clear_googleplus);
		btnApiGooglePlus = (Button)findViewById(R.id.btn_api_googleplus);
		//btnViewComment = (Button)findViewById(R.id.btn_view_comment);
				
		btnOAuthGooglePlus.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startOauthFlow(Oauth2Params.GOOGLE_PLUS);
			}
		});
		
		btnClearGooglePlus.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				clearCredentials(Oauth2Params.GOOGLE_PLUS);
			}

		});
		
		btnApiGooglePlus.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startMainScreen(Oauth2Params.GOOGLE_PLUS);
			}

		});
		
//		btnViewComment.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				startOauthFlow(Oauth2Params.GOOGLE_PLUS_COMMENT);
//				//startMainScreenComment(Oauth2Params.GOOGLE_PLUS_COMMENT);
//			}
//		});
	}

	/**
	 * Starts the main screen where we show the API results.
	 * 
	 * @param oauth2Params
	 */
	private void startMainScreen(Oauth2Params oauth2Params) {
		Constants.OAUTH2PARAMS = oauth2Params;
		startActivity(new Intent().setClass(this,MainScreen.class));
	}
	
	////////////////////////
//	private void startMainScreenComment(Oauth2Params oauth2Params) {
//		Constants.OAUTH2PARAMS = oauth2Params;
//		startActivity(new Intent().setClass(this, MainScreenComment.class));
//	}
	
	/**
	 * Starts the activity that takes care of the OAuth2 flow
	 * 
	 * @param oauth2Params
	 */
	private void startOauthFlow(Oauth2Params oauth2Params) {
		Constants.OAUTH2PARAMS = oauth2Params;
		startActivity(new Intent().setClass(this,OAuthAccessTokenActivity.class));
	}	
	
	/**
	 * Clears our credentials (token and token secret) from the shared preferences.
	 * We also setup the authorizer (without the token).
	 * After this, no more authorized API calls will be possible.
	 * @throws IOException 
	 */
    private void clearCredentials(Oauth2Params oauth2Params)  {
		try {
			new OAuth2Helper(prefs,oauth2Params).clearCredentials();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		startTimer();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopTimer();
	}

	private void stopTimer() {
		timer.cancel();
	}

	private String getTokenStatusText(Oauth2Params oauth2Params) throws IOException {
		Credential credential = new OAuth2Helper(this.prefs,oauth2Params).loadCredential();
		String output = null;
		if (credential==null || credential.getAccessToken()==null) {
			output = "No access token found.";
		} else if (credential.getExpirationTimeMilliseconds()!=null){
			output = credential.getAccessToken() + "[ " + credential.getExpiresInSeconds() + " seconds remaining]";
		} else {
			output = credential.getAccessToken() + "[does not expire]";
		}
		return output;
	}
	
	protected  void startTimer() {
		Log.i(Constants.TAG," +++++ Started timer");
		timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	Log.i(Constants.TAG," +++++ Refreshing data");
	        	try {
		            Message msg = new Message();
		            Bundle bundle = new Bundle();
		            bundle.putString("plus", getTokenStatusText(Oauth2Params.GOOGLE_PLUS));
		            msg.setData(bundle);
		            //mHandler.sendMessage(msg);
		            
	        	} catch (Exception ex) {
	        		ex.printStackTrace();
	        		timer.cancel();
		            Message msg = new Message();
		            Bundle bundle = new Bundle();
		            bundle.putString("plus", ex.getMessage());
		            bundle.putString("tasks", ex.getMessage());
		            bundle.putString("foursquare", ex.getMessage());
		            msg.setData(bundle);
	        		//mHandler.sendMessage(msg);
	        	}

	        }
	    }, 0, 1000);
	}



}

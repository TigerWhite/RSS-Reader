package com.googleplusfull;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.googleplusfull.R.id;

public class MainActivity extends FragmentActivity implements
    ConnectionCallbacks, OnConnectionFailedListener,
    ResultCallback<People.LoadPeopleResult>, View.OnClickListener {

  private static final String TAG = "android-plus-quickstart";
  private static final int STATE_DEFAULT = 0;
  private static final int STATE_SIGN_IN = 1;
  private static final int STATE_IN_PROGRESS = 2;
  private static final int RC_SIGN_IN = 0;
  private static final int DIALOG_PLAY_SERVICES_ERROR = 0;
  private static final String SAVED_PROGRESS = "sign_in_progress";
  private GoogleApiClient mGoogleApiClient;
  private int mSignInProgress;
  private PendingIntent mSignInIntent;
  private int mSignInError;
  private SignInButton mSignInButton;
  private Button mSignOutButton;
  private Button mRevokeButton;
  private Button ShareButton, IntentButton;
  private TextView mStatus;
  private ListView mCirclesListView;
  private ArrayAdapter<String> mCirclesAdapter;
  private ArrayList<String> mCirclesList;

  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.main_activity);

    mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
    mSignOutButton = (Button) findViewById(R.id.sign_out_button);
    mRevokeButton = (Button) findViewById(R.id.revoke_access_button);
    ShareButton = (Button) findViewById(id.post_button);
    IntentButton = (Button) findViewById(id.intent_button);
    mStatus = (TextView) findViewById(R.id.sign_in_status);
    mCirclesListView = (ListView) findViewById(R.id.circles_list);

    mSignInButton.setOnClickListener(this);
    mSignOutButton.setOnClickListener(this);
    mRevokeButton.setOnClickListener(this);
    ShareButton.setOnClickListener(this);
    IntentButton.setOnClickListener(this);//

    mCirclesList = new ArrayList<String>();
    mCirclesAdapter = new ArrayAdapter<String>(
        this, R.layout.circle_member, mCirclesList);
    mCirclesListView.setAdapter(mCirclesAdapter);

    if (savedInstanceState != null) {
      mSignInProgress = savedInstanceState
          .getInt(SAVED_PROGRESS, STATE_DEFAULT);
    }

    mGoogleApiClient = buildGoogleApiClient();
  }

  private GoogleApiClient buildGoogleApiClient() {
    return new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API, Plus.PlusOptions.builder().build())
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mGoogleApiClient.connect();
  }

  @Override
  protected void onStop() {
    super.onStop();

    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVED_PROGRESS, mSignInProgress);
  }

  @Override
  public void onClick(View v) {
    if (!mGoogleApiClient.isConnecting()) {
      switch (v.getId()) {
          case R.id.sign_in_button:
            mStatus.setText(R.string.status_signing_in);
            resolveSignInError();
            break;
          case R.id.sign_out_button:
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            break;
          case R.id.revoke_access_button:
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient = buildGoogleApiClient();
            mGoogleApiClient.connect();
            break;
          case R.id.post_button:
        	Intent shareIntent = new PlusShare.Builder(MainActivity.this)
            	.setType("text/plain")
            	.setText(" ")
            	.setContentUrl(Uri.parse("http://google.com"))
            	.getIntent();
        		startActivityForResult(shareIntent, 0);
//          case R.id.intent_button:
//        	  Intent i = new Intent(MainActivity.this,
//						PlusSample.class);
//				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(i);
//        	  //openGPlus("117146718199415655123");
      }
    }
  }

  @Override
  public void onConnected(Bundle connectionHint) {
    Log.i(TAG, "onConnected");

    mSignInButton.setEnabled(false);
    mSignOutButton.setEnabled(true);
    mRevokeButton.setEnabled(true);

    Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

    mStatus.setText(String.format(
        getResources().getString(R.string.signed_in_as),
        currentUser.getDisplayName()));

//    Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
//        .setResultCallback(this);

    mSignInProgress = STATE_DEFAULT;
  }

  @Override
  public void onConnectionFailed(ConnectionResult result) {

    Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
        + result.getErrorCode());

    if (mSignInProgress != STATE_IN_PROGRESS) {
      mSignInIntent = result.getResolution();
      mSignInError = result.getErrorCode();

      if (mSignInProgress == STATE_SIGN_IN) {
        resolveSignInError();
      }
    }
    onSignedOut();
  }

  private void resolveSignInError() {
    if (mSignInIntent != null) {
      try {
        mSignInProgress = STATE_IN_PROGRESS;
        startIntentSenderForResult(mSignInIntent.getIntentSender(),
            RC_SIGN_IN, null, 0, 0, 0);
      } catch (SendIntentException e) {
        Log.i(TAG, "Sign in intent could not be sent: "
            + e.getLocalizedMessage());
        mSignInProgress = STATE_SIGN_IN;
        mGoogleApiClient.connect();
      }
    } else {
      showDialog(DIALOG_PLAY_SERVICES_ERROR);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
      Intent data) {
    switch (requestCode) {
      case RC_SIGN_IN:
        if (resultCode == RESULT_OK) {
          mSignInProgress = STATE_SIGN_IN;
        } else {
          mSignInProgress = STATE_DEFAULT;
        }

        if (!mGoogleApiClient.isConnecting()) {
          mGoogleApiClient.connect();
        }
        break;
    }
  }

  @Override
  public void onResult(LoadPeopleResult peopleData) {
    if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
      mCirclesList.clear();
      //Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
      PersonBuffer personBuffer = peopleData.getPersonBuffer();
      //String nextpageToken = currentPerson.getTagline()();
      //String personGooglePlusProfile = currentPerson.getUrl();
      try {
          int count = personBuffer.getCount();
          for (int i = 0; i < count; i++) {
              mCirclesList.add(personBuffer.get(i).getDisplayName());
          }
      } finally {
          personBuffer.close();
      }
      
      //mCirclesList.add(personBuffer.getDispalyName());

      mCirclesAdapter.notifyDataSetChanged();
    } else {
      Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
    }
  }

  private void onSignedOut() {
    mSignInButton.setEnabled(true);
    mSignOutButton.setEnabled(false);
    mRevokeButton.setEnabled(false);

    mStatus.setText(R.string.status_signed_out);

    mCirclesList.clear();
    mCirclesAdapter.notifyDataSetChanged();
  }

  @Override
  public void onConnectionSuspended(int cause) {
    mGoogleApiClient.connect();
  }
  
  ///////////
//  public void openGPlus(String profile) {
//	    try {
//	        Intent intent = new Intent(Intent.ACTION_VIEW);
//	        intent.setClassName("com.google.android.apps.plus",
//	          "com.google.android.apps.plus.phone.UrlGatewayActivity");
//	        intent.putExtra("customAppUri", profile);
//	        startActivity(intent);
//	    } catch(ActivityNotFoundException e) {
//	        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/"+profile+"/posts")));
//	    }
//	}

  
  @Override
  protected Dialog onCreateDialog(int id) {
    switch(id) {
      case DIALOG_PLAY_SERVICES_ERROR:
        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
          return GooglePlayServicesUtil.getErrorDialog(
              mSignInError,
              this,
              RC_SIGN_IN,
              new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                  Log.e(TAG, "Google Play services resolution cancelled");
                  mSignInProgress = STATE_DEFAULT;
                  mStatus.setText(R.string.status_signed_out);
                }
              });
        } else {
          return new AlertDialog.Builder(this)
              .setMessage(R.string.play_services_error)
              .setPositiveButton(R.string.close,
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      Log.e(TAG, "Google Play services error could not be "
                          + "resolved: " + mSignInError);
                      mSignInProgress = STATE_DEFAULT;
                      mStatus.setText(R.string.status_signed_out);
                    }
                  }).create();
        }
      default:
        return super.onCreateDialog(id);
    }
  }
}
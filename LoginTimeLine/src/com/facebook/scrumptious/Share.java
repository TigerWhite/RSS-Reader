package com.facebook.scrumptious;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Share extends Activity {

	private static final String PERMISSION = "publish_actions";
	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this).build();
			//setlink...........
			/*FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this).setLink("aslkjflasf").build();*/
			uiHelper.trackPendingDialogCall(shareDialog.present());

		} else {
			publishFeedDialog();
			// Fallback. For example, publish the post using the Feed Dialog
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Intent i=new Intent(Share.this, MainActivity.class);
						startActivity(i);
						Log.i("Activity", "Success!");
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	private void publishFeedDialog() {
	    Bundle params = new Bundle();
	    params.putString("name", "Facebook SDK for Android");
	    params.putString("caption", "Build great social apps and get more installs.");
	    params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	    params.putString("link", "https://developers.facebook.com/android");
	    params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(Share.this,
	            Session.getActiveSession(),
	            params)).setOnCompleteListener(listener).build();
	    feedDialog.show();
	}
	
	OnCompleteListener listener=new OnCompleteListener() {
		
		@Override
		public void onComplete(Bundle values, FacebookException error) {
			// TODO Auto-generated method stub
			if (error == null) {
                // When the story is posted, echo the success
                // and the post Id.
                final String postId = values.getString("post_id");
                if (postId != null) {
                    Toast.makeText(Share.this,
                        "Posted story, id: "+postId,
                        Toast.LENGTH_SHORT).show();
                } else {
                    // User clicked the Cancel button
                    Toast.makeText(Share.this.getApplicationContext(), 
                        "Publish cancelled", 
                        Toast.LENGTH_SHORT).show();
                }
            } else if (error instanceof FacebookOperationCanceledException) {
                // User clicked the "x" button
                Toast.makeText(Share.this.getApplicationContext(), 
                    "Publish cancelled", 
                    Toast.LENGTH_SHORT).show();
            } else {
                // Generic, ex: network error
                Toast.makeText(Share.this.getApplicationContext(), 
                    "Error posting story", 
                    Toast.LENGTH_SHORT).show();
            }
			Intent i=new Intent(Share.this, MainActivity.class);
			startActivity(i);
		}
	};
}
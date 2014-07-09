package com.example.signinregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.library.UserFunctions;

public class DashboardActivity extends Activity {

	UserFunctions userFunctions;
	Button btnLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Check login status in database
		userFunctions = new UserFunctions(DashboardActivity.this);
		if (userFunctions.isUserLoggedIn()) {
			// user already logged in show dashboard
			setContentView(R.layout.activity_dashboard);
			btnLogout = (Button) findViewById(R.id.btnLogout);

			btnLogout.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					userFunctions.logoutUser();
					Intent main = new Intent(DashboardActivity.this,
							MainActivity.class);
					main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(main);
					// Closing dashboard screen
					finish();
				}
			});

		} else {
			// user is not logged in show main screen
			Intent main = new Intent(DashboardActivity.this,
					MainActivity.class);
			main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(main);
			// Closing dashboard screen
			finish();
		}
	}

}

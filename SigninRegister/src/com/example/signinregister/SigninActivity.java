package com.example.signinregister;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.widget.TextView;
import android.widget.Toast;



import com.example.library.PrefManagement;
import com.example.library.UserFunctions;

public class SigninActivity extends Activity{
	
	PrefManagement pref;
	
	Button btnLogin;
	ImageButton imageButton1, imageButton2;
	EditText edt_email, edt_password;
	TextView registerScreen1;
	
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_ID = "id";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singin);
		
		pref = new PrefManagement(SigninActivity.this);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
		edt_email = (EditText) findViewById(R.id.edt_email);
		edt_password = (EditText) findViewById(R.id.edt_password);
		registerScreen1 = (TextView) findViewById(R.id.link_to_register1);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (!"".equals(edt_email.getText().toString())
						&& !"".equals(edt_password.getText().toString())) {
					new SigninTask().execute();
				} else {
					Toast.makeText(SigninActivity.this,
							"Please input full information", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		registerScreen1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SigninActivity.this,
						RegisterActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}
	
	private class SigninTask extends AsyncTask<Void, Void, JSONObject> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(SigninActivity.this, "",
					".... Processing ....", true);
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONObject json = null;

			try {
				String email = edt_email.getText().toString();
				String password = edt_password.getText().toString();
				UserFunctions userFunction = new UserFunctions(
						SigninActivity.this);
				json = userFunction.loginUser(email, password);
			} catch (Exception e) {

			}

			return json;
		}

		protected void onPostExecute(JSONObject json) {
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
						

						// Launch Dashboard Screen
						Intent dashboard = new Intent(SigninActivity.this,
								DashboardActivity.class);

						// Close all views before launching Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);

						// Close Login Screen
						finish();
					} else {
						// Error in login
						Toast.makeText(SigninActivity.this,
								json.getString(KEY_ERROR_MSG).toString(),
								Toast.LENGTH_LONG).show();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			dialog.dismiss();
		}

	}
}

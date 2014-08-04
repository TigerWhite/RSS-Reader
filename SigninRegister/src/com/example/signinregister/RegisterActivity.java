package com.example.signinregister;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.UserFunctions;

public class RegisterActivity extends Activity {

	EditText edt_fullname, edt_email, edt_password;
	Button btn_register;
	TextView loginScreen;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR_MSG = "error_msg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		loginScreen = (TextView) findViewById(R.id.link_to_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		edt_fullname = (EditText) findViewById(R.id.edt_fullname);
		edt_email = (EditText) findViewById(R.id.edt_email);
		edt_password = (EditText) findViewById(R.id.edt_password);

		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!"".equals(edt_fullname.getText().toString())
						&& !"".equals(edt_email.getText().toString())
						&& !"".equals(edt_password.getText().toString())) {
					new RegisterTask().execute();
				} else {
					Toast.makeText(RegisterActivity.this,
							"Please input full information", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		loginScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private class RegisterTask extends AsyncTask<Void, Void, JSONObject> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(RegisterActivity.this, "",
					".... Processing ....", true);
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONObject json = null;

			try {
				String name = edt_fullname.getText().toString();
				String email = edt_email.getText().toString();
				String password = edt_password.getText().toString();
				UserFunctions userFunction = new UserFunctions(
						RegisterActivity.this);
				json = userFunction.registerUser(name, email, password);
			} catch (Exception e) {

			}

			return json;
		}

		protected void onPostExecute(JSONObject json) {
			// check for login response
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						// user successfully registred
						finish();
					} else {
						// Error in login
						Toast.makeText(RegisterActivity.this,
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

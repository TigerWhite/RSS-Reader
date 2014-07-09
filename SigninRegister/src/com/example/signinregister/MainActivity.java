package com.example.signinregister;

//import org.json.JSONException;
//import org.json.JSONObject;

import android.app.Activity;
//import android.app.ProgressDialog;
import android.content.Intent;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toast;

import com.example.library.PrefManagement;
//import com.example.library.UserFunctions;

public class MainActivity extends Activity {

	PrefManagement pref;

	TextView registerScreen;
	EditText edt_email, edt_password;
	Button btn_login;
	Button btnSignin;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		pref = new PrefManagement(MainActivity.this);

		btnSignin = (Button) findViewById(R.id.btnSignin);
		registerScreen = (TextView) findViewById(R.id.link_to_register);
				
		
		btnSignin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,
						SigninActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		
		registerScreen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,
						RegisterActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

	}

}

package com.example.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefManagement {

	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	int PRIVATE_MODE = 0;

	// Shared preferences name file
	private static final String PREF_FILENAME = "AndroidAPIUser";

	// Key of all information
	private static final String IS_LOGIN = "is_login";
	private static final String KEY_USERID = "user_id";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_FULLNAME = "full_name";

	// Constructor
	public PrefManagement(Context context) {

		_context = context;
		pref = _context.getSharedPreferences(PREF_FILENAME, PRIVATE_MODE);
		editor = pref.edit();
		editor.commit();
	}

	// Get value of key
	public String getUserID() {
		return pref.getString(KEY_USERID, null);
	}

	public String getEmail() {
		return pref.getString(KEY_EMAIL, null);
	}

	public String getFullName() {
		return pref.getString(KEY_FULLNAME, null);
	}

	// Call after login success full to create information for user
	public void createLoginPref(String user_id, String email, String full_name) {

		// Storing value to KEY of shared preferences
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_USERID, user_id);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_FULLNAME, full_name);

		// Commit information and save into shared preferences
		editor.commit();
	}

	// Logout user
	public void clearAll() {

		// Clear shared references data
		editor.clear();
		editor.commit();

	}

	// Get login status
	public boolean isLogged() {
		return pref.getBoolean(IS_LOGIN, false);
	}

}

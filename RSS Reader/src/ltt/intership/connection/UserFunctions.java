package ltt.intership.connection;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class UserFunctions {
	private JSONParser jsonParser;
	private PrefManagement pref;
	private Context context;

	private static String URL = "http://anhkullpro.freevnn.com/index.php";

	private static String login_tag = "login";
	private static String register_tag = "register";

	// constructor
	public UserFunctions(Context context) {
		this.context = context;
		this.jsonParser = new JSONParser();
		this.pref = new PrefManagement(this.context);
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJsonFromUrl(URL, params);
		return json;
	}

	/**
	 * function make Register Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJsonFromUrl(URL, params);
		return json;
	}

	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn() {
		return pref.isLogged();
	}

	/**
	 * Function to logout user
	 * */
	public boolean logoutUser() {
		pref.clearAll();
		return true;
	}
}

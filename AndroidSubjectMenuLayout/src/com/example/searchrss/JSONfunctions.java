package com.example.searchrss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONfunctions {

	public static JSONObject getJSONfromURL(String url) {
		URLConnection connection;
		JSONObject jsonobject = null;
		URL url1;
		// Download JSON data from URL
		try {
			url1 = new URL(url);
			connection = url1.openConnection();

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}

//			Log.d("tag1","tag1 "+ builder.toString());
			jsonobject = new JSONObject(builder.toString());

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return jsonobject;
	}
}

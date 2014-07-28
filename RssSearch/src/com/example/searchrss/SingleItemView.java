package com.example.searchrss;

import com.androidbegin.jsonparsetutorial.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleItemView extends Activity {
	// Declare Variables
	String url;
	String title;
	String description;
	String position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);

		Intent i = getIntent();
		// Get the result of rank
		url = i.getStringExtra("url");
		// Get the result of country
		title = i.getStringExtra("title");
		// Get the result of population
		description = i.getStringExtra("description");

		// Locate the TextViews in singleitemview.xml
		TextView txtrank = (TextView) findViewById(R.id.url);
		TextView txtcountry = (TextView) findViewById(R.id.title);
		TextView txtpopulation = (TextView) findViewById(R.id.description);


		// Set results to the TextViews
		txtrank.setText(url);
		txtcountry.setText(title);
		txtpopulation.setText(description);

	}
}
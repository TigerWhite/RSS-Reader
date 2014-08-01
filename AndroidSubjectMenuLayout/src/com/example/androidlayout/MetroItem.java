package com.example.androidlayout;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MetroItem extends LinearLayout{
	
	public MetroItem(Context context, int imageID, int idMetro){
		this(context, DEFAULT_TEXT,  imageID, DEFAULT_LINK, idMetro);
	}
	public MetroItem(Context context, int imageID, String text, int idMetro){
		this(context, text,  imageID, DEFAULT_LINK, idMetro);
	}
	public MetroItem(Context context, String text, int imageID, String link, int idMetro){
		super(context);
		this.text = text;
		this.imageID = imageID;
		this.idMetro = idMetro;
		this.link = link;
		
		this.setOnClickListener((OnClickListener) context);
		this.setBackgroundResource(imageID);
		this.setId(idMetro);
		imageView = new ImageView(context);
		imageView.setPadding(5, 0, 0, 0);
		
		textView = new TextView(context);
		textView.setTextColor(getResources().getColor(android.R.color.white));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		textView.setText(text);
		
		this.setGravity(Gravity.CENTER);
		this.setClickable(true);
		this.addView(imageView);
		this.addView(textView);
	}
	public String getText(){
		return text;
	}
	public void setText(String text){
		this.text = text;
	}
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	public int getImageID(){
		return imageID;
	}
	public void setImageID(int imageID){
		this.imageID = imageID;
	}
	public int getIdMetro() {
		return idMetro;
	}
	public void setIdMetro(int id) {
		this.idMetro = idMetro;
	}
	public void setIcon(int resid) {
		imageView.setImageDrawable(getResources().getDrawable(resid));
	}
//	public ImageView getImageView() {
//		return imageView;
//	}

	public TextView getTextView() {
		return textView;
	}
	

	private int width, height;
	private int imageID, idMetro;
	private String text, link;
	private static final int DEFAULT_WIDTH = 1;
	private static final int DEFAULT_HEIGHT = 1;
	private static final String DEFAULT_TEXT = "Title";
	private static final String DEFAULT_LINK = "dantri.com.vn";
	private ImageView imageView;
	private TextView textView;
	
	private RelativeLayout.LayoutParams tvParams;
	private int oneUnitWidth, oneUnitHeight;
	private int anchor1, anchor2;
	private int marginArg1, marginArg2, marginArg3, marginArg4;
}

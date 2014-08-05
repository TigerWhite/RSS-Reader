package com.example.androidlayout;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MetroItem extends LinearLayout{
	public MetroItem(Context context){
		super(context);
	}
	public MetroItem(Context context, int imageID, int id){
		this(context, DEFAULT_TEXT,  imageID, DEFAULT_LINK, id);
	}
	public MetroItem(Context context, int imageID, String title, int id){
		this(context, title,  imageID, DEFAULT_LINK, id);
	}
	public MetroItem(Context context, String title, int image, String link, int id){
		super(context);
		this.title = title;
		this.image = image;
		this.id = id;
		this.link = link;
		
		this.setOnClickListener((OnClickListener) context);
		this.setOnLongClickListener((OnLongClickListener) context);
		this.setBackgroundResource(image);
		this.setId(id);
		imageView = new ImageView(context);
		imageView.setPadding(5, 0, 0, 0);
		
		textView = new TextView(context);
		textView.setTextColor(getResources().getColor(android.R.color.white));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
		textView.setText(title);
		
		this.setGravity(Gravity.BOTTOM);
		this.setClickable(true);
		this.addView(imageView);
		this.addView(textView);
	}
	public void setID(int id){
		this.setId(id);
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	public int getImage(){
		return image;
	}
	public void setImage(int image){
		this.image = image;
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
	private int image, id;
	private String title, link;
	private static final int DEFAULT_WIDTH = 1;
	private static final int DEFAULT_HEIGHT = 1;
	private static final String DEFAULT_TEXT = "Title";
	private static final String DEFAULT_LINK = "dantri.com.vn";
	private ImageView imageView;
	private TextView textView;
}

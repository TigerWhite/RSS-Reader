package com.example.androidlayout;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

@SuppressLint("NewApi") public class MetroItem extends FrameLayout{
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
//		this.setBackgroundResource(image);
		this.setId(id);
		imageView = new ImageView(context);
		imageView.setPadding(5, 0, 0, 0);
//		imageView.setImageDrawable(getResources().getDrawable(image));
		imageView.setScaleType(ScaleType.CENTER_CROP);
		
		imageView2 = new ImageView(context);
		imageView2.setPadding(5, 0, 0, 0);
//		imageView2.setImageDrawable(getResources().getDrawable(image));
		imageView2.setScaleType(ScaleType.CENTER_CROP);

		
//		AnimationDrawable animation = new AnimationDrawable();
//	    animation.addFrame(getResources().getDrawable(R.drawable.orange), 4000);
//	    animation.addFrame(getResources().getDrawable(R.drawable.orange_pressed), 5000);
//	    animation.setOneShot(false);
//	    Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//	            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF,
//	            1f, Animation.RELATIVE_TO_SELF, 0f);
	    final Animation slide = new ScaleAnimation((float)1,
	            (float)1, (float)0, (float)1,
	            Animation.RELATIVE_TO_SELF, (float)0,
	            Animation.RELATIVE_TO_SELF, (float)1);

	    slide.setDuration(2000);
	    slide.setFillAfter(true);
	    slide.setFillEnabled(true);

	    // start the animation!
//	    animation.start();

		
		
		final int imageArray[] = {R.drawable.pink,R.drawable.pink_pressed};

		Random r = new Random();
//		final int it = r.nextInt(1) + 3;
		final int it = id%4 + 1 ;
		final Handler handler = new Handler();
		         Runnable runnable = new Runnable() {
		            int i=0;
		            public void run() {
		            	if(i == 0){
		                imageView.setImageResource(imageArray[i]);
		                imageView2.setImageResource(imageArray[i+1]);
		        	    imageView2.startAnimation(slide);
		            	}
		            	if(i == 1){
			                imageView.setImageResource(imageArray[i]);
			                imageView2.setImageResource(imageArray[i-1]);
			            	}
		            	 imageView2.startAnimation(slide);
		                i++;
		                if(i>imageArray.length-1)
		                {
		                i=0;    
		                }
		                handler.postDelayed(this, 3000*it);  //for interval...
		            }
		        };
		        handler.postDelayed(runnable, 2000); //for initial delay..
		    
		
		
//		imageView.setScaleType(ScaleType.FIT_XY);
//		imageView.setX(2);
//		imageView.setY(2);
		
		textView = new TextView(context);
		textView.setTextColor(getResources().getColor(android.R.color.white));
		Typeface dincond = Typeface.createFromAsset(context.getAssets(), "fonts/segoeui.ttf");
		textView.setTypeface(dincond);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
		textView.setText(title);
//		textView.setBackground(getResources().getDrawable(image));
//		textView.setGravity(Gravity.BOTTOM);
		textView.setGravity(Gravity.BOTTOM);
		
//		this.setGravity(Gravity.BOTTOM);
		this.setClickable(true);

		
		this.addView(imageView);
		this.addView(imageView2);
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
	private ImageView imageView, imageView2;
	private TextView textView;
}

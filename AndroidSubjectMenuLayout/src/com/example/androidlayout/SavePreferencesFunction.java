package com.example.androidlayout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SavePreferencesFunction extends Activity{
	SharedPreferences pre;
public SavePreferencesFunction(){
}
@Override
protected void onPause(){
	super.onPause();
//	savingPreferences(filename, key, value)
}
public void saveArray(String filename, int id, String title, String url){
	String[] array = {title, url};
	String arrayName = "metro" + id;
	SharedPreferences prefs = getSharedPreferences(filename, MODE_PRIVATE);
	SharedPreferences.Editor editor = prefs.edit();
	editor.putInt(arrayName + "_size", array.length);
	for(int i = 0; i<array.length; i++){
		editor.putString(arrayName + "_" + i, array[i]);
	}
	editor.commit();
}
//public void loadArray(String filename, )

}


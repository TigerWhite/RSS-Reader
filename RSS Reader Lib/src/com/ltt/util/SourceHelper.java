package com.ltt.util;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;

import com.ltt.rssreader.R;

/**
 * Class cung cap ham lay phan tu trog /res theo name<br>
 * Tra ve mang cac Categories va mang cac link theo category hien tai
 * 
 * @author Nguyen Duc Hieu
 * 
 */
public class SourceHelper extends Activity {
	private String[] categories;
	private String[] items;
	private Context parent;

	/**
	 * khoi tao mac dinh, su dung link trong strings.xml
	 * 
	 * @param parent
	 *            Context ngu canh dung de goi resource mac dinh
	 */
	public SourceHelper(Context iParent) {
		super();
		this.parent = iParent;
		setCategories(parent.getResources().getStringArray(R.array.Categories));
		setItems(parent.getResources().getStringArray(R.array.General));
	}

	/**
	 * khoi tao doi tuong xu ly nguon thong qua chu de va link
	 * 
	 * @param categories
	 *            String[]
	 * @param items
	 *            String[]
	 */
	public SourceHelper(Context iParent, String[] categories, String[] items) {
		super();
		this.parent = iParent;
		this.categories = categories;
		this.items = items;
	}

	// tra lai mang cac Chu de
	public String[] getCategories() {
		return categories;
	}

	// thiet lap chu de
	// TODO phu thuoc profile nguoi dung
	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	// tra ve mang cac link
	public String[] getItems() {
		return items;
	}

	// thiet lap link truc tiep
	public void setItems(String[] items) {
		this.items = items;
	}

	// thiet lap link tu nhom
	public void setItemsFromCategory(String iName) {
		int resId = getResId(iName, R.array.class);
		setItems(parent.getResources().getStringArray(resId));
	}

	/**
	 * tra lai resource id (int) tu ten cua element
	 * 
	 * @param variableName
	 *            - name of drawable, e.g R.drawable.<b>image</b>
	 * @param c
	 *            - class of resource, e.g R.drawable.<b>class</b>
	 * @return id of resource
	 */
	public int getResId(String variableName, Class<?> c) {

		try {
			Field idField = c.getDeclaredField(variableName);
			return idField.getInt(idField);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}

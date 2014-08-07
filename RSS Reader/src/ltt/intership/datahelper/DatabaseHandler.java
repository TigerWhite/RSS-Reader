package ltt.intership.datahelper;

import java.util.ArrayList;
import java.util.List;

import ltt.intership.R;
import ltt.intership.androidlayout.MetroItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "RSSSearch";

	// table name
	private static final String TABLE_NAME = "MetroItem";

	// Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_LINK = "link";
	Context context;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER," + KEY_TITLE + " TEXT,"
				+ KEY_IMAGE + " INTEGER," + KEY_LINK + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding
	public void addData(MetroItem mMetroItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMetroItem.getId());
		values.put(KEY_TITLE, mMetroItem.getTitle());
		values.put(KEY_IMAGE, mMetroItem.getImage());
		values.put(KEY_LINK, mMetroItem.getLink());

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}
	public void addData2(String title, int image, String link, int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, id);
		values.put(KEY_TITLE, title);
		values.put(KEY_IMAGE, image);
		values.put(KEY_LINK, link);

		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
	}

//	// Getting single
	//id value 1->n
	public MetroItem getMetroItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
				KEY_TITLE, KEY_IMAGE, KEY_LINK }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		MetroItem mMetroItem = new MetroItem(context, cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(0)));
		// return
		return mMetroItem;
	}
	
	// Getting All Contacts
	public List<MetroItem> getAllMetroItem() {
		List<MetroItem> mMetroList = new ArrayList<MetroItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				MetroItem mMetroItem = new MetroItem(context);
				mMetroItem.setID(Integer.parseInt(cursor.getString(0)));
				mMetroItem.setTitle(cursor.getString(1));

				Log.d("bug", "bug1:" +cursor.getString(1));
				Log.d("bug", "bug1:" +cursor.getString(2));
				Log.d("bug", "bug1:" +cursor.getString(3));
				Log.d("bug", "bug1:" +cursor.getString(0));
				
				mMetroItem.setImage(Integer.parseInt(cursor.getString(2)));
				
				mMetroItem.setLink(cursor.getString(3));
				// Adding contact to list
				
				mMetroList.add(new MetroItem(context, cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(0))));

			} while (cursor.moveToNext());
		}
		db.close();
		// return list
		return mMetroList;
	}

	// Updating single
	public int updateMetroItem(MetroItem mMetroItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMetroItem.getId());
		values.put(KEY_TITLE, mMetroItem.getTitle());
		values.put(KEY_IMAGE, mMetroItem.getImage());
		values.put(KEY_LINK, mMetroItem.getLink());

		// updating row
		return db.update(TABLE_NAME, values, KEY_ID + " = ?",
				new String[] { String.valueOf(mMetroItem.getId()) });
	}

	// Deleting single
	public void deleteMetroItem(MetroItem mMetroItem1) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { String.valueOf(mMetroItem1.getId()) });
		updateIdMetroItem(mMetroItem1.getId());
//		updateIdMetroItem();
		Log.d("tag", "deleted id:"+mMetroItem1.getId());
		db.close();
	}
	
//	public int updateIdMetroItem(int id){
//		MetroItem mMetroItem;
//		SQLiteDatabase db = this.getWritableDatabase();
//		// looping through all rows and adding to list
//		Log.d("tag", "delete, count:"+getListCount());
//		if(id == getListCount()){
//			return 1;
//		}
//		else{
//		for(int i = id; i <= getListCount(); i++) {
//				mMetroItem = getMetroItem(i+1);
//				ContentValues values = new ContentValues();
//				values.put(KEY_ID, i);
//				Log.d("tag", "runid: "+i);
//				values.put(KEY_TITLE, mMetroItem.getTitle());
//				values.put(KEY_IMAGE, mMetroItem.getImage());
//				values.put(KEY_LINK, mMetroItem.getLink());
//				
//
//				// updating row
//				db.update(TABLE_NAME, values, KEY_ID + " = ?",
//						new String[] { String.valueOf(i+1) });
//			}
//		}
//		return 0;
//		}
	
//	public void updateIdMetroItem(){
//		MetroItem mMetroItem;
//		SQLiteDatabase db = this.getWritableDatabase();
//		// looping through all rows and adding to list
//		Log.d("tag", "delete, count:"+getListCount());
//
//		List<MetroItem> mMetroList = new ArrayList<MetroItem>();
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_NAME;
//
//		Cursor cursor = db.rawQuery(selectQuery, null);
//		int i = 1;
//		if (cursor.moveToFirst()) {
//			do {
//				mMetroItem = new MetroItem(context);
//				if(i != Integer.parseInt(cursor.getString(0))){
//					ContentValues values = new ContentValues();
//					values.put(KEY_ID, i);
//					Log.d("tag", "idupdate: "+i);
//					
//					Log.d("tag", "idmetroupdate: "+Integer.parseInt(cursor.getString(0)));
//					values.put(KEY_TITLE, mMetroItem.getTitle());
//					values.put(KEY_IMAGE, mMetroItem.getImage());
//					values.put(KEY_LINK, mMetroItem.getLink());
//					// updating row
//					db.update(TABLE_NAME, values, KEY_ID + " = ?",
//							new String[] { String.valueOf(Integer.parseInt(cursor.getString(0))) });
//				}
//				i++;
////				mMetroItem.setID(Integer.parseInt(cursor.getString(0)));
////				mMetroItem.setTitle(cursor.getString(1));
////				mMetroItem.setImage(Integer.parseInt(cursor.getString(2)));
////				mMetroItem.setLink(cursor.getString(3));
////				// Adding contact to list
////				
////				mMetroList.add(new MetroItem(context, cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(0))));
//
//			} while (cursor.moveToNext());
//		}
//		db.close();
//		}
	public void updateIdMetroItem(int id){
	SQLiteDatabase db = this.getWritableDatabase();
	Log.d("tag", "delete, count:"+getListCount());
	if(id-1 != getListCount()){
	for(int i = id ; i <= getListCount(); i++) {
			MetroItem mMetroItem = getMetroItem(i+1);
			ContentValues values = new ContentValues();
			values.put(KEY_ID, i);
			Log.d("tag", "idchange: "+i);
			values.put(KEY_TITLE, mMetroItem.getTitle());
			values.put(KEY_IMAGE, mMetroItem.getImage());
			values.put(KEY_LINK, mMetroItem.getLink());
			// updating row
			db.update(TABLE_NAME, values, KEY_ID + " = ?",
					new String[] { String.valueOf(i+1) });
		}
	}
	
	
	}


	// Getting Count
	public int getListCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
	    cursor.close(); 
		// return count
		return count;
	}
	public void initDefaultMetroItem(){
		addData(new MetroItem(context, "General", R.drawable.selector_orange, "http://vnexpress.net/rss/tin-moi-nhat.rss", 1));
		addData(new MetroItem(context,"Sport", R.drawable.selector_blue1, "http://vnexpress.net/rss/the-thao.rss", 2));
		addData(new MetroItem(context,"Youth", R.drawable.selector_green2, "http://dantri.com.vn/nhipsongtre.rss", 3));
		addData(new MetroItem(context, "Economy", R.drawable.selector_yellow, "http://vnexpress.net/rss/kinh-doanh.rss", 4));
		addData(new MetroItem(context, "Politics", R.drawable.selector_green1, "http://dantri.com.vn/xa-hoi.rss", 5));
		addData(new MetroItem(context, "Education", R.drawable.selector_blue3, "http://dantri.com.vn/giaoduc-khuyenhoc.rss", 6));
		addData(new MetroItem(context, "Science", R.drawable.selector_pink, "http://vnexpress.net/rss/khoa-hoc.rss", 7));
		addData(new MetroItem(context, "Health", R.drawable.selector_green3, "http://dantri.com.vn/suckhoe.rss", 8));
		addData(new MetroItem(context, "Entertainment", R.drawable.selector_yellow, "http://vnexpress.net/rss/giai-tri.rss", 9));
		addData(new MetroItem(context, "World", R.drawable.selector_blue4, "http://vnexpress.net/rss/the-gioi.rss", 10));
//		addData(new MetroItem(context, R.drawable.selector_orange, 11));
//		addData(new MetroItem(context, R.drawable.selector_blue1, 12));
//		addData(new MetroItem(context, R.drawable.selector_green2, 13));
//		addData(new MetroItem(context, R.drawable.selector_yellow, 14));
//		addData(new MetroItem(context, R.drawable.selector_green1, 15));

		
	}
}

package com.vesit.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "Praxis";

	// Stuffs table name
	private static final String TABLE_TOP_NEWS = "topNews";
	private static final String TABLE_EVENTS = "events";
	private static final String TABLE_COUPONS = "coupons";

	// Stuffs Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_NEWS = "news";
	private static final String KEY_URL = "url";
	private static final String KEY_CATEGORY = "category";

	private static final String KEY_UNIQUEID = "uniqueid";
	private static final String KEY_STATUS = "status";
	private static final String KEY_EVENT_NAME = "eventname";
	private static final String KEY_TEAMS = "teams";
	private static final String KEY_FEES = "fees";
	private static final String KEY_PM_1 = "pm_1";
	private static final String KEY_PM_2 = "pm_2";
	private static final String KEY_PM_3 = "pm_3";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_StuffS_TABLE = "CREATE TABLE " + TABLE_TOP_NEWS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE
				+ " TEXT UNIQUE," + KEY_NEWS + " TEXT UNIQUE," + KEY_URL
				+ " TEXT UNIQUE)";
		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT ,"
				+ KEY_NEWS + " TEXT UNIQUE ," + KEY_URL + " TEXT ,"
				+ KEY_CATEGORY + " TEXT," + KEY_TEAMS + " TEXT," + KEY_FEES
				+ " TEXT," + KEY_PM_1 + " TEXT," + KEY_PM_2 + " TEXT,"
				+ KEY_PM_3 + " TEXT )";
		String CREATE_COUPONS_TABLE = "CREATE TABLE " + TABLE_COUPONS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_EVENT_NAME
				+ " TEXT UNIQUE," + KEY_UNIQUEID + " TEXT ," + KEY_STATUS
				+ " TEXT)";
		db.execSQL(CREATE_StuffS_TABLE);
		db.execSQL(CREATE_EVENTS_TABLE);
		db.execSQL(CREATE_COUPONS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOP_NEWS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUPONS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public// Adding new Stuff
	void addStuff(Stuff stuff) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, stuff.getTitle()); // Stuff Name
		values.put(KEY_NEWS, stuff.getNews());
		values.put(KEY_URL, stuff.getUrl());// Stuff Phone

		// Inserting Row
		db.insert(TABLE_TOP_NEWS, null, values);
		db.close(); // Closing database connection
	}

	public// Adding new Stuff
	void addEvents(Events events) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, events.getTitle()); // Stuff Name
		values.put(KEY_NEWS, events.getNews());
		values.put(KEY_URL, events.getUrl());// Stuff Phone
		values.put(KEY_CATEGORY, events.getCategory());
		values.put(KEY_TEAMS, events.getTeams());
		values.put(KEY_FEES, events.getFees());
		values.put(KEY_PM_1, events.getPm_1());
		values.put(KEY_PM_2, events.getPm_2());
		values.put(KEY_PM_3, events.getPm_3());

		// Inserting Row
		db.insert(TABLE_EVENTS, null, values);
		db.close(); // Closing database connection
	}

	public void addCoupons(Coupons coupons) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_UNIQUEID, coupons.getUniqueid());
		values.put(KEY_STATUS, coupons.getStatus());// Stuff Phone
		values.put(KEY_EVENT_NAME, coupons.getEventname());

		// Inserting Row
		db.insert(TABLE_COUPONS, null, values);
		db.close(); // Closing database connection

	}

	// Getting All Stuffs
	public ArrayList<HashMap<String, String>> getAllStuff() {
		ArrayList<HashMap<String, String>> stuffList = new ArrayList<HashMap<String, String>>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TOP_NEWS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();

				map.put("title", cursor.getString(1));

				map.put("news", cursor.getString(2));
				map.put("image", cursor.getString(3));
				stuffList.add(map);

			} while (cursor.moveToNext());
		}

		// return Stuff list
		return stuffList;
	}

	public ArrayList<HashMap<String, String>> getAllEvents(String category) {
		ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_EVENTS
				+ " WHERE category=" + "'" + category + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();

				map.put("eventname", cursor.getString(1));

				map.put("description", cursor.getString(2));
				map.put("image", cursor.getString(3));
				map.put("category", cursor.getString(4));
				map.put("teams", cursor.getString(5));
				map.put("fees", cursor.getString(6));
				map.put("pm_1", cursor.getString(7));
				map.put("pm_2", cursor.getString(8));
				map.put("pm_3", cursor.getString(9));

				eventList.add(map);

			} while (cursor.moveToNext());
		}

		// return Stuff list
		return eventList;
	}

	public ArrayList<HashMap<String, String>> getAllCoupons() {
		ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_COUPONS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();

				map.put("eventname", cursor.getString(1));
				map.put("uniqueid", cursor.getString(2));
				map.put("status", cursor.getString(3));

				eventList.add(map);

			} while (cursor.moveToNext());
		}

		// return Stuff list
		return eventList;
	}

	// Getting Stuffs Count
	public int getStuffCount() {
		String countQuery = "SELECT  * FROM " + TABLE_TOP_NEWS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

	// Getting Stuffs Count
	public int getEventsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

	public int getCouponsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COUPONS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}

	public void deleteStuff() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_TOP_NEWS);
	}
	public void deleteEvents(String category) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_EVENTS + " where category=" + "'" + category + "'");
	}

}
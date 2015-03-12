package com.vesit.utils;

public class Stuff {
	// private variables
	int _id;
	String _title;
	String _news;
	String _url;

	// Empty constructor
	public Stuff() {

	}

	// constructor
	public Stuff(int id, String title, String news, String url) {
		this._id = id;
		this._title = title;
		this._news = news;
		this._url = url;
	}

	// constructor
	public Stuff(String title, String news, String url) {
		this._title = title;
		this._news = news;
		this._url = url;
	}

	// getting ID
	public int getID() {
		return this._id;
	}

	// setting id
	public void setID(int id) {
		this._id = id;
	}

	// getting name
	public String getTitle() {
		return this._title;
	}

	// setting name
	public void setTitle(String title) {
		this._title = title;
	}

	// getting phone number
	public String getNews() {
		return this._news;
	}

	// setting phone number
	public void setNews(String news) {
		this._news = news;
	}

	public String getUrl() {
		return this._url;
	}

	// setting phone number
	public void setUrl(String url) {
		this._url = url;
	}

}

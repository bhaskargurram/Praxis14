package com.vesit.utils;

public class Events {
	// private variables
	int _id;
	String _title;
	String _news;
	String _url;
	String _category;
	String _teams;
	String _fees;
	String _pm_1; 
	String _pm_2; 
	String _pm_3;

	// Empty constructor
	public Events() {

	}

	// constructor
	public Events(int id, String title, String news, String url, String category,String teams,String fees,String pm_1, String pm_2, String pm_3) {
		this._id = id;
		this._title = title;
		this._news = news;
		this._url = url;
		this._category = category;
		this._teams=teams;
		this._fees=fees;
		this._pm_1=pm_1;
		this._pm_2=pm_2;
		this._pm_3=pm_3;
	}

	// constructor
	public Events(String title, String news, String url, String category,String teams,String fees,String pm_1, String pm_2, String pm_3) {
		this._title = title;
		this._news = news;
		this._url = url;
		this._category = category;
		this._teams=teams;
		this._fees=fees;
		this._pm_1=pm_1;
		this._pm_2=pm_2;
		this._pm_3=pm_3;
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

	public String getCategory() {
		return this._category;
	}

	// setting phone number
	public void setCategory(String category) {
		this._category = category;
	}
	public String getTeams() {
		return this._teams;
	}

	// setting phone number
	public void setTeams(String teams) {
		this._teams = teams;
	}
	public String getFees() {
		return this._fees;
	}

	// setting phone number
	public void setFees(String fees) {
		this._fees = fees;
	}
	public String getPm_1() {
		return this._pm_1;
	}

	// setting phone number
	public void setPm_1(String pm_1) {
		this._pm_1 = pm_1;
	}
	public String getPm_2() {
		return this._pm_2;
	}

	// setting phone number
	public void setPm_2(String pm_2) {
		this._pm_2 = pm_2;
	}
	public String getPm_3() {
		return this._pm_3;
	}

	// setting phone number
	public void setPm_3(String pm_3) {
		this._pm_3 = pm_3;
	}

}

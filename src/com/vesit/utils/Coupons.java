package com.vesit.utils;

public class Coupons {
	int _id;
	String _regid;
	String _uniqueid;
	String _status;
	String _eventname;

	Coupons() {

	}

	public Coupons(int id,  String eventname, String uniqueid,
			String status) {
		this._id = id;
		
		this._eventname = eventname;
		this._uniqueid = uniqueid;
		this._status = status;
	}

	public Coupons(String regid, String eventname, String uniqueid,
			String status) {

		this._regid = regid;
		this._eventname = eventname;
		this._uniqueid = uniqueid;
		this._status = status;
	}

	
	public String getEventname() {
		return this._eventname;
	}

	public void setEventname(String eventname) {
		this._eventname = eventname;
	}

	public String getUniqueid() {
		return this._uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this._uniqueid = uniqueid;
	}

	public String getStatus() {
		return this._status;
	}

	public void setStatus(String status) {
		this._status = status;
	}
}

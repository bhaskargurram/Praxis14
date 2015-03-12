package com.vesit.adapter;

public class NavigationItemAdapter {

	public String title;
	public int counter;

	public boolean isHeader;

	public NavigationItemAdapter(String title, boolean header, int counter) {
		this.title = title;

		this.isHeader = header;
		this.counter = counter;
	}

	public NavigationItemAdapter(String title, boolean header) {
		this(title, header, 0);
	}

	public NavigationItemAdapter(String title) {
		this(title, false);
	}
}
